package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.bank.ebanking.api.TransactionAPIService;
import com.bank.ebanking.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getTransactions(String data, Context context, AppCompatActivity intent) {
        TransactionAPIService.service.getTransactions(data).enqueue(new Callback<List<Transaction>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call, @NonNull Response<List<Transaction>> response) {
                ArrayList<Transaction> transactions = new ArrayList<>();
                try {
                    if(response.body()!= null){
                        response.body().forEach(transaction -> {
                            transactions.add(transaction);
                        });
                    }
                    Intent newIntent = new Intent(context, intent.getClass());
                    newIntent.putExtra("transactions", transactions);
                    UserService.getUser(data, context, newIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
