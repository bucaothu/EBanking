package com.bank.ebanking.model;

import java.io.Serializable;

public class BankAccount implements Serializable {
    private String accountNumber;
    private float balance;
    private User idUser;

    public BankAccount(String accountNumber, float balance, User idUser) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.idUser = idUser;
    }

    public BankAccount() {
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
    // Constructors, getters, setters
}
