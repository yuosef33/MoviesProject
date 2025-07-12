package com.project.revision.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientAccInfo {
    @NotBlank
    private String user_name;
    @NotBlank
    @Email
    private String user_email;
    @NotBlank
    private String user_password;
    @NotBlank
    private String user_phoneNumber;



    public ClientAccInfo() {
    }

    public ClientAccInfo(String user_name, String user_email, String user_password, String user_phoneNumber) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_phoneNumber = user_phoneNumber;
    }



    public @NotBlank String getUser_name() {
        return user_name;
    }

    public void setUser_name(@NotBlank String user_name) {
        this.user_name = user_name;
    }

    public @NotBlank @Email String getUser_email() {
        return user_email;
    }

    public void setUser_email(@NotBlank @Email String user_email) {
        this.user_email = user_email;
    }

    public @NotBlank String getUser_password() {
        return user_password;
    }

    public void setUser_password(@NotBlank String user_password) {
        this.user_password = user_password;
    }

    public @NotBlank String getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_phoneNumber(@NotBlank String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }
}
