package com.bank.ebanking.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.adapter.SpinnerBankAccountAdapter;
import com.bank.ebanking.adapter.SpinnerSavingAccountTypeAdapter;
import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.InterestRate;
import com.bank.ebanking.model.SavingAccountType;

import java.util.Calendar;
import java.util.List;

public class IntentAddSavingAccount extends AppCompatActivity {
    Spinner spinnerSavingTypes, spinnerBankAccounts;
    TextView tvNoBankAccount, tvNotifyIR;
    Button btnAgree;
    EditText edtAccountNumber, edtTermMonths, edtAmount;
    TableLayout tblInterestRates;
    List<BankAccount> bankAccounts;
    List<SavingAccountType> savingAccountTypes;
    List<InterestRate> interestRates;

    BankAccount selectedBankAccount;
    SavingAccountType selectedSavingAccountType;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        bankAccounts = (List<BankAccount>) intent.getSerializableExtra("bankAccounts");
        savingAccountTypes = (List<SavingAccountType>) intent.getSerializableExtra("savingAccountTypes");
        interestRates = (List<InterestRate>) intent.getSerializableExtra("interestRates");

        selectedSavingAccountType = (SavingAccountType) spinnerSavingTypes.getItemAtPosition(0);
        Context context = tblInterestRates.getContext();
        addInterestRates(context, selectedSavingAccountType);

        SpinnerSavingAccountTypeAdapter adapterSaving = new SpinnerSavingAccountTypeAdapter(this, savingAccountTypes);
        spinnerSavingTypes.setAdapter(adapterSaving);

        if(bankAccounts.size()==0){
            spinnerBankAccounts.setVisibility(View.GONE);
            tvNoBankAccount.setVisibility(View.VISIBLE);
            btnAgree.setVisibility(View.GONE);
        }
        else {
            SpinnerBankAccountAdapter adapter = new SpinnerBankAccountAdapter(this, bankAccounts);
            spinnerBankAccounts.setAdapter(adapter);
        }
        spinnerSavingTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                while (tblInterestRates.getChildCount() > 1) {
                    tblInterestRates.removeViewAt(1);
                }
                selectedSavingAccountType = (SavingAccountType) adapterView.getItemAtPosition(i);
                Context context = tblInterestRates.getContext();
                addInterestRates(context, selectedSavingAccountType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerBankAccounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBankAccount = (BankAccount) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAmount.getText().toString().equals("")){
                    Toast.makeText(context, "Hãy nhập lượng tiền", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(edtAmount.getText().toString()) > selectedBankAccount.getBalance()) {
                    Toast.makeText(context, "Lượng tiền muốn gửi lớn hơn số dư", Toast.LENGTH_SHORT).show();
                }
                int amount = Integer.parseInt(edtAmount.getText().toString());
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkAndSetTextView();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        edtTermMonths.addTextChangedListener(textWatcher);
        edtAmount.addTextChangedListener(textWatcher);
    }

    private void setControl() {
        spinnerBankAccounts = findViewById(R.id.spinner_bank_accounts);
        spinnerSavingTypes = findViewById(R.id.spinner_saving_types);
        tvNoBankAccount = findViewById(R.id.noBankAccountTextView);
        tvNotifyIR = findViewById(R.id.tvNotifyIR);
        btnAgree = findViewById(R.id.btnAgree);
        tblInterestRates = findViewById(R.id.tlInterestRates);
        edtAccountNumber = findViewById(R.id.edtAccountNumber);
        edtTermMonths = findViewById(R.id.edtTermMonths);
        edtAmount = findViewById(R.id.edtAmount);
    }

    private void addInterestRates(Context context, SavingAccountType savingAccountType){
        for (InterestRate interestRate: interestRates) {
            if (interestRate.getIdSavingAccountType().getIdSavingAccountType() != savingAccountType.getIdSavingAccountType()
                    || interestRate.getEndDate().before(Calendar.getInstance().getTime())) {
                continue;
            }
            TableRow row = new TableRow(context);

            TextView tvMonths = new TextView(context);
            tvMonths.setText(interestRate.getTermMonths());
            tvMonths.setGravity(Gravity.CENTER);
            tvMonths.setTextSize(18);
            TableRow.LayoutParams monthParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
            tvMonths.setLayoutParams(monthParams);

            TextView tvAmount = new TextView(context);
            tvAmount.setText((int)interestRate.getMinBalance());
            tvAmount.setTextSize(18);
            TableRow.LayoutParams amountParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
            tvAmount.setLayoutParams(amountParams);

            TextView tvInterestRate = new TextView(context);
            tvInterestRate.setText(String.valueOf(interestRate.getInterestRate()));
            tvInterestRate.setGravity(Gravity.CENTER);
            tvInterestRate.setTextSize(18);
            TableRow.LayoutParams countParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
            tvInterestRate.setLayoutParams(countParams);

            row.addView(tvMonths);
            row.addView(tvAmount);
            row.addView(tvInterestRate);

            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            rowParams.setMargins(0, 10, 0, 10);
            row.setLayoutParams(rowParams);

            tblInterestRates.addView(row);
        }
    }

    private void checkAndSetTextView() {
        String amount = edtAmount.getText().toString();
        String termMonths = edtTermMonths.getText().toString();

        if (!amount.isEmpty() && !termMonths.isEmpty()) {
            tvNotifyIR.setText("Both fields are filled");
        } else {
            tvNotifyIR.setText("");
        }
    }
}
