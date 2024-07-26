package com.bank.ebanking.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.bank.ebanking.services.Services.SavingAccountService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentAddSavingAccount extends AppCompatActivity {
    Spinner spinnerSavingTypes, spinnerBankAccounts;
    TextView tvNoBankAccount, tvNotifyIR;
    Button btnAgree;
    EditText edtAccountNumber, edtTermMonths, edtAmount;
    LinearLayout llTermMonths;
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
                if(i==0)    llTermMonths.setVisibility(View.GONE);
                else    llTermMonths.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        selectedSavingAccountType = (SavingAccountType) spinnerSavingTypes.getItemAtPosition(0);
        Context context = tblInterestRates.getContext();
        addInterestRates(context, selectedSavingAccountType);
        llTermMonths.setVisibility(View.GONE);
        btnAgree.setVisibility(View.GONE);
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("") || source.toString().matches("[0-9]*")) {
                    return null; // Accept the input
                }
                return ""; // Reject the input
            }
        };
        edtAmount.setFilters(new InputFilter[]{filter});
        edtTermMonths.setFilters(new InputFilter[]{filter});
        edtAccountNumber.setFilters(new InputFilter[]{filter});

        spinnerBankAccounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBankAccount = (BankAccount) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(edtAmount.getText().toString().equals("")){
                    Toast.makeText(context, "Hãy nhập lượng tiền", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(edtAmount.getText().toString()) > selectedBankAccount.getBalance()) {
                    Toast.makeText(context, "Lượng tiền muốn gửi lớn hơn số dư", Toast.LENGTH_SHORT).show();
                } else if(tvNotifyIR.getText().toString().equals("")){
                    Toast.makeText(context, "Chưa thể xác định lãi suất", Toast.LENGTH_SHORT).show();
                } else if(!edtAccountNumber.getText().toString().equals("") && edtAccountNumber.getText().toString().length()!=11) {
                    Toast.makeText(context, "Xin hãy nhập số tài khoản có 11 kí tự", Toast.LENGTH_SHORT).show();
                }   else {
                    int balance = Integer.parseInt(edtAmount.getText().toString());
                    String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    float interestRate = Float.parseFloat(tvNotifyIR.getText().toString().split(": ")[1].replace("%", "").trim());
                    String bankAccountNumber = selectedBankAccount.getAccountNumber();
                    String idSavingAccountType = String.valueOf(selectedSavingAccountType.getIdSavingAccountType());
                    Map<String, Object> data = new HashMap<>();
                    if(!edtAccountNumber.getText().toString().equals("") && edtAccountNumber.getText().toString().length()!=11){
                        data.put("accountNumber", edtAccountNumber.getText().toString());
                    }
                    data.put("balance", balance);
                    data.put("startDate", startDate);
                    if(selectedSavingAccountType.getIdSavingAccountType()!=1){
                        int termMonths = Integer.parseInt(edtTermMonths.getText().toString());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.MONTH, termMonths);
                        String endDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
                        data.put("endDate", endDate);
                    }
                    data.put("interestRate", interestRate);
                    data.put("status", 1);
                    data.put("bankAccountNumber", bankAccountNumber);
                    data.put("idSavingAccountType", idSavingAccountType);
                    SavingAccountService.addSavingAccount(data, IntentAddSavingAccount.this, new IntentBankAccount());
                }
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
        llTermMonths = findViewById(R.id.layout_termMonths);
        btnAgree = findViewById(R.id.btnAgree);
        tblInterestRates = findViewById(R.id.tlInterestRates);
        edtAccountNumber = findViewById(R.id.edtAccountNumber);
        edtTermMonths = findViewById(R.id.edtTermMonths);
        edtAmount = findViewById(R.id.edtAmount);
    }

    private void addInterestRates(Context context, SavingAccountType savingAccountType){
        for (InterestRate interestRate: interestRates) {
            if (interestRate.getIdSavingAccountType().getIdSavingAccountType() != savingAccountType.getIdSavingAccountType()) {
                continue;
            } else if(interestRate.getEndDate() != null){
                if(interestRate.getEndDate().before(Calendar.getInstance().getTime())){
                    continue;
                }
            }
            TableRow row = new TableRow(context);
            int width = 0;
            TextView tvMonths = new TextView(context);
            tvMonths.setText(String.valueOf(interestRate.getTermMonths()));
            tvMonths.setGravity(Gravity.CENTER);
            tvMonths.setTextSize(16);
//            tvMonths.setLayoutParams(new TableRow.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tvAmount = new TextView(context);
            tvAmount.setText(String.valueOf((int)interestRate.getMinBalance()));
            tvAmount.setTextSize(16);
            tvAmount.setGravity(Gravity.CENTER);
//            tvAmount.setLayoutParams(new TableRow.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));


            TextView tvInterestRate = new TextView(context);
            tvInterestRate.setText(String.valueOf(interestRate.getInterestRate()));
            tvInterestRate.setGravity(Gravity.CENTER);
            tvInterestRate.setTextSize(16);
//            tvInterestRate.setLayoutParams(new TableRow.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));

            row.addView(tvMonths);
            row.addView(tvAmount);
            row.addView(tvInterestRate);

            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            rowParams.setMargins(0, 10, 0, 10);
            row.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING|LinearLayout.SHOW_DIVIDER_MIDDLE| LinearLayout.SHOW_DIVIDER_END);
            row.setLayoutParams(rowParams);

            tblInterestRates.addView(row);
        }
    }

    private void checkAndSetTextView() {
        String amount = edtAmount.getText().toString();
        String termMonths = edtTermMonths.getText().toString();
        boolean isSet = false;
        if (!amount.isEmpty() && (!termMonths.isEmpty() || selectedSavingAccountType.getIdSavingAccountType() == 1)) {
            for(int i = 0 ; i<interestRates.size();i++){
                if(selectedSavingAccountType.getIdSavingAccountType() == interestRates.get(i).getIdSavingAccountType().getIdSavingAccountType()
                        && Integer.parseInt(amount) >= interestRates.get(i).getMinBalance()){
                    if(selectedSavingAccountType.getIdSavingAccountType() == 1 || Integer.parseInt(termMonths) > interestRates.get(i).getTermMonths()){
                        tvNotifyIR.setText("Lãi suất của bạn sẽ là: "+String.valueOf(interestRates.get(i).getInterestRate())+"%");
                        btnAgree.setVisibility(View.VISIBLE);
                        isSet = true;
                    }
                }
            }
            if(!isSet){
                tvNotifyIR.setText("Thông tin bạn nhập hiện không có mức lãi suất phù hợp");
                btnAgree.setVisibility(View.GONE);
            }
        } else {
            tvNotifyIR.setText("");
            btnAgree.setVisibility(View.GONE);
        }
    }
}
