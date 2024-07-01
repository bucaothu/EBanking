package com.bank.ebanking.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
    private int idPayments;
    private String merchant;
    private boolean isReceived;
    private float amount;
    private Date date;
    private String description;
    private BankAccount accountNumber;



    public Payment() {
    }

    public Payment(int idPayments, String merchant, boolean isReceived, float amount, Date date, String description, BankAccount accountNumber) {
        this.idPayments = idPayments;
        this.merchant = merchant;
        this.isReceived = isReceived;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.accountNumber = accountNumber;
    }

    public Payment(String merchant, boolean isReceived, float amount, Date date, String description, BankAccount accountNumber) {
        this.merchant = merchant;
        this.isReceived = isReceived;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.accountNumber = accountNumber;
    }

    public int getIdPayments() {
        return idPayments;
    }

    public void setIdPayments(int idPayments) {
        this.idPayments = idPayments;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankAccount getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BankAccount accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Constructors, getters, setters
}
