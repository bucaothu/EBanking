package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.model.UserProfile;
import com.bank.ebanking.services.Services.UserService;
import com.bank.ebanking.services.Services.UserSessionManager;

import java.util.HashMap;
import java.util.Map;

public class IntentChangeProfile extends AppCompatActivity {
    private EditText edtName, edtCccd, edtPhone, edtEmail;
    private Button btnUpdateProfile;
    private UserProfile userProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = this.getIntent();
        userProfile = (UserProfile) intent.getSerializableExtra("userProfile");
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("") || source.toString().matches("[0-9]*")) {
                    return null; // Accept the input
                }
                return ""; // Reject the input
            }
        };
        edtPhone.setFilters(new InputFilter[]{filter});
        edtCccd.setFilters(new InputFilter[]{filter});
        edtName.setText(userProfile.getName());
        edtName.setHint(userProfile.getName());
        edtEmail.setText(userProfile.getEmail());
        edtEmail.setHint(userProfile.getEmail());
        edtPhone.setText(userProfile.getPhone());
        edtPhone.setHint(userProfile.getPhone());
        edtCccd.setText(userProfile.getCccd());
        edtCccd.setHint(userProfile.getCccd());
        TextWatcher textWatcherName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(userProfile.getName())){
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                } else if (checkSame(1)) {
                    btnUpdateProfile.setVisibility(View.INVISIBLE);
                }
            }
        };
        TextWatcher textWatcherCccd = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(userProfile.getCccd())){
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                } else if (checkSame(2)) {
                    btnUpdateProfile.setVisibility(View.INVISIBLE);
                }
            }
        };
        TextWatcher textWatcherEmail = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(userProfile.getEmail())){
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                } else if (checkSame(3)) {
                    btnUpdateProfile.setVisibility(View.INVISIBLE);
                }
            }
        };
        TextWatcher textWatcherPhone = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(userProfile.getPhone())){
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                } else if (checkSame(4)) {
                    btnUpdateProfile.setVisibility(View.INVISIBLE);
                }
            }
        };
        edtName.addTextChangedListener(textWatcherName);
        edtCccd.addTextChangedListener(textWatcherCccd);
        edtPhone.addTextChangedListener(textWatcherPhone);
        edtEmail.addTextChangedListener(textWatcherEmail);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Map<String, String> data = new HashMap<>();
                if(edtName.getText().toString().equals("") ||
                        edtEmail.getText().toString().equals("") ||
                        edtCccd.getText().toString().equals("") ||
                        edtPhone.getText().toString().equals("")){
                    Toast.makeText(IntentChangeProfile.this, getResources().getString(R.string.toast_field_empty), Toast.LENGTH_SHORT).show();
                }
                else {
                    data.put("name", edtName.getText().toString());
                    data.put("email", edtEmail.getText().toString());
                    data.put("cccd", edtCccd.getText().toString());
                    data.put("phone", edtPhone.getText().toString());
                    UserService.updateUserProfile(UserSessionManager.getUsername(), data, IntentChangeProfile.this);
                }
            }
        });
    }

    private void setControl() {
        edtName = findViewById(R.id.edtName);
        edtCccd = findViewById(R.id.edtCccd);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
    }

    private boolean checkSame(int i){
        return ((edtName.getText().toString().equals(userProfile.getName())||i==1)
                && (edtCccd.getText().toString().equals(userProfile.getCccd())||i==2)
                && (edtEmail.getText().toString().equals(userProfile.getEmail())||i==3)
                && (edtPhone.getText().toString().equals(userProfile.getPhone()))||i==4);
    }
}
