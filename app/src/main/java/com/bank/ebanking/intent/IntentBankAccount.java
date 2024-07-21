package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bank.EBanking.R;
import com.bank.ebanking.fragment.FragmentBankAccounts;
import com.bank.ebanking.fragment.FragmentSavingAccounts;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.InterestRate;
import com.bank.ebanking.model.SavingAccount;
import com.bank.ebanking.model.Transaction;
import com.bank.ebanking.services.Services.TransactionService;
import com.bank.ebanking.services.Services.UserSessionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IntentBankAccount extends AppCompatActivity {
    ImageButton btnBack, btnAdd;
    Button buttonToFirst, buttonToSecond;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_accounts);
        if (savedInstanceState == null) {
            loadFragment(new FragmentBankAccounts());
        }
        setControl();
        setEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        buttonToFirst.setOnClickListener(v -> loadFragment(new FragmentBankAccounts()));
        buttonToSecond.setOnClickListener(v -> loadFragment(new FragmentSavingAccounts()));
        btnBack.setOnClickListener(view -> {
            IntentMainScreen intentMainScreen = new IntentMainScreen();
            TransactionService.getTransactions(UserSessionManager.getUsername(), this, intentMainScreen);
        });
    }

    private void setControl() {
        buttonToFirst = findViewById(R.id.btn_to_bank_accounts);
        buttonToSecond = findViewById(R.id.btn_to_saving_accounts);
        btnBack = findViewById(R.id.btn_back_home);
        btnAdd = findViewById(R.id.btn_add_account);
    }

    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        ArrayList<BankAccount> bankAccounts = (ArrayList<BankAccount>) intent.getSerializableExtra("bankAccounts");
        ArrayList<SavingAccount> savingAccounts = (ArrayList<SavingAccount>) intent.getSerializableExtra("savingAccounts");
        ArrayList<InterestRate> interestRates = (ArrayList<InterestRate>) intent.getSerializableExtra("InterestRates");

        bundle.putSerializable("bankAccounts", bankAccounts);
        bundle.putSerializable("savingAccounts", savingAccounts);
        bundle.putSerializable("interestRates", interestRates);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment.getClass()==FragmentBankAccounts.class)
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fl_bank_accounts, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
