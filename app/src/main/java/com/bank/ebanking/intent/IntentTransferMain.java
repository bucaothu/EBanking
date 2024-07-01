package com.bank.ebanking.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;

public class IntentTransferMain extends AppCompatActivity {
    Button btnContinueTransfer;
    ImageButton btnBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainScreen= new Intent(IntentTransferMain.this, IntentMainScreen.class);
                startActivity(mainScreen);
            }
        });

        btnContinueTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transferDetails= new Intent(IntentTransferMain.this, IntentTransferDetails.class);
                startActivity(transferDetails);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });
    }

    private void setControl() {
        btnContinueTransfer = findViewById(R.id.btn_continue_transfer);
        btnBackHome = findViewById(R.id.btn_back_home);
    }
}
