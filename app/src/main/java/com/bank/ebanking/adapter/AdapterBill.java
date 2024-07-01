package com.bank.ebanking.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.model.Payment;
import com.bank.ebanking.model.Transfer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.MyViewHolder> {
    private List<Object> itemList = new ArrayList<>();

    public AdapterBill(List<Payment> paymentList, List<Transfer> transferList) {
        itemList.addAll(paymentList);
        itemList.addAll(transferList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            itemList.sort((o1, o2) -> {
                Date date1 = (o1 instanceof Payment) ? ((Payment) o1).getDate() : ((Transfer) o1).getDate();
                Date date2 = (o2 instanceof Payment) ? ((Payment) o2).getDate() : ((Transfer) o2).getDate();
                return date2.compareTo(date1);  // Sort in descending order
            });
        }
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
        Object item = itemList.get(position);
        String person, date, amount;
        if(item instanceof Payment){
            person = ((Payment) item).getMerchant();
            date = ((Payment) item).getDate().toString();
            amount = String.valueOf(((Payment) item).getAmount());
        }
        else{
            if(((Transfer) item).getIdBankAccount() == ((Transfer) item).getFromAccountNumber()){
                person = ((Transfer) item).getToAccountNumber().getIdUser().getUserProfile().getName();
            }
            else person = ((Transfer) item).getFromAccountNumber().getIdUser().getUserProfile().getName();
            date = ((Transfer) item).getDate().toString();
            amount= String.valueOf(((Transfer) item).getAmount());
        }
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
