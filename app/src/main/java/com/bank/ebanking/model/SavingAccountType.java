package com.bank.ebanking.model;

import java.io.Serializable;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavingAccountType implements Serializable {
    private int idSavingAccountType;
    private String name;
    private String description;
    private float defaultInterestRate;

    public SavingAccountType(int idSavingAccountType, String name, String description, float defaultInterestRate) {
        this.idSavingAccountType = idSavingAccountType;
        this.name = name;
        this.description = description;
        this.defaultInterestRate = defaultInterestRate;
    }

    public SavingAccountType() {
    }

    public SavingAccountType(String name, String description, float defaultInterestRate) {
        this.name = name;
        this.description = description;
        this.defaultInterestRate = defaultInterestRate;
    }

    public int getIdSavingAccountType() {
        return idSavingAccountType;
    }

    public void setIdSavingAccountType(int idSavingAccountType) {
        this.idSavingAccountType = idSavingAccountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDefaultInterestRate() {
        return defaultInterestRate;
    }

    public void setDefaultInterestRate(float defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    // Constructors, getters, setters
}
