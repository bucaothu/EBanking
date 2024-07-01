package com.bank.ebanking.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.Payment;
import com.bank.ebanking.model.Transfer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdapterBankAccounts extends RecyclerView.Adapter<AdapterBankAccounts.MyViewHolder> {
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public AdapterBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts.addAll(bankAccounts);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_accounts, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BankAccount bankAccount = bankAccounts.get(position);
        holder.tvAccountNumber.setText(bankAccount.getAccountNumber());
        holder.tvBalance.setText(String.valueOf(bankAccount.getBalance())+" VND");
    }

    @Override
    public int getItemCount() {
        return bankAccounts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAccountNumber, tvBalance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountNumber = itemView.findViewById(R.id.tv_bank_account_number);
            tvBalance = itemView.findViewById(R.id.tv_bank_account_balance);
        }
    }
}