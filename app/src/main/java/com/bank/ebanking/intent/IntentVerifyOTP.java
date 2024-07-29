package com.bank.ebanking.intent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.services.Services.TransactionService;
import com.bank.ebanking.services.Services.UserSessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IntentVerifyOTP extends AppCompatActivity {
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth myAuth;
    EditText codeInput1, codeInput2, codeInput3, codeInput4, codeInput5, codeInput6;
    String verificationID;
    TextView btnBack;
    Button btnVerify, btnResendOPT;
    BankAccount bankAccount, toBankAccount;
    int amount;
    String description;
    String codeInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_verify_otp_activity);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        if (getIntent().getExtras() != null) {
            bankAccount = (BankAccount) getIntent().getSerializableExtra("bankAccount");
            toBankAccount = (BankAccount) getIntent().getSerializableExtra("toBankAccount");
            amount = getIntent().getIntExtra("amount", 0);
            description = getIntent().getStringExtra("description");
        }
        setControl();
        setEvent();
        setupOTPinputs();
    }

    private void setControl() {
        codeInput1 = findViewById(R.id.code_input_1);
        codeInput2 = findViewById(R.id.code_input_2);
        codeInput3 = findViewById(R.id.code_input_3);
        codeInput4 = findViewById(R.id.code_input_4);
        codeInput5 = findViewById(R.id.code_input_5);
        codeInput6 = findViewById(R.id.code_input_6);
        btnBack = findViewById(R.id.back);
        btnVerify = findViewById(R.id.btnVerify);
        btnResendOPT = findViewById(R.id.btnResendOPT);
    }

    private void setEvent() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (codeInput1.getText().toString().trim().isEmpty() || codeInput2.getText().toString().trim().isEmpty() ||
                        codeInput3.getText().toString().trim().isEmpty() || codeInput4.getText().toString().trim().isEmpty() ||
                        codeInput5.getText().toString().trim().isEmpty() || codeInput6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(IntentVerifyOTP.this, "Vui long nhap day du ma OPT", Toast.LENGTH_SHORT).show();
                    return;
                }

                codeInput = codeInput1.getText().toString().trim() +
                        codeInput2.getText().toString().trim() +
                        codeInput3.getText().toString().trim() +
                        codeInput4.getText().toString().trim() +
                        codeInput5.getText().toString().trim() +
                        codeInput6.getText().toString().trim();
                Map<String, Object> data = new HashMap<>();
                data.put("accountNumber", bankAccount.getAccountNumber());
                data.put("toAccountNumber", toBankAccount.getAccountNumber());
                data.put("amount", amount);
                data.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                data.put("description", description);
                data.put("idTransactionType", 1);
                data.put("otp", codeInput);
                TransactionService.transfer(data, IntentVerifyOTP.this, new IntentMainScreen());
            }
        });

        btnResendOPT.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.setClass(IntentVerifyOTP.this, IntentVerifyOTP.class);
                TransactionService.otp(UserSessionManager.getUsername(), IntentVerifyOTP.this, intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.setClass(IntentVerifyOTP.this, IntentTransferDetails.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupOTPinputs(){
        codeInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}