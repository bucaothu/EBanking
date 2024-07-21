package com.bank.ebanking.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.MyViewHolder> {
    private List<Transaction> itemList = new ArrayList<>();

    public AdapterBill(List<Transaction> transactionList) {
        itemList.addAll(transactionList);
        Collections.sort(itemList, (t1, t2) -> t1.getDate().compareTo(t2.getDate()));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction item = itemList.get(position);
        String person, date, amount;
        person = item.getToAccountNumber().getIdUser().getUserProfile().getName();
        date = new SimpleDateFormat("dd-MM-yyyy").format(item.getDate());
        amount = String.valueOf(item.getAmount());

        holder.person.setText(person);
        holder.date.setText(date);
        holder.amount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView person, amount, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            person = itemView.findViewById(R.id.txt_toWhom);
            amount = itemView.findViewById(R.id.txt_amount);
            date = itemView.findViewById(R.id.txt_date);
        }
    }
}
