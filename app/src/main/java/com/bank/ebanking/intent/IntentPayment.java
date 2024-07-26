package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;

public class IntentPayment extends AppCompatActivity {
    LinearLayout llBillPayment, llTopUp;
    ImageButton btnBack;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payments);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(IntentPayment.this, IntentMainScreen.class);
                startActivity(intent);
            }
        });
        llBillPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(IntentPayment.this, IntentPayBill.class);
                startActivity(intent);
            }
        });
        llTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setControl() {
        llBillPayment = findViewById(R.id.llBillPayment);
        llTopUp = findViewById(R.id.llTopUp);
        btnBack = findViewById(R.id.btn_back_home);
    }
}
