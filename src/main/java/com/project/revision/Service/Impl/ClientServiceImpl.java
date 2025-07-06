package com.project.revision.Service.Impl;

import com.project.revision.Config.JWT.TokenHandler;
import com.project.revision.Dao.ClientDao;
import com.project.revision.Dto.ClientAccInfo;
import com.project.revision.Dto.ClientDto;
import com.project.revision.Dto.LoginInfo;
import com.project.revision.Mapper.ClientMapper;
import com.project.revision.Service.ClientService;
import com.project.revision.model.Auth;
import com.project.revision.model.Client;
import com.project.revision.sitting.CodeGenrator;
import jakarta.transaction.SystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final TokenHandler tokenHandler;
    private final PasswordEncoder passwordEncoder;
    private final RedisServiceImpl redisService;
    private final EmailServiceImpl emailService;

    public ClientServiceImpl(ClientDao clientDao, TokenHandler tokenHandler, PasswordEncoder passwordEncoder, RedisServiceImpl redisService, EmailServiceImpl emailService) {
        this.clientDao = clientDao;
        this.tokenHandler = tokenHandler;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.emailService = emailService;
    }


    @Override
    public ClientDto findbyuseremail(String email) throws SystemException {
      Client client = clientDao.findClientByUserEmail(email);
       if(client ==null){
           throw new SystemException("no user with that name");
       }
        return ClientMapper.toDto(client);
    }


    public String login(LoginInfo loginInfo) throws SystemException {
        Client client=clientDao.findClientByUserEmail(loginInfo.getEmail());
        if(client ==null)
            throw new SystemException("no user with that name");
        if(!passwordEncoder.matches(loginInfo.getPassword(),client.getUserPassword()))
            throw new SystemException("invalid password");

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
                                                    clientAccInfo.getUser_phoneNumber(),auths);
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
        emailService.sendSimpleMessage(clientDto.getUser_email(),"Email confirmation code from Spring boot",code);
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


}
