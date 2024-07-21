package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.bank.ebanking.api.BankAccountAPIService;
import com.bank.ebanking.model.BankAccount;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankAccountService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getBankAccounts(String data, Context context, AppCompatActivity intent) {
        BankAccountAPIService.service.getBankAccounts(data).enqueue(new Callback<List<BankAccount>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<BankAccount>> call, @NonNull Response<List<BankAccount>> response) {
                ArrayList<BankAccount> bankAccounts = new ArrayList<>();
                try {
                    if(response.body()!= null){
                        response.body().forEach(bankAccount -> {
                            bankAccounts.add(bankAccount);
                        });
                    }
                    Intent newIntent = new Intent(context, intent.getClass());
                    newIntent.putExtra("bankAccounts", bankAccounts);
                    SavingAccountService.getSavingAccounts(data, context, newIntent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<BankAccount>> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
}
