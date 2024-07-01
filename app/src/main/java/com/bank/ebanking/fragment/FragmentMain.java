package com.bank.ebanking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.AdapterBill;
import com.bank.ebanking.intent.IntentBankAccount;
import com.bank.ebanking.intent.IntentMainScreen;
import com.bank.ebanking.intent.IntentTransferMain;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.Payment;
import com.bank.ebanking.model.Transfer;
import com.bank.ebanking.model.User;
import com.bank.ebanking.model.UserProfile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FragmentMain extends Fragment {
    LinearLayout llBankAccount, llTransfer;

    private AdapterBill adapterBill;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_bill);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserProfile userProfile1= new UserProfile("hello1", "238759", "user1@email", "82374");
        UserProfile userProfile2= new UserProfile("hello2", "238759", "user2@email", "82374");
        User user1 = new User("skjdfh", "ksjfdb", "298734", true, userProfile1);
        User user2 = new User("skjdfh", "ksjfdb", "298734", true, userProfile2);

        BankAccount bankAccount1 = new BankAccount("1212121212", 12, user1);
        BankAccount bankAccount2 = new BankAccount("0101010101", 12, user2);

        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("merchant", true, 12, new Date(2024,12,12), "", bankAccount1));
        payments.add(new Payment("merchant", true, 12, new Date(2024,12,12), "", bankAccount2));

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer(bankAccount1, bankAccount2, 12, new Date(2024,12,12), bankAccount1));
        transfers.add(new Transfer(bankAccount2, bankAccount1, 11, new Date(2024,12,12), bankAccount1));

        adapterBill = new AdapterBill(payments, transfers);
        recyclerView.setAdapter(adapterBill);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    private void setEvent() {
        llBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bankAccounts= new Intent(view.getContext(), IntentBankAccount.class);
                startActivity(bankAccounts);
            }
        });

        llTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transfer= new Intent(view.getContext(), IntentTransferMain.class);
                startActivity(transfer);
            }
        });
    }

    private void setControl(View view) {
        llBankAccount = view.findViewById(R.id.llBankAccount);
        llTransfer = view.findViewById(R.id.ll_transaction);
    }

}
