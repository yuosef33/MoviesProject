package com.project.revision.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.revision.model.Auth;

import java.util.List;

public class ClientDto {
    private Long id;

    private String user_name;

    private String user_email;

    private String user_password;

    private String user_phoneNumber;

    private List<Auth> auths;

    public ClientDto() {
    }

    public ClientDto(Long id, String user_name, String user_email, String user_password, String user_phoneNumber, List<Auth> auths) {
        this.id = id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_phoneNumber = user_phoneNumber;
        this.auths = auths;
    }
    public ClientDto( String user_name, String user_email, String user_password, String user_phoneNumber, List<Auth> auths) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_phoneNumber = user_phoneNumber;
        this.auths = auths;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    public List<Auth> getAuths() {
        return auths;
    }

    public void setAuths(List<Auth> auths) {
        this.auths = auths;
    }
}
