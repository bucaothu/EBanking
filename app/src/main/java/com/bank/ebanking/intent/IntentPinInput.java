package com.bank.ebanking.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;

import com.bank.EBanking.R;
import com.bank.ebanking.services.Services.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

public class IntentPinInput extends AppCompatActivity {

    private EditText[] pinDigits;
    private View[] pinIndicators;
    private Button confirmButton;
    private TextView title;
    private Map<String, String> signUpInfos = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_registering);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        String displayText = intent.getStringExtra("displayText");
        String endPoint = intent.getStringExtra("endPoint");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                signUpInfos.put(key, bundle.getString(key));
            }
        }
        title.setText(displayText);
        for (int i = 0; i < pinDigits.length; i++) {
            final int index = i;
            pinDigits[index].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    if (pinDigits[index].getText().length() == 0 && index > 0) {
                        pinDigits[index - 1].requestFocus();
                        pinDigits[index - 1].setText("");
                        pinIndicators[index].setVisibility(View.INVISIBLE);
                    }
                }
                return false;
            });

            pinDigits[index].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1) {
                        pinIndicators[index].setVisibility(View.VISIBLE);
                        if (index < pinDigits.length - 1) {
                            pinDigits[index + 1].requestFocus();
                        }
                    } else {
                        pinIndicators[index].setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        confirmButton.setOnClickListener(v -> {
            StringBuilder pinBuilder = new StringBuilder();
            for (EditText digit : pinDigits) {
                pinBuilder.append(digit.getText().toString());
            }
            String pin = pinBuilder.toString();
            boolean isRetype = intent.getBooleanExtra("isRetype", false);
            if(isRetype){
                signUpInfos.put("pin", pin);
                Intent intentRetype = new Intent(IntentPinInput.this, IntentPinInput.class);
                Bundle bundleRetype = new Bundle();
                for (Map.Entry<String, String> entry : signUpInfos.entrySet()) {
                    bundleRetype.putString(entry.getKey(), entry.getValue());
                }
                intentRetype.putExtras(bundleRetype);
                intentRetype.putExtra("displayText", "Hãy nhập lại mã PIN");
                intentRetype.putExtra("endPoint", endPoint);
                startActivity(intentRetype);
            }
            else{
                switch (endPoint){
                    case "signUp":
                        if(signUpInfos.get("pin").equals(pin)) {
                            AuthenticationService.register(signUpInfos, this);
                        }
                        else Toast.makeText(this, "Mã PIN không giống nhau", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setControl() {
        pinDigits = new EditText[6];
        pinDigits[0] = findViewById(R.id.pin_digit_1);
        pinDigits[1] = findViewById(R.id.pin_digit_2);
        pinDigits[2] = findViewById(R.id.pin_digit_3);
        pinDigits[3] = findViewById(R.id.pin_digit_4);
        pinDigits[4] = findViewById(R.id.pin_digit_5);
        pinDigits[5] = findViewById(R.id.pin_digit_6);

        pinIndicators = new View[6];
        pinIndicators[0] = findViewById(R.id.pin_indicator_1);
        pinIndicators[1] = findViewById(R.id.pin_indicator_2);
        pinIndicators[2] = findViewById(R.id.pin_indicator_3);
        pinIndicators[3] = findViewById(R.id.pin_indicator_4);
        pinIndicators[4] = findViewById(R.id.pin_indicator_5);
        pinIndicators[5] = findViewById(R.id.pin_indicator_6);

        confirmButton = findViewById(R.id.confirm_button);
        title = findViewById(R.id.pin_input_title);
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : signUpInfos.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
        super.onBackPressed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        signUpInfos.put(key, bundle.getString(key));
                    }
                }
            }
        }
    }

}
