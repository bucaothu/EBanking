package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bank.ebanking.api.SavingAccountTypeAPIService;
import com.bank.ebanking.model.SavingAccountType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingAccountTypeService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getSavingAccountTypes(Context context, Intent intent) {
        SavingAccountTypeAPIService.service.getSavingAccountTypes().enqueue(new Callback<List<SavingAccountType>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<SavingAccountType>> call, @NonNull Response<List<SavingAccountType>> response) {
                ArrayList<SavingAccountType> savingAccountTypes = new ArrayList<>();
                try {
                    if(response.body()!= null){
                        response.body().forEach(savingAccountType -> {
                            savingAccountTypes.add(savingAccountType);
                        });
                        intent.putExtra("savingAccountTypes", savingAccountTypes);
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<SavingAccountType>> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
}
