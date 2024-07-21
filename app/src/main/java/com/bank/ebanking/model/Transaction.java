package com.bank.ebanking.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private int idPayments;
    private BankAccount toAccountNumber;
    private float amount;
    private Date date;
    private String description;
    private TransactionType transactionType;
    private BankAccount accountNumber;

    public Transaction() {
    }

    public Transaction(BankAccount toAccountNumber, float amount, Date date, String description, TransactionType transactionType, BankAccount accountNumber) {
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
    }

    public Transaction(int idPayments, BankAccount toAccountNumber, float amount, Date date, String description, TransactionType transactionType, BankAccount accountNumber) {
        this.idPayments = idPayments;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
    }

    public int getIdPayments() {
        return idPayments;
    }

    public void setIdPayments(int idPayments) {
        this.idPayments = idPayments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BankAccount getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BankAccount accountNumber) {
        this.accountNumber = accountNumber;
    }
}
