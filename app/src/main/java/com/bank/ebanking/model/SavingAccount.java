package com.bank.ebanking.model;

import java.io.Serializable;
import java.util.Date;

public class SavingAccount implements Serializable {
    private String accountNumber;
    private float balance;
    private Date startDate;
    private Date endDate;
    private float interestRate;
    private int status;
    private BankAccount bankAccountNumber;
    private SavingAccountType idSavingAccountType;

    public SavingAccount(String accountNumber, float balance, Date startDate, Date endDate, float interestRate, int status, BankAccount bankAccountNumber, SavingAccountType idSavingAccountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interestRate = interestRate;
        this.status = status;
        this.bankAccountNumber = bankAccountNumber;
        this.idSavingAccountType = idSavingAccountType;
    }

    public SavingAccount() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BankAccount getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(BankAccount bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public SavingAccountType getIdSavingAccountType() {
        return idSavingAccountType;
    }

    public void setIdSavingAccountType(SavingAccountType idSavingAccountType) {
        this.idSavingAccountType = idSavingAccountType;
    }
    // Constructors, getters, setters
}
