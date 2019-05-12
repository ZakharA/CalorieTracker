package com.example.calorietracker.helper.api.Entities;

import java.util.Date;

public class Credential {
    private int credentialId;
    private String passwordHash;
    private String signUpDate;
    private User userId;
    private String username;

    public Credential(User user, String username, String password, String singupDate) {
        this.userId = user;
        this.username = username;
        this.passwordHash = password;
        this.signUpDate = singupDate;
    }

    public Credential(String username, String password) {
        this.username = username;
        this.passwordHash = password;
    }

    public Credential() {

    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }
}
