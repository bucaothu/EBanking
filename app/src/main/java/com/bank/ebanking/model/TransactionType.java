package com.bank.ebanking.model;

import java.io.Serializable;

public class TransactionType implements Serializable {
    private int idTransactionType;
    private String name;

    public TransactionType(int idTransactionType, String name) {
        this.idTransactionType = idTransactionType;
        this.name = name;
    }

    public int getIdTransactionType() {
        return idTransactionType;
    }

    public void setIdTransactionType(int idTransactionType) {
        this.idTransactionType = idTransactionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
