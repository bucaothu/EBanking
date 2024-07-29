package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bank.EBanking.R;
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
    public static void login(Map<String, String> data, Context context) {
        AuthenticationAPIService.service.login(data).enqueue(new Callback<JsonElement>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                try {
                    JsonObject responseData = Objects.requireNonNull(response.body()).getAsJsonObject();
                    String accessToken = responseData.get("accessToken").getAsString();
                    UserSessionManager.innit(context);
                    // Store username
                    UserSessionManager.saveUsername(responseData.get("username").getAsString());
                    UserSessionManager.saveToken(accessToken);
                    TokenStorage.setAccessToken(accessToken);
                    IntentMainScreen intentMainScreen = new IntentMainScreen();
                    TransactionService.getTransactions(responseData.get("username").getAsString(), context, intentMainScreen);
                } catch (Exception e) {
                    Toast.makeText(context, context.getResources().getString(R.string.toast_username_password), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }

    public static void register(Map<String, String> data, Context context) {
        UserProfile userProfile = new UserProfile(data.get("name"), data.get("phone"), data.get("email"), data.get("cccd"));
        User user = new User(data.get("username"), data.get("password"), data.get("pin"), 1, userProfile);
        AuthenticationAPIService.service.register(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, context.getResources().getString(R.string.toast_register_successful), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, IntentLogin.class));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }
}
