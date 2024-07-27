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
import com.bank.ebanking.model.UserProfile;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getUserProfile(String data, Context context, AppCompatActivity intent) {
        UserAPIService.service.getUserProfile(data).enqueue(new Callback<UserProfile>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<UserProfile> call, @NonNull Response<UserProfile> response) {
                UserProfile userProfile = new UserProfile();
                try {
                    if(response.body()!= null){
                        userProfile.setIdUserProfile(response.body().getIdUserProfile());
                        userProfile.setName(response.body().getName());
                        userProfile.setCccd(response.body().getCccd());
                        userProfile.setPhone(response.body().getPhone());
                        userProfile.setEmail(response.body().getEmail());
                        System.out.println(response.body().getEmail());
                    }
                    Intent newIntent = new Intent(context, intent.getClass());
                    newIntent.putExtra("userProfile", userProfile);
                    context.startActivity(newIntent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserProfile> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updateUserProfile(String username, Map<String, String> data, Context context) {
        UserAPIService.service.updateUserProfile(username, data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.errorBody()!=null) {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updatePassword(String username, Map<String, String> data, Context context) {
        UserAPIService.service.updatePassword(username, data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.errorBody()!=null) {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }
}
