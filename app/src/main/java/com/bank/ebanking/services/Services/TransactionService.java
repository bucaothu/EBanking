package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.ebanking.api.TransactionAPIService;
import com.bank.ebanking.intent.IntentMainScreen;
import com.bank.ebanking.model.BillDetail;
import com.bank.ebanking.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                    System.out.println(e);
                    Toast.makeText(context, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call, @NonNull Throwable t) {
                System.out.println(t);
                Toast.makeText(context, "Truy vấn thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void transfer(Map<String, Object> data, Context context, AppCompatActivity intent) {
        TransactionAPIService.service.transfer(data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body()!= null){
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        getTransactions(UserSessionManager.getUsername(), context, intent);
                    }
                    else{
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(context, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void otp(String data, Context context, Intent intent) {
        TransactionAPIService.service.otp(data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body()!= null){
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Không gửi được mã otp", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(context, "Lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getBill(Map<String, String> data, Context context, Intent intent) {
        TransactionAPIService.service.getBill(data).enqueue(new Callback<JsonElement>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                JsonObject responseData = Objects.requireNonNull(response.body()).getAsJsonObject();
                try {
                    if(response.body()!= null){
                        String partnerRefId = responseData.get("partnerRefId").getAsString();
                        JsonObject billDetailObject = responseData.getAsJsonArray("billDetail").get(0).getAsJsonObject();
                        BillDetail billDetail = new Gson().fromJson(billDetailObject, BillDetail.class);
                        intent.putExtra("partnerRefId", partnerRefId);
                        intent.putExtra("billDetail", billDetail);
                        BankAccountService.getBankAccounts(UserSessionManager.getUsername(), context, intent, false);
                    }
                    else{
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Toast.makeText(context, "Lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void payBill(Map<String, Object> data, Context context) {
        TransactionAPIService.service.payBill(data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body()!= null){
                        Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        IntentMainScreen intentMainScreen = new IntentMainScreen();
                        TransactionService.getTransactions(UserSessionManager.getUsername(), context, intentMainScreen);
                    }
                    else{
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(context, "Lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void topUp(Map<String, Object> data, Context context) {
        TransactionAPIService.service.topUp(data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body()!= null){
                        Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        IntentMainScreen intentMainScreen = new IntentMainScreen();
                        TransactionService.getTransactions(UserSessionManager.getUsername(), context, intentMainScreen);
                    }
                    else{
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(context, "Lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }
}
