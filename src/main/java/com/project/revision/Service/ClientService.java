package com.project.revision.Service;

import com.project.revision.Dto.*;
import com.project.revision.model.Client;
import jakarta.transaction.SystemException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ClientService {
    ClientDto findbyuseremail(String username) throws SystemException;
    String login(LoginInfo loginInfo);
    Client getClientFromToken(String token) ;
    ClientDto createClient(ClientAccInfo clientAccInfo) throws SystemException;
    List<Map<String ,String>> createUniqeClient(ClientAccInfo clientAccInfo) throws SystemException;
    ClientDto verifyAccount(String code,String email) throws SystemException;
    Client getCurrentClient();
    String uploadImage(MultipartFile file) throws IOException, SystemException;
    ClientDataUpdate updateUserData(ClientDataUpdate clientDto) ;
    Map<String,String> updateUserPassword(PasswordUpdate passwordUpdate) throws SystemException;
    Map<String,String> forgetPassword(String useremail);
    Map<String, String> verifyForgetPasswordCode(String code,String key);
    Map<String, String> updateUserPasswordFromForget(String userEmail,String Password,String Key) throws SystemException;
}
