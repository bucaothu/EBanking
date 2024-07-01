package com.bank.ebanking.model;

import java.io.Serializable;
import java.util.Date;

public class InterestRate implements Serializable {
    public InterestRate(int termMonths, float minBalance, float interestRate, Date startDate, Date endDate, SavingAccountType idSavingAccountType) {
        this.termMonths = termMonths;
        this.minBalance = minBalance;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idSavingAccountType = idSavingAccountType;
    }

    private int idInterestRate;
    private int termMonths;
    private float minBalance;
    private float interestRate;
    private Date startDate;
    private Date endDate;
    private SavingAccountType idSavingAccountType;

    public InterestRate(int idInterestRate, int termMonths, float minBalance, float interestRate, Date startDate, Date endDate, SavingAccountType idSavingAccountType) {
        this.idInterestRate = idInterestRate;
        this.termMonths = termMonths;
        this.minBalance = minBalance;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idSavingAccountType = idSavingAccountType;
    }

    public InterestRate() {
    }

    public int getIdInterestRate() {
        return idInterestRate;
    }

    public void setIdInterestRate(int idInterestRate) {
        this.idInterestRate = idInterestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public float getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(float minBalance) {
        this.minBalance = minBalance;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
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

    public SavingAccountType getIdSavingAccountType() {
        return idSavingAccountType;
    }

    public void setIdSavingAccountType(SavingAccountType idSavingAccountType) {
        this.idSavingAccountType = idSavingAccountType;
    }
    // Constructors, getters, setters
}