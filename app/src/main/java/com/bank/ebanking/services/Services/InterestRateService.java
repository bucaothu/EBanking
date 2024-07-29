package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.api.InterestRateAPIService;
import com.bank.ebanking.model.InterestRate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestRateService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getInterestRates(Context context, Intent intent) {
        InterestRateAPIService.service.getInterestRates().enqueue(new Callback<List<InterestRate>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<InterestRate>> call, @NonNull Response<List<InterestRate>> response) {
                ArrayList<InterestRate> InterestRates = new ArrayList<>();
                try {
                    if(response.body()!= null){
                        response.body().forEach(bankAccount -> {
                            InterestRates.add(bankAccount);
                        });
                    }
                    intent.putExtra("InterestRates", InterestRates);
                    context.startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<InterestRate>> call, @NonNull Throwable t) {
                context.getResources().getString(R.string.toast_error);
                System.out.println(t);
            }
        });
    }
}
