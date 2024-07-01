package com.bank.ebanking.model;

import java.io.Serializable;
import java.util.Date;

public class Transfer implements Serializable {
    private int idTransfer;
    private BankAccount fromAccountNumber;
    private BankAccount toAccountNumber;
    private float amount;
    private Date date;
    private BankAccount idBankAccount;

    public Transfer(int idTransfer, BankAccount fromAccountNumber, BankAccount toAccountNumber, float amount, Date date, BankAccount idBankAccount) {
        this.idTransfer = idTransfer;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.date = date;
        this.idBankAccount = idBankAccount;
    }

    public Transfer(BankAccount fromAccountNumber, BankAccount toAccountNumber, float amount, Date date, BankAccount idBankAccount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.date = date;
        this.idBankAccount = idBankAccount;
    }

    public Transfer() {
    }

    public int getIdTransfer() {
        return idTransfer;
    }

    public void setIdTransfer(int idTransfer) {
        this.idTransfer = idTransfer;
    }

    public BankAccount getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(BankAccount fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public BankAccount getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(BankAccount toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BankAccount getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(BankAccount idBankAccount) {
        this.idBankAccount = idBankAccount;
    }
    // Constructors, getters, setters
}

