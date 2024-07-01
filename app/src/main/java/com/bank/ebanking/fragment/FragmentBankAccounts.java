package com.bank.ebanking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.AdapterBankAccounts;
import com.bank.ebanking.adapter.AdapterBill;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.Payment;
import com.bank.ebanking.model.Transfer;
import com.bank.ebanking.model.User;
import com.bank.ebanking.model.UserProfile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FragmentBankAccounts extends Fragment {
    private AdapterBankAccounts adapterBankAccounts;
    private RecyclerView recyclerView;
    public FragmentBankAccounts(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_accounts, container, false);
        recyclerView = view.findViewById(R.id.rv_bank_accounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserProfile userProfile1 = new UserProfile("John Doe", "1234567890", "john.doe@example.com", "CCCD123");
        UserProfile userProfile2 = new UserProfile("Jane Smith", "0987654321", "jane.smith@example.com", "CCCD456");

        User user1 = new User("john_doe", "password123", "1234", true, userProfile1);
        User user2 = new User("jane_smith", "password456", "5678", true, userProfile2);

        BankAccount account1 = new BankAccount("ACC001", 1500.50f, user1);
        BankAccount account2 = new BankAccount("ACC002", 2500.00f, user1);
        BankAccount account3 = new BankAccount("ACC003", 3500.75f, user2);
        BankAccount account4 = new BankAccount("ACC004", 4500.20f, user2);
        BankAccount account5 = new BankAccount("ACC005", 5500.90f, user1);

        // Add BankAccount objects to a list
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(account1);
        bankAccounts.add(account2);
        bankAccounts.add(account3);
        bankAccounts.add(account4);
        bankAccounts.add(account5);
        adapterBankAccounts = new AdapterBankAccounts(bankAccounts);
        recyclerView.setAdapter(adapterBankAccounts);

        return view;
    }
}
