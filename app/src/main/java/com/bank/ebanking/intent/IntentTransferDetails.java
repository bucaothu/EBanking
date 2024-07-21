package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.SpinnerBankAccountAdapter;
import com.bank.ebanking.model.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class IntentTransferDetails extends AppCompatActivity {
    Spinner spinner;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);
        setControl();
        setEvent();
    }

    private void setEvent() {
//        UserProfile userProfile1 = new UserProfile("John Doe", "1234567890", "john.doe@example.com", "CCCD123");
//        UserProfile userProfile2 = new UserProfile("Jane Smith", "0987654321", "jane.smith@example.com", "CCCD456");
//        User user1 = new User("john_doe", "password123", "1234", true, userProfile1);
//        User user2 = new User("jane_smith", "password456", "5678", true, userProfile2);
//
//        BankAccount account1 = new BankAccount("ACC001", 1500.50f, user1);
//        BankAccount account2 = new BankAccount("ACC002", 2500.00f, user1);
//        BankAccount account3 = new BankAccount("ACC003", 3500.75f, user2);
//        BankAccount account4 = new BankAccount("ACC004", 4500.20f, user2);
//        BankAccount account5 = new BankAccount("ACC005", 5500.90f, user1);

        // Add BankAccount objects to a list
        List<BankAccount> bankAccounts = new ArrayList<>();
//        bankAccounts.add(account1);
//        bankAccounts.add(account2);
//        bankAccounts.add(account3);
//        bankAccounts.add(account4);
//        bankAccounts.add(account5);
        SpinnerBankAccountAdapter adapter = new SpinnerBankAccountAdapter(this, bankAccounts);
        spinner.setAdapter(adapter);
        btnBack.setOnClickListener(view -> {
            Intent transferMain= new Intent(IntentTransferDetails.this, IntentTransferMain.class);
            startActivity(transferMain);
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        });
    }

    private void setControl() {
        spinner = findViewById(R.id.spinner_bank_accounts);
        btnBack = findViewById(R.id.btn_back_transfer_main);
    }
}
