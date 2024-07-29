package com.bank.ebanking.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.AdapterBill;
import com.bank.ebanking.intent.IntentBankAccount;
import com.bank.ebanking.intent.IntentPayment;
import com.bank.ebanking.intent.IntentTransferMain;
import com.bank.ebanking.model.Transaction;
import com.bank.ebanking.model.User;
import com.bank.ebanking.services.Services.BankAccountService;
import com.bank.ebanking.services.Services.UserSessionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FragmentMain extends Fragment {
    LinearLayout llBankAccount, llTransfer, llPayment;
    TextView tvCurrDate, tvName;
    private AdapterBill adapterBill;
    private RecyclerView recyclerView;
    private List<Transaction> transactions;
    private User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_bill);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Intent intent = getActivity().getIntent();
        transactions = (List<Transaction>) intent.getSerializableExtra("transactions");
        user = (User) intent.getSerializableExtra("user");
        adapterBill = new AdapterBill(transactions);
        recyclerView.setAdapter(adapterBill);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        tvCurrDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        tvName.setText("Xin chào " + user.getUserProfile().getName());
        llBankAccount.setOnClickListener(view -> {
            BankAccountService.getBankAccounts(UserSessionManager.getUsername(), view.getContext(), new IntentBankAccount());
        });

        llTransfer.setOnClickListener(view -> {
            Intent transfer= new Intent(view.getContext(), IntentTransferMain.class);
            startActivity(transfer);
        });

        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getActivity().getIntent();
                intent.setClass(getContext(), IntentPayment.class);
                startActivity(intent);
            }
        });
    }

    private void setControl(View view) {
        llBankAccount = view.findViewById(R.id.llBankAccount);
        llTransfer = view.findViewById(R.id.ll_transaction);
        llPayment = view.findViewById(R.id.linear_layout_payment);
        tvCurrDate = view.findViewById(R.id.text_view_date_main);
        tvName = view.findViewById(R.id.text_view_name);
    }

}
