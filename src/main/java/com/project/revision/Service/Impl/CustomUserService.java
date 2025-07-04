package com.project.revision.Service.Impl;

import com.project.revision.Mapper.ClientMapper;
import com.project.revision.Service.ClientService;
import com.project.revision.model.Client;
import jakarta.transaction.SystemException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
public class CustomUserService implements UserDetailsService {//this if you want to let spring get username for you then u go and get the client
    //on your self and sent the client to spring and spring evalute it if it correct or incorrect

    private ClientService clientService;


    public CustomUserService(ClientService clientService) {
        this.clientService = clientService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Client client = ClientMapper.toEntity(clientService.findbyuseremail(username));
        return new CustomUserDetails(client);

        } catch (SystemException e) {
            throw new RuntimeException(e);
        }

    }
}
