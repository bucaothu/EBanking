package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.model.User;
import com.bank.ebanking.services.Services.BankAccountService;

import java.util.HashMap;
import java.util.Map;

public class IntentAddBankAccount extends AppCompatActivity {
    TextView tvTitle, tvNote;
    EditText edtAccountNumber;
    ImageButton btnBack;
    Button btnConfirm;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        setControl();
        setEvent();
    }

    private void setEvent() {
        tvTitle.setText("Nhập số tài khoản bạn muốn");
        tvNote.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.GONE);
        btnConfirm.setText("Xác nhận");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String accountNumber = edtAccountNumber.getText().toString();
                if(!accountNumber.equals("") && accountNumber.length()!=11){
                    Toast.makeText(IntentAddBankAccount.this, "Hãy nhập số tài khoản đủ 11 số", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> data = new HashMap<>();
                    if (!accountNumber.equals("")){
                        data.put("accountNumber", accountNumber);
                    }
                    data.put("balance", 0);
                    User user = (User)getIntent().getSerializableExtra("user");
                    data.put("idUser", user.getIdUser());
                    BankAccountService.addBankAccount(data, IntentAddBankAccount.this, new IntentBankAccount());
                }
            }
        });
    }

    private void setControl() {
        tvTitle = findViewById(R.id.title_text);
        tvNote = findViewById(R.id.tvNote);
        btnBack = findViewById(R.id.btn_back_home);
        edtAccountNumber = findViewById(R.id.account_number_input);
        btnConfirm = findViewById(R.id.btn_continue_transfer);
    }
}