package com.bank.ebanking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bank.EBanking.R;
import com.bank.ebanking.model.BankAccount;

import java.util.List;

public class SpinnerBankAccountAdapter extends ArrayAdapter<BankAccount> {
    private final Context context;
    private final List<BankAccount> accounts;

    public SpinnerBankAccountAdapter(Context context, List<BankAccount> accounts) {
        super(context, 0, accounts);
        this.context = context;
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item_bank_accounts);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item_bank_accounts);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        BankAccount account = accounts.get(position);

        TextView accountName = convertView.findViewById(R.id.from_account_name);
        TextView accountNumber = convertView.findViewById(R.id.from_account_number);
        TextView accountBalance = convertView.findViewById(R.id.from_account_balance);

        accountName.setText(account.getIdUser().getUserProfile().getName()); // Replace with actual account holder name if available
        accountNumber.setText(account.getAccountNumber());
        accountBalance.setText(String.format("%.2f VND", account.getBalance()));

        return convertView;
    }
}
