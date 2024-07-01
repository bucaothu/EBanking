package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bank.EBanking.R;
import com.bank.ebanking.fragment.FragmentBankAccounts;
import com.bank.ebanking.fragment.FragmentSavingAccounts;

public class IntentBankAccount extends AppCompatActivity {
    ImageButton btnBack, btnAdd;
    Button buttonToFirst, buttonToSecond;
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

    private void setEvent() {
        buttonToFirst = findViewById(R.id.btn_to_bank_accounts);
        buttonToSecond = findViewById(R.id.btn_to_saving_accounts);
        btnBack = findViewById(R.id.btn_back_home);
        btnAdd = findViewById(R.id.btn_add_account);
    }

    private void setControl() {
        buttonToFirst.setOnClickListener(v -> loadFragment(new FragmentBankAccounts()));
        buttonToSecond.setOnClickListener(v -> loadFragment(new FragmentSavingAccounts()));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainScreen= new Intent(IntentBankAccount.this, IntentMainScreen.class);
                startActivity(mainScreen);
                finish();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
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
