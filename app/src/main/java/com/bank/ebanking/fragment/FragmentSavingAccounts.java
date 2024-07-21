package com.bank.ebanking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;

import com.bank.ebanking.adapter.AdapterSavingAccounts;
import com.bank.ebanking.model.InterestRate;
import com.bank.ebanking.model.SavingAccount;
import java.util.ArrayList;
import java.util.List;

public class FragmentSavingAccounts extends Fragment {
    public FragmentSavingAccounts() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saving_accounts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_saving_accounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        UserProfile userProfile1 = new UserProfile("John Doe", "1234567890", "john.doe@example.com", "CCCD123");
//        UserProfile userProfile2 = new UserProfile("Jane Smith", "0987654321", "jane.smith@example.com", "CCCD456");
//
//        User user1 = new User("john_doe", "password123", "1234", true, userProfile1);
//        User user2 = new User("jane_smith", "password456", "5678", true, userProfile2);
//
//        BankAccount account1 = new BankAccount("ACC001", 1500.50f, user1);
//        BankAccount account2 = new BankAccount("ACC002", 2500.00f, user1);
//        BankAccount account3 = new BankAccount("ACC003", 3500.75f, user2);
//        BankAccount account4 = new BankAccount("ACC004", 4500.20f, user2);
//        BankAccount account5 = new BankAccount("ACC005", 5500.90f, user1);
//
//        // Add BankAccount objects to a list
//        SavingAccountType type1 = new SavingAccountType(1, "Regular", "Regular Savings Account", 0.5f);
//        SavingAccountType type2 = new SavingAccountType(2, "Premium", "Premium Savings Account", 1.0f);
//
//        // Create InterestRate objects
//        Date now = new Date();
//        Date later = new Date(now.getTime() + (1000L * 60 * 60 * 24 * 30 * 10)); // 30 days later
//        InterestRate interestRate1 = new InterestRate(3, 1000.0f, 0.75f, now, later, type1);
//        InterestRate interestRate2 = new InterestRate(6, 2000.0f, 1.25f, now, later, type2);
//
//        // Create SavingAccount objects
//        SavingAccount savingAccount1 = new SavingAccount("SAV001", 3000.00f, now, later, 0.75f, true, account1, type1);
//        SavingAccount savingAccount2 = new SavingAccount("SAV002", 5000.00f, now, later, 1.25f, true, account2, type2);
//        SavingAccount savingAccount3 = new SavingAccount("SAV003", 7000.00f, now, later, 0.75f, true, account3, type1);
//        SavingAccount savingAccount4 = new SavingAccount("SAV004", 9000.00f, now, later, 1.25f, true, account4, type2);
//        SavingAccount savingAccount5 = new SavingAccount("SAV005", 11000.00f, now, later, 0.75f, true, account5, type1);

        // Add SavingAccount objects to a list
        List<SavingAccount> savingAccounts = new ArrayList<>();
//        savingAccounts.add(savingAccount1);
//        savingAccounts.add(savingAccount2);
//        savingAccounts.add(savingAccount3);
//        savingAccounts.add(savingAccount4);
//        savingAccounts.add(savingAccount5);

        List<InterestRate> interestRates = new ArrayList<>();
//        interestRates.add(interestRate1);
//        interestRates.add(interestRate2);

        AdapterSavingAccounts adapterSavingAccounts = new AdapterSavingAccounts(savingAccounts, interestRates);
        recyclerView.setAdapter(adapterSavingAccounts);

        return view;
    }
}
