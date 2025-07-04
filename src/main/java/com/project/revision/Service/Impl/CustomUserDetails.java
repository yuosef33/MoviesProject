package com.project.revision.Service.Impl;

import com.project.revision.model.Auth;
import com.project.revision.model.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Client theClient;

    public CustomUserDetails(Client theClient) {
        this.theClient = theClient;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
   List<SimpleGrantedAuthority> mainAuths=new ArrayList<>();
        for(Auth auth: theClient.getAuths()){
            SimpleGrantedAuthority x =new SimpleGrantedAuthority("ROLE_"+auth.getUser_Role());
            mainAuths.add(x);
        }
        return mainAuths;
    }

    @Override
    public String getPassword() {
        return "{noop}"+theClient.getUserPassword();
    }

    @Override
    public String getUsername() {
        return theClient.getUserName();
    }


}
