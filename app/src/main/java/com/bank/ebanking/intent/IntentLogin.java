package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.services.Services.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

public class IntentLogin extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> loginInfo = new HashMap<>();
                loginInfo.put("username", edtUsername.getText().toString());
                loginInfo.put("password", edtPassword.getText().toString());
                AuthenticationService.login(loginInfo, IntentLogin.this);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp= new Intent(IntentLogin.this, IntentSignUp.class);
                startActivity(signUp);
                finish();
            }
        });
    }

    private void setControl() {
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnSignUp);
    }

}
