package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.SpinnerBankAccountAdapter;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.BillDetail;
import com.bank.ebanking.services.Services.TransactionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentBillDetails extends AppCompatActivity {
    TextView billId, billAmount, billPeriod, tvNoBankAccount;
    Spinner spinnerBankAccounts;
    Button btnTransfer;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        BillDetail billDetail = (BillDetail) intent.getSerializableExtra("billDetail");
        String partnerRefId = intent.getStringExtra("partnerRefId");
        String billCode = intent.getStringExtra("billCode");
        String serviceCode = intent.getStringExtra("serviceCode");
        billAmount.setText(String.valueOf(billDetail.getAmount()));
        billId.setText(billDetail.getBillNumber());
        billPeriod.setText(billDetail.getPeriod());
        List<BankAccount> bankAccounts = (List<BankAccount>) intent.getSerializableExtra("bankAccounts");
        if(bankAccounts.size()==0){
            spinnerBankAccounts.setVisibility(View.GONE);
            tvNoBankAccount.setVisibility(View.VISIBLE);
            btnTransfer.setVisibility(View.GONE);
        }
        else {
            SpinnerBankAccountAdapter adapter = new SpinnerBankAccountAdapter(this, bankAccounts);
            spinnerBankAccounts.setAdapter(adapter);
        }
        final BankAccount[] selectedBankAccount = {new BankAccount()};
        spinnerBankAccounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBankAccount[0] = (BankAccount) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(selectedBankAccount[0].getBalance()<billDetail.getAmount()){
                    Toast.makeText(IntentBillDetails.this, "Số dư tài khoản không đủ", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("accountNumber", selectedBankAccount[0].getAccountNumber());
                    data.put("partnerRefId", partnerRefId);
                    data.put("serviceCode", serviceCode);
                    data.put("billCode", billCode);
                    data.put("amount", billDetail.getAmount());
                    data.put("billDetail", billDetail);
                    TransactionService.payBill(data, IntentBillDetails.this);
                }
            }
        });
    }

    private void setControl() {
        billAmount = findViewById(R.id.billAmount);
        billId = findViewById(R.id.billId);
        billPeriod = findViewById(R.id.billPeriod);
        btnTransfer = findViewById(R.id.cbtn_finish_transfer);
        spinnerBankAccounts = findViewById(R.id.spinner_bank_accounts);
        tvNoBankAccount = findViewById(R.id.noBankAccountTextView);
    }
}
