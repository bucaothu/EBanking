package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.SpinnerBankAccountAdapter;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.services.Services.TransactionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentTopUp extends AppCompatActivity {
    EditText edtAmount, edtPhone;
    Spinner spinnerBankAccounts, spinnerTelcoType;
    TextView tvNoBankAccount;
    Button btnTopUp;
    String selectedType;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        List<BankAccount> bankAccounts = (List<BankAccount>) intent.getSerializableExtra("bankAccounts");
        if(bankAccounts.size()==0){
            spinnerBankAccounts.setVisibility(View.GONE);
            tvNoBankAccount.setVisibility(View.VISIBLE);
            btnTopUp.setVisibility(View.GONE);
        }
        else {
            SpinnerBankAccountAdapter adapter = new SpinnerBankAccountAdapter(this, bankAccounts);
            spinnerBankAccounts.setAdapter(adapter);
            edtPhone.setText(bankAccounts.get(0).getIdUser().getUserProfile().getPhone());
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
        List<String> telcoType = new ArrayList<>();
        telcoType.add(getResources().getString(R.string.label_prepaid_phone));
        telcoType.add(getResources().getString(R.string.label_postpaid_phone));
        selectedType = telcoType.get(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_text, telcoType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTelcoType.setAdapter(adapter);
        spinnerTelcoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedType = telcoType.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                String telco = getNetworkProviders(edtPhone.getText().toString());
                if (telco.equals(getResources().getString(R.string.toast_cannnot_find))){
                    Toast.makeText(IntentTopUp.this, getResources().getString(R.string.toast_undefined_phone), Toast.LENGTH_SHORT).show();
                } else if (edtAmount.getText().toString().isEmpty()) {
                    Toast.makeText(IntentTopUp.this, getResources().getString(R.string.toast_no_amount), Toast.LENGTH_SHORT).show();
                } else if (!edtAmount.getText().toString().equals("10000") &&
                            !edtAmount.getText().toString().equals("20000") &&
                            !edtAmount.getText().toString().equals("30000") &&
                            !edtAmount.getText().toString().equals("50000") &&
                            !edtAmount.getText().toString().equals("100000") &&
                            !edtAmount.getText().toString().equals("200000") &&
                            !edtAmount.getText().toString().equals("300000") &&
                            !edtAmount.getText().toString().equals("500000") &&
                            !edtAmount.getText().toString().equals("1000000")){
                    Toast.makeText(IntentTopUp.this, getResources().getString(R.string.toast_unacceptable_amount), Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(edtAmount.getText().toString()) > selectedBankAccount[0].getBalance()) {
                    Toast.makeText(IntentTopUp.this, getResources().getString(R.string.toast_not_enough_money), Toast.LENGTH_SHORT).show();
                } else {
                    data.put("telco", getNetworkProviders(edtPhone.getText().toString()));
                    if(selectedType.equals(getResources().getString(R.string.label_prepaid_phone)))
                        data.put("telcoServiceType", "prepaid");
                    else data.put("telcoServiceType", "postpaid");
                    data.put("phoneNumber", edtPhone.getText().toString());
                    data.put("amount", Integer.parseInt(edtAmount.getText().toString()));
                    data.put("accountNumber", selectedBankAccount[0].getAccountNumber());
                    TransactionService.topUp(data, IntentTopUp.this);
                }
            }
        });
    }

    private void setControl() {
        edtAmount = findViewById(R.id.edtAmount);
        edtPhone = findViewById(R.id.edtPhone);
        btnTopUp = findViewById(R.id.buttonTopUp);
        spinnerBankAccounts = findViewById(R.id.spinner_bank_accounts);
        spinnerTelcoType = findViewById(R.id.spinner_telco_type);
        tvNoBankAccount = findViewById(R.id.noBankAccountTextView);
    }

    public String getNetworkProviders(String phoneNumber) {
        // Xóa bỏ ký tự không phải số
        phoneNumber = phoneNumber.replaceAll("\\D", "");

        // Tạo một bản đồ để lưu các đầu số và nhà mạng tương ứng
        Map<String, String> networkMap = new HashMap<>();
        networkMap.put("098", "viettel");
        networkMap.put("097", "viettel");
        networkMap.put("096", "viettel");
        networkMap.put("095", "viettel");
        networkMap.put("094", "viettel");
        networkMap.put("093", "viettel");
        networkMap.put("091", "viettel");
        networkMap.put("090", "viettel");
        networkMap.put("089", "viettel");
        networkMap.put("088", "viettel");
        networkMap.put("087", "viettel");
        networkMap.put("086", "viettel");
        networkMap.put("085", "vinaphone");
        networkMap.put("084", "vinaphone");
        networkMap.put("083", "vinaphone");
        networkMap.put("082", "vinaphone");
        networkMap.put("081", "vinaphone");
        networkMap.put("080", "vinaphone");
        networkMap.put("078", "vinaphone");
        networkMap.put("077", "vinaphone");
        networkMap.put("076", "vinaphone");
        networkMap.put("075", "vinaphone");
        networkMap.put("074", "vinaphone");
        networkMap.put("073", "vinaphone");
        networkMap.put("072", "vinaphone");
        networkMap.put("071", "vinaphone");
        networkMap.put("070", "vinaphone");
        networkMap.put("099", "mobifone");
        networkMap.put("098", "mobifone");
        networkMap.put("097", "mobifone");
        networkMap.put("096", "mobifone");
        networkMap.put("091", "mobifone");
        networkMap.put("090", "mobifone");
        networkMap.put("089", "mobifone");
        networkMap.put("088", "mobifone");
        networkMap.put("087", "mobifone");
        networkMap.put("086", "mobifone");
        networkMap.put("085", "vnmobile");
        networkMap.put("084", "vnmobile");
        networkMap.put("083", "vnmobile");
        networkMap.put("082", "vnmobile");
        networkMap.put("081", "vnmobile");
        networkMap.put("080", "vnmobile");
        networkMap.put("078", "vnmobile");
        networkMap.put("077", "vnmobile");
        networkMap.put("076", "vnmobile");
        networkMap.put("075", "vnmobile");
        networkMap.put("074", "vnmobile");
        networkMap.put("073", "vnmobile");
        networkMap.put("072", "vnmobile");
        networkMap.put("071", "vnmobile");
        networkMap.put("070", "vnmobile");

        List<String> networks = new ArrayList<>();

        for (String prefix : networkMap.keySet()) {
            if (phoneNumber.startsWith(prefix)) {
                return networkMap.get(prefix);
            }
        }
        return getResources().getString(R.string.toast_cannnot_find);
    }
}
