package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.ebanking.api.UserAPIService;
import com.bank.ebanking.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getUser(String data, Context context, Intent newIntent) {
        UserAPIService.service.getUser(data).enqueue(new Callback<User>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = new User();
                try {
                    if(response.body()!= null){
                        user = response.body();
                    }
                    newIntent.putExtra("user", user);
                    context.startActivity(newIntent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
}
