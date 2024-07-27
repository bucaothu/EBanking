package com.bank.ebanking.intent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.bank.ebanking.model.UserProfile;
import com.bank.ebanking.services.Services.AuthenticationService;
import com.bank.ebanking.services.Services.TransactionService;
import com.bank.ebanking.services.Services.UserService;
import com.bank.ebanking.services.Services.UserSessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IntentVerifyPhone extends AppCompatActivity {
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth myAuth;
    EditText codeInput1, codeInput2, codeInput3, codeInput4, codeInput5, codeInput6;
    String verificationID;
    TextView btnBack;
    Button btnVerify, btnResendOPT;
    String codeInput;
    Map<String, String> signUpInfos = new HashMap<>();
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//            signinbyCredentials(credential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                System.out.println(e);
            } else if (e instanceof FirebaseTooManyRequestsException) {
                System.out.println(e);
            } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                System.out.println(e);
            }
            System.out.println(e);
            Toast.makeText(IntentVerifyPhone.this, "Vertification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(IntentVerifyPhone.this, "sent OPT success", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        myAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                signUpInfos.put(key, bundle.getString(key));
            }
        }
        sendOtp(signUpInfos.get("phone"));
        setContentView(R.layout.auth_verify_otp_activity);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

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
                    Toast.makeText(IntentVerifyPhone.this, "Vui long nhap day du ma OPT", Toast.LENGTH_SHORT).show();
                    return;
                }

                codeInput = codeInput1.getText().toString().trim() +
                        codeInput2.getText().toString().trim() +
                        codeInput3.getText().toString().trim() +
                        codeInput4.getText().toString().trim() +
                        codeInput5.getText().toString().trim() +
                        codeInput6.getText().toString().trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, codeInput);
                signinbyCredentials(credential);
            }
        });

        btnResendOPT.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                sendOtp(signUpInfos.get("phone"));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.setClass(IntentVerifyPhone.this, IntentSignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(myAuth)
                        .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                    firebaseAuth.getCurrentUser().
//                            unlink(PhoneAuthProvider.PROVIDER_ID);
                    AuthenticationService.register(signUpInfos, IntentVerifyPhone.this);
                } else {
                    System.out.println(task.getException().toString());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
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