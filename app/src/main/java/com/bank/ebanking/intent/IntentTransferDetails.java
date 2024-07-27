package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.SpinnerBankAccountAdapter;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.services.Services.TransactionService;
import com.bank.ebanking.services.Services.UserSessionManager;

import java.util.List;

public class IntentTransferDetails extends AppCompatActivity {
    Spinner spinner;
    ImageButton btnBack;
    EditText edtTransferAmount, edtTransferDetail;
    TextView toPerson, toAccount, tvNoBankAccount;
    Button btnFinishTransfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        List<BankAccount> bankAccounts = (List<BankAccount>) intent.getSerializableExtra("bankAccounts");
        BankAccount toBankAccount = (BankAccount) intent.getSerializableExtra("bankAccount");
        toPerson.setText(toBankAccount.getIdUser().getUserProfile().getName());
        toAccount.setText(toBankAccount.getAccountNumber());
        if(bankAccounts.size()==0){
            spinner.setVisibility(View.GONE);
            tvNoBankAccount.setVisibility(View.VISIBLE);
            btnFinishTransfer.setVisibility(View.GONE);
        }
        else {
            SpinnerBankAccountAdapter adapter = new SpinnerBankAccountAdapter(this, bankAccounts);
            spinner.setAdapter(adapter);
        }
        btnBack.setOnClickListener(view -> {
            Intent transferMain= new Intent(IntentTransferDetails.this, IntentTransferMain.class);
            startActivity(transferMain);
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        });
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("") || source.toString().matches("[0-9]*")) {
                    return null; // Accept the input
                }
                return ""; // Reject the input
            }
        };
        edtTransferAmount.setFilters(new InputFilter[]{filter});
        final BankAccount[] selectedBankAccount = {new BankAccount()};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBankAccount[0] = (BankAccount) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnFinishTransfer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(selectedBankAccount[0] == null){
                    Toast.makeText(IntentTransferDetails.this, "Xin hãy chọn một tài khoản để gửi", Toast.LENGTH_SHORT).show();
                } else if (edtTransferAmount.getText().toString().equals("")) {
                    Toast.makeText(IntentTransferDetails.this, "Xin hãy nhập số tiền gửi", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(edtTransferAmount.getText().toString())>selectedBankAccount[0].getBalance()) {
                    Toast.makeText(IntentTransferDetails.this, "Số tiền gửi lớn hơn số dư trong tài khoản", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(IntentTransferDetails.this, IntentVerifyOTP.class);
                    intent.putExtra("bankAccount", selectedBankAccount[0]);
                    intent.putExtra("toBankAccount", toBankAccount);
                    intent.putExtra("amount", Integer.parseInt(edtTransferAmount.getText().toString()));
                    intent.putExtra("detail", edtTransferDetail.getText().toString());
                    TransactionService.otp(UserSessionManager.getUsername(), IntentTransferDetails.this, intent);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }
        });
    }

    private void setControl() {
        spinner = findViewById(R.id.spinner_bank_accounts);
        btnBack = findViewById(R.id.btn_back_transfer_main);
        edtTransferAmount = findViewById(R.id.transferAmount);
        edtTransferDetail = findViewById(R.id.transferDetail);
        toPerson = findViewById(R.id.to_account_name);
        toAccount = findViewById(R.id.to_account_number);
        btnFinishTransfer = findViewById(R.id.cbtn_finish_transfer);
        tvNoBankAccount = findViewById(R.id.noBankAccountTextView);
    }
}
