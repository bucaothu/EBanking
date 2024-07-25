package com.bank.ebanking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.AdapterBankAccounts;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.InterestRate;
import com.bank.ebanking.model.SavingAccount;

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
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            bankAccounts.addAll((ArrayList<BankAccount>) bundle.getSerializable("bankAccounts"));
//        }
        bankAccounts = (ArrayList<BankAccount>) getActivity().getIntent().getSerializableExtra("bankAccounts");
        adapterBankAccounts = new AdapterBankAccounts(bankAccounts);
        recyclerView.setAdapter(adapterBankAccounts);

        return view;
    }
}
