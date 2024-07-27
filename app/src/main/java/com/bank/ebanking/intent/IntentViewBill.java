package com.bank.ebanking.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.model.Transaction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import android.provider.MediaStore;

public class IntentViewBill extends AppCompatActivity {
    // Khai báo các biến để liên kết với các view
    private TextView tvTitle;
    private TextView tvAmountLabel;
    private TextView tvAmountInfo;
    private TextView tvDateLabel;
    private TextView tvDateInfo;
    private TextView tvDescriptionLabel;
    private TextView tvDescriptionInfo;
    private TextView tvFromAccountLabel;
    private TextView tvFromAccountInfo;
    private TextView tvToAccountLabel;
    private TextView tvToAccountInfo;
    private TextView tvUserInfoLabel;
    private TextView tvUserInfoDetails;
    private Button cbtnFinishTransfer;
    private LinearLayout llContainer;

    private View breakBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        Transaction transaction = (Transaction) intent.getSerializableExtra("transaction");
        tvAmountInfo.setText(String.format("%.2f₫", transaction.getAmount()).concat("đ"));
        tvDateInfo.setText(new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate()));
        tvDescriptionInfo.setText(transaction.getDescription());
        tvFromAccountInfo.setText(transaction.getAccountNumber().getAccountNumber());
        if(transaction.getTransactionType().getIdTransactionType()==2){
            tvToAccountInfo.setVisibility(View.GONE);
            tvToAccountLabel.setVisibility(View.GONE);
            tvUserInfoDetails.setVisibility(View.GONE);
            tvUserInfoLabel.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) breakBar.getLayoutParams();
            layoutParams.topMargin = 0;
            breakBar.setLayoutParams(layoutParams);
        }
        else {
            tvToAccountInfo.setText(transaction.getToAccountNumber().getAccountNumber());
            tvUserInfoDetails.setText(transaction.getToAccountNumber().getIdUser().getUserProfile().getName());
        }
        cbtnFinishTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llContainer.setDrawingCacheEnabled(true);
                llContainer.buildDrawingCache();
                Bitmap bm = llContainer.getDrawingCache();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString(), "EBanking.png");
                OutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                try {
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setControl() {
        tvTitle = findViewById(R.id.tv_title);
        tvAmountLabel = findViewById(R.id.tv_amount_label);
        tvAmountInfo = findViewById(R.id.tv_amount_info);
        tvDateLabel = findViewById(R.id.tv_date_label);
        tvDateInfo = findViewById(R.id.tv_date_info);
        tvDescriptionLabel = findViewById(R.id.tv_description_label);
        tvDescriptionInfo = findViewById(R.id.tv_description_info);
        tvFromAccountLabel = findViewById(R.id.tv_from_account_label);
        tvFromAccountInfo = findViewById(R.id.tv_from_account_info);
        tvToAccountLabel = findViewById(R.id.tv_to_account_label);
        tvToAccountInfo = findViewById(R.id.tv_to_account_info);
        tvUserInfoLabel = findViewById(R.id.tv_user_info_label);
        tvUserInfoDetails = findViewById(R.id.tv_user_info_details);
        cbtnFinishTransfer = findViewById(R.id.cbtn_finish_transfer);
        breakBar = findViewById(R.id.breakBar);
        llContainer = findViewById(R.id.linearLayoutContainer);
    }
}
