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
import com.bank.ebanking.model.BankAccount;
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
        ArrayList<SavingAccount> savingAccounts = new ArrayList<>();
        ArrayList<InterestRate> interestRates = new ArrayList<>();
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            savingAccounts.addAll((ArrayList<SavingAccount>) bundle.getSerializable("savingAccounts"));
//            interestRates.addAll((ArrayList<InterestRate>) bundle.getSerializable("interestRates"));
//        }
//        bankAccounts = (ArrayList<BankAccount>) intent.getSerializableExtra("bankAccounts");
        savingAccounts = (ArrayList<SavingAccount>) getActivity().getIntent().getSerializableExtra("savingAccounts");
        interestRates = (ArrayList<InterestRate>) getActivity().getIntent().getSerializableExtra("InterestRates");
        AdapterSavingAccounts adapterSavingAccounts = new AdapterSavingAccounts(savingAccounts, interestRates, getContext());
        recyclerView.setAdapter(adapterSavingAccounts);
        return view;
    }
}
