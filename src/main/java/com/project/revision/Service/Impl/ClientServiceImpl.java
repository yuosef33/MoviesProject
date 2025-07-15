package com.project.revision.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.revision.Config.JWT.TokenHandler;
import com.project.revision.Dao.ClientDao;
import com.project.revision.Dto.*;
import com.project.revision.Mapper.ClientMapper;
import com.project.revision.Service.ClientService;
import com.project.revision.model.Auth;
import com.project.revision.model.Client;
import com.project.revision.sitting.CodeGenrator;
import jakarta.transaction.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final TokenHandler tokenHandler;
    private final PasswordEncoder passwordEncoder;
    private final RedisServiceImpl redisService;
    private final EmailServiceImpl emailService;
    private final Cloudinary cloudinary;

    public ClientServiceImpl(ClientDao clientDao, TokenHandler tokenHandler, PasswordEncoder passwordEncoder, RedisServiceImpl redisService, EmailServiceImpl emailService, Cloudinary cloudinary) {
        this.clientDao = clientDao;
        this.tokenHandler = tokenHandler;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.emailService = emailService;
        this.cloudinary = cloudinary;
    }


    @Override
    public ClientDto findbyuseremail(String email) throws SystemException {
      Client client = clientDao.findClientByUserEmail(email);
       if(client ==null){
           throw new SystemException("no user with that name");
       }
        return ClientMapper.toDto(client);
    }


    public String login(LoginInfo loginInfo) {
        Client client=clientDao.findClientByUserEmail(loginInfo.getEmail());
        if(client ==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"invalid password");
        if(!passwordEncoder.matches(loginInfo.getPassword(),client.getUserPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"invalid password");

        return tokenHandler.creatToken(ClientMapper.toEntity(ClientMapper.toDto(client)));

    }
    public Client getClientFromToken(String token) {
           String email= tokenHandler.getSubject(token);
           if(Objects.isNull(email)){
               return null;
           }
        if(clientDao.findClientByUserEmail(email) ==null)
            return null;
        return clientDao.findClientByUserEmail(email);
    }

    @Override
    public ClientDto createClient(ClientAccInfo clientAccInfo) throws SystemException {

      Client client= clientDao.findClientByUserEmail(clientAccInfo.getUser_email());
        if(client!=null)
            throw new SystemException("this email "+clientAccInfo.getUser_email()+" is already in use");
        // client not exist

        List<Auth> auths=new ArrayList<>();
        Auth auth= new Auth();
        auth.setId(1L);
        auth.setUser_Role("NORMALUSER");
        auths.add(auth);
        ClientDto clientDto=new ClientDto(clientAccInfo.getUser_name(),
                                            clientAccInfo.getUser_email(),
                                                   passwordEncoder.encode(clientAccInfo.getUser_password()),
                                                    clientAccInfo.getUser_phoneNumber()
                ,null
                ,auths
        );
         clientDao.save(ClientMapper.toEntity(clientDto));
        return clientDto;
    }

    @Override
    public List<Map<String ,String>> createUniqeClient(ClientAccInfo clientAccInfo) throws SystemException {
        Client client= clientDao.findClientByUserEmail(clientAccInfo.getUser_email());
        if(client!=null)
            throw new SystemException("this email "+clientAccInfo.getUser_email()+" is already in use");
        // client not exist
        List<Auth> auths=new ArrayList<>();
        Auth auth= new Auth();
        auth.setId(1L);
        auth.setUser_Role("NORMALUSER");
        auths.add(auth);
        ClientDto clientDto=new ClientDto(clientAccInfo.getUser_name(),
                clientAccInfo.getUser_email(),
                passwordEncoder.encode(clientAccInfo.getUser_password()),
                clientAccInfo.getUser_phoneNumber(),auths);
        String code=CodeGenrator.generateCode();
        String verificationId = UUID.randomUUID().toString();
        redisService.save("Client:"+verificationId+"email",clientDto.getUser_email(),600);
        redisService.save("Client:"+verificationId+"username",clientDto.getUser_name(),600);
        redisService.save("Client:"+verificationId+"password",clientDto.getUser_password(),600);
        redisService.save("Client:"+verificationId+"phone",clientDto.getUser_phoneNumber(),600);
        emailService.sendSimpleMessage(clientDto.getUser_email(),"Email confirmation code from Spring boot",code);   //email config
        redisService.save("OTP:"+verificationId,code,600);
        Map<String,String> codes1= new HashMap<>();
        Map<String,String> codes2= new HashMap<>();
        codes1.put("VerificationCode",code);
        codes2.put("VerificationId",verificationId);
        List<Map<String ,String>> mapList=new ArrayList<>();
        mapList.add(codes1);
        mapList.add(codes2);
        return mapList;
    }

    @Override
    public ClientDto verifyAccount(String code,String vId) throws SystemException {
        String mainCode =redisService.get("OTP:"+vId);
        if(mainCode==null)
            throw new SystemException("Code Expierd");
        else if(mainCode.equals(code)){
            ClientDto clientDto=new ClientDto();
            clientDto.setUser_email(redisService.get("Client:"+vId+"email"));
            clientDto.setUser_name(redisService.get("Client:"+vId+"username"));
            clientDto.setUser_password(redisService.get("Client:"+vId+"password"));
            clientDto.setUser_phoneNumber(redisService.get("Client:"+vId+"phone"));
            clientDto.setUserPhoto(null);
            List<Auth> auths=new ArrayList<>();
            Auth auth= new Auth();
            auth.setId(1L);
            auth.setUser_Role("NORMALUSER");
            auths.add(auth);
            clientDto.setAuths(auths);
            clientDao.save(ClientMapper.toEntity(clientDto));
            redisService.delete("Client:"+vId+"email");
            redisService.delete("Client:"+vId+"username");
            redisService.delete("Client:"+vId+"password");
            redisService.delete("Client:"+vId+"phone");
            redisService.delete("OTP:"+vId);
            return clientDto;
        }
        throw new SystemException("Worng Code");
    }

    @Override
    public Client getCurrentClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Client) authentication.getPrincipal();
    }
    public String uploadImage(MultipartFile file) throws IOException, SystemException {
        if(file.getSize() >10*1024*1024){
            throw new SystemException("File too large");
        }
        else if(file.getContentType()==null || !file.getContentType().startsWith("image/"))
            throw new SystemException("Only images allowed");
        
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        Client client=getCurrentClient();
        client.setUserPhoto(uploadResult.get("secure_url").toString());
        clientDao.save(client);
        return uploadResult.get("secure_url").toString();
    }

    @Override
    public ClientDataUpdate updateUserData(ClientDataUpdate clientDto)  {
        Client client= getCurrentClient();
      client.setUserName(clientDto.getUsername());
      client.setUserPhoneNumber(clientDto.getPhoneNumber());
        clientDao.save(client);
        return clientDto;
    }

    @Override
    public Map<String,String> updateUserPassword(PasswordUpdate passwordUpdate) throws SystemException {
      //  Client client =getCurrentClient();
        Client clientRealtime= getCurrentClient();
        if(!passwordEncoder.matches(passwordUpdate.getCurrentPassword(),clientRealtime.getUserPassword())){
            throw new SystemException("wrong password");
        }
        clientRealtime.setUserPassword(passwordEncoder.encode(passwordUpdate.getUpdatedPassword()));
        clientDao.save(clientRealtime);
        Map<String,String> response=new HashMap<>();
        response.put("response","Password successfully updated");
        return response;
    }

    @Override
    public Map<String, String> forgetPassword(String userEmail) {
        Client client= clientDao.findClientByUserEmail(userEmail);
        if(client==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"there is no account with this email");
        }
        String code=CodeGenrator.generateCode();
        String userid=UUID.randomUUID().toString();
        redisService.save("ForgetPassword:"+userid,code,300); //forget password stays for 5 minutes
        emailService.sendSimpleMessage(userEmail,"Forget Password Code",code);
        Map<String,String> response=new HashMap<>();
        response.put("message","Verification code sent");
        response.put("key",userid);
        return response;
    }
    @Override
    public Map<String, String> verifyForgetPasswordCode(String code,String key){
        String realCode= redisService.get("ForgetPassword:"+key);
        if(!realCode.equals(code)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Wrong code");
        }
        redisService.delete("ForgetPassword:"+key);
        String finalKey=UUID.randomUUID().toString();
        redisService.save("NewPasswordToken:"+finalKey,"ture",3600);
        Map<String, String> response=new HashMap<>();
        response.put("message","Verification successful");
        response.put("resetToken",finalKey);
        return response;
    }
    @Override
    public Map<String, String> updateUserPasswordFromForget(String userEmail,String Password,String Key) throws SystemException {
       if (redisService.get("NewPasswordToken:"+Key)==null)
           throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Wrong Key");
       Client client= clientDao.findClientByUserEmail(userEmail);
        if(client==null){
            throw new SystemException("Wrong userEmail");
        }
        client.setUserPassword(passwordEncoder.encode(Password));
        clientDao.save(client);
        Map<String, String> response=new HashMap<>();
        response.put("message","Password changed successfully");
        return response;
    }


}
