package com.bank.ebanking.model;

import java.io.Serializable;

// Class representing a User
public class UserProfile implements Serializable {
    private int idUserProfile;
    private String name;
    private String phone;
    private String email;
    private String cccd;
    public UserProfile(String name, String phone, String email, String cccd) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cccd = cccd;
    }

    public UserProfile() {
    }

    public int getIdUserProfile() {
        return idUserProfile;
    }

    public void setIdUserProfile(int idUserProfile) {
        this.idUserProfile = idUserProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
    // Constructors, getters, setters
}