package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.ebanking.api.SavingAccountAPIService;
import com.bank.ebanking.model.SavingAccount;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingAccountService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getSavingAccounts(String data, Context context, Intent intent) {
        SavingAccountAPIService.service.getSavingAccounts(data).enqueue(new Callback<List<SavingAccount>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<SavingAccount>> call, @NonNull Response<List<SavingAccount>> response) {
                ArrayList<SavingAccount> savingAccounts = new ArrayList<>();
                try {
                    if(response.body()!= null){
                        response.body().forEach(savingAccount -> {
                            savingAccounts.add(savingAccount);
                        });
                    }
                    intent.putExtra("savingAccounts", savingAccounts);
                    InterestRateService.getInterestRates(context, intent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<SavingAccount>> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
}
