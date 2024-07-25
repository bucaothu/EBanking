package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bank.EBanking.R;
import com.bank.ebanking.services.Services.UserService;
import com.bank.ebanking.services.Services.UserSessionManager;

public class IntentAddAccount extends AppCompatActivity {
    ConstraintLayout savingAccount, bankAccount;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        setControl();
        setEvent();
    }

    private void setEvent() {
        bankAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntentAddAccount.this, IntentAddBankAccount.class);
                UserService.getUser(UserSessionManager.getUsername(), IntentAddAccount.this, intent);
            }
        });
        savingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(IntentAddAccount.this, IntentAddSavingAccount.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        savingAccount = findViewById(R.id.addSaving);
        bankAccount = findViewById(R.id.addBank);
    }
}
