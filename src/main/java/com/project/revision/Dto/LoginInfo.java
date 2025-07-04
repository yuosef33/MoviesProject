package com.project.revision.Dto;



public class LoginInfo {
    private String email;
    private String password;

    public LoginInfo(){}
    public LoginInfo(String password, String email) {
        this.password = password;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
