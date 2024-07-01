package com.bank.ebanking.model;

import java.io.Serializable;
import java.util.Date;

// Class representing a User
public class User implements Serializable {
    private int idUser;
    private String username;
    private String password;
    private String pin;
    private boolean status;

    private UserProfile userProfile;

    public User(String username, String password, String pin, boolean status) {
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.status = status;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public User(String username, String password, String pin, boolean status, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.status = status;
        this.userProfile = userProfile;
    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pin='" + pin + '\'' +
                ", status=" + status +
                '}';
    }
}
