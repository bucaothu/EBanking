package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;

import java.util.HashMap;
import java.util.Map;

public class IntentSignUp extends AppCompatActivity {
    Button btnSignUp;
    EditText edtName, edtPhone, edtCccd, edtEmail, edtUsername, edtSignupPassword, edtSignupRepassword;
    Map<String, String> signUpData= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String cccd = edtCccd.getText().toString();
                String phone = edtPhone.getText().toString();
                String username = edtUsername.getText().toString();
                String signUpPassword = edtSignupPassword.getText().toString();
                String signUpRePassword = edtSignupRepassword.getText().toString();
                if(!signUpPassword.equals(signUpRePassword)){
                    Toast.makeText(IntentSignUp.this, "2 mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                } else if (signUpPassword.length()<6) {
                    Toast.makeText(IntentSignUp.this, "Mật khẩu phải dài hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                }
                else{
                    signUpData.put("name", name);
                    signUpData.put("email", email);
                    signUpData.put("phone", phone);
                    signUpData.put("cccd", cccd);
                    signUpData.put("username", username);
                    signUpData.put("password", signUpPassword);
                    Intent intent = new Intent(IntentSignUp.this, IntentPinInput.class);
                    Bundle bundle = new Bundle();
                    for (Map.Entry<String, String> entry : signUpData.entrySet()) {
                        bundle.putString(entry.getKey(), entry.getValue());
                    }
                    intent.putExtras(bundle);
                    intent.putExtra("displayText", "Hãy nhập mã PIN");
                    intent.putExtra("endPoint", "signUp");
                    intent.putExtra("isRetype", true);
                    startActivity(intent);
                }
            }
        });
    }

    private void setControl() {
        btnSignUp = findViewById(R.id.button_sign_up);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtCccd = findViewById(R.id.edtCccd);
        edtPhone = findViewById(R.id.edtPhone);
        edtUsername = findViewById(R.id.edtUsername);
        edtSignupPassword = findViewById(R.id.signup_password);
        edtSignupRepassword = findViewById(R.id.signup_repassword);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Map<String, String> signUpInfos = new HashMap<>();
                    for (String key : bundle.keySet()) {
                        signUpInfos.put(key, bundle.getString(key));
                    }
                    edtName.setText(signUpInfos.get("name"));
                    edtEmail.setText(signUpInfos.get("email"));
                    edtPhone.setText(signUpInfos.get("phone"));
                    edtCccd.setText(signUpInfos.get("cccd"));
                    edtUsername.setText(signUpInfos.get("username"));
                    edtSignupPassword.setText(signUpInfos.get("password"));
                    edtSignupRepassword.setText(signUpInfos.get("password"));
                }
            }
        }
    }

}
