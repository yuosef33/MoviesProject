package com.project.revision.Dto;

public class ClientDataUpdate {
    private String username;
    private String phoneNumber;

    public ClientDataUpdate() {
    }

    public ClientDataUpdate(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
