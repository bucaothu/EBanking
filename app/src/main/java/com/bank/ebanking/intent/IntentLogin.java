package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;

public class IntentLogin extends AppCompatActivity {
    EditText username, password;
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
                Intent mainScreen= new Intent(IntentLogin.this, IntentMainScreen.class);
                startActivity(mainScreen);
                finish();
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
        username=findViewById(R.id.edtUsername);
        password=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnSignUp);
    }

}
