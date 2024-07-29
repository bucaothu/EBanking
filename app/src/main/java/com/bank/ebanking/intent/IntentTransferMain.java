package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.services.Services.BankAccountService;
import com.bank.ebanking.services.Services.UserSessionManager;

public class IntentTransferMain extends AppCompatActivity {
    Button btnContinueTransfer;
    ImageButton btnBackHome;
    EditText edtAccountNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        setControl();
        setEvent();
    }

    private void setEvent() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("") || source.toString().matches("[0-9]*")) {
                    return null; // Accept the input
                }
                return ""; // Reject the input
            }
        };
        edtAccountNumber.setFilters(new InputFilter[]{filter});
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainScreen= new Intent(IntentTransferMain.this, IntentMainScreen.class);
                startActivity(mainScreen);
            }
        });

        btnContinueTransfer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(edtAccountNumber.getText().toString().length()!=11){
                    Toast.makeText(IntentTransferMain.this, getResources().getString(R.string.toast_11_number_account), Toast.LENGTH_SHORT).show();
                }
                BankAccountService.getBankAccount(edtAccountNumber.getText().toString(), UserSessionManager.getUsername(), IntentTransferMain.this, new IntentTransferDetails());
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });
    }

    private void setControl() {
        btnContinueTransfer = findViewById(R.id.btn_continue_transfer);
        btnBackHome = findViewById(R.id.btn_back_home);
        edtAccountNumber = findViewById(R.id.account_number_input);
    }
}
