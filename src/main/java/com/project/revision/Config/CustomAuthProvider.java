package com.project.revision.Config;

import com.project.revision.Mapper.ClientMapper;
import com.project.revision.Service.ClientService;
import com.project.revision.model.Auth;
import com.project.revision.model.Client;
import jakarta.transaction.SystemException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

//@Service
public class CustomAuthProvider /*implements AuthenticationProvider*/ {
                                  // you take the username and password from spring and u evalute it by your way
//
//    private final ClientService clientService;
//
//    public CustomAuthProvider(ClientService clientService) {
//        this.clientService = clientService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        try {
//            Client client = ClientMapper.toEntity(clientService.findbyuseremail(username));
//            if (!client.getUserPassword().equals(password)){
//                throw new RuntimeException("Invalid password");
//            }
//
//            List<SimpleGrantedAuthority> mainAuths=new ArrayList<>();
//            for(Auth auth: client.getAuths()){
//                SimpleGrantedAuthority x =new SimpleGrantedAuthority("ROLE_"+auth.getUser_Role());
//                mainAuths.add(x);
//            }
//
//            return new UsernamePasswordAuthenticationToken(username,password,mainAuths);
//        } catch (SystemException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
}
