package com.bank.ebanking.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.intent.IntentViewBill;
import com.bank.ebanking.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.MyViewHolder> {
    private List<Transaction> itemList = new ArrayList<>();

    public AdapterBill(List<Transaction> transactionList) {
        itemList.addAll(transactionList);
        Collections.sort(itemList, (t1, t2) -> t2.getDate().compareTo(t1.getDate()));
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
        if(item.getTransactionType().getIdTransactionType()==2)
            person = item.getDescription();
        else person = item.getToAccountNumber().getIdUser().getUserProfile().getName();
        date = new SimpleDateFormat("dd-MM-yyyy").format(item.getDate());
        amount = String.valueOf(item.getAmount());

        holder.person.setText(person);
        holder.date.setText(date);
        holder.amount.setText(amount);
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ((Activity) view.getContext()).getIntent();
                intent.putExtra("transaction", item);
                intent.setClass(view.getContext(), IntentViewBill.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView person, amount, date;
        public ImageButton btnViewDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            person = itemView.findViewById(R.id.txt_toWhom);
            amount = itemView.findViewById(R.id.txt_amount);
            date = itemView.findViewById(R.id.txt_date);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetail);
        }
    }
}
