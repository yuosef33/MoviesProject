package com.project.revision.Dto;

public class PasswordUpdate {
    private String currentPassword;
    private String updatedPassword;

    public PasswordUpdate() {
    }

    public PasswordUpdate(String currentPassword, String updatedPassword) {
        this.currentPassword = currentPassword;
        this.updatedPassword = updatedPassword;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public void setUpdatedPassword(String updatedPassword) {
        this.updatedPassword = updatedPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
