package com.bank.ebanking.services.Services;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bank.ebanking.api.AuthenticationAPIService;
import com.bank.ebanking.api.interceptor.TokenStorage;
import com.bank.ebanking.intent.IntentLogin;
import com.bank.ebanking.intent.IntentMainScreen;
import com.bank.ebanking.model.User;
import com.bank.ebanking.model.UserProfile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {
    private static UserSessionManager userSessionManager;
    public static void login(Map<String, String> data, Context context) {
        AuthenticationAPIService.service.login(data).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                try {
                    // GET JSON RESPONSE.
                    JsonObject responseData = Objects.requireNonNull(response.body()).getAsJsonObject();
                    String accessToken = responseData.get("accessToken").getAsString();
                    userSessionManager = new UserSessionManager(context);
                    // Store username
                    userSessionManager.saveUsername(responseData.get("username").getAsString());
                    userSessionManager.saveToken(accessToken);
                    TokenStorage.setAccessToken(accessToken);
                    Intent intent = new Intent(context, IntentMainScreen.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Toast.makeText(context, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }

    public static void register(Map<String, String> data, Context context) {
        UserProfile userProfile = new UserProfile(data.get("name"), data.get("phone"), data.get("email"), data.get("cccd"));
        User user = new User(data.get("username"), data.get("password"), data.get("pin"), true, userProfile);
        AuthenticationAPIService.service.register(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, IntentLogin.class));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(context, "Thêm tài khoản thất bại", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }
}
