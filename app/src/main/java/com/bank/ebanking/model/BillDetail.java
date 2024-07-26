package com.bank.ebanking.model;

import java.io.Serializable;

public class BillDetail implements Serializable {
    private String billNumber;
    private String period;
    private int amount;
    private String billCreated;
    private String billExpiry;
    private String billType;
    private String billOtherInfo;
    private boolean isPartialPaymentAllowed;
    private String extraInfo;

    // Getters and setters for all fields
    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBillCreated() {
        return billCreated;
    }

    public void setBillCreated(String billCreated) {
        this.billCreated = billCreated;
    }

    public String getBillExpiry() {
        return billExpiry;
    }

    public void setBillExpiry(String billExpiry) {
        this.billExpiry = billExpiry;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillOtherInfo() {
        return billOtherInfo;
    }

    public void setBillOtherInfo(String billOtherInfo) {
        this.billOtherInfo = billOtherInfo;
    }

    public boolean isPartialPaymentAllowed() {
        return isPartialPaymentAllowed;
    }

    public void setPartialPaymentAllowed(boolean isPartialPaymentAllowed) {
        this.isPartialPaymentAllowed = isPartialPaymentAllowed;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}

