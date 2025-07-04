package com.project.revision.Service;

import com.project.revision.Dto.ClientAccInfo;
import com.project.revision.Dto.ClientDto;
import com.project.revision.Dto.LoginInfo;
import com.project.revision.model.Client;
import jakarta.transaction.SystemException;

import java.util.List;
import java.util.Map;

public interface ClientService {
    ClientDto findbyuseremail(String username) throws SystemException;
    String login(LoginInfo loginInfo) throws SystemException;
    Client getClientFromToken(String token) ;
    ClientDto createClient(ClientAccInfo clientAccInfo) throws SystemException;
    List<Map<String ,String>> createUniqeClient(ClientAccInfo clientAccInfo) throws SystemException;
    ClientDto verifyAccount(String code,String email) throws SystemException;
}
