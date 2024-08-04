package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bank.EBanking.R;
import com.bank.ebanking.api.BankAccountAPIService;
import com.bank.ebanking.model.BankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                context.getResources().getString(R.string.toast_error);
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getBankAccounts(String data, Context context, Intent intent, boolean redirect) {
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
                    intent.putExtra("bankAccounts", bankAccounts);
                    context.startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<BankAccount>> call, @NonNull Throwable t) {
                context.getResources().getString(R.string.toast_error);
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getBankAccount(String data, String username, Context context, AppCompatActivity intent) {
        BankAccountAPIService.service.getBankAccount(data).enqueue(new Callback<BankAccount>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<BankAccount> call, @NonNull Response<BankAccount> response) {
                try {
                    if(response.body()!= null){
                        BankAccount bankAccount = response.body();
                        System.out.println("1 " +bankAccount.getAccountNumber());
                        Intent newIntent = new Intent(context, intent.getClass());
                        newIntent.putExtra("bankAccount", bankAccount);
                        getBankAccounts(username, context, newIntent, true);
                    }
                    else{
                        Toast.makeText(context, context.getResources().getString(R.string.notify_no_bank_account), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<BankAccount> call, @NonNull Throwable t) {
                context.getResources().getString(R.string.toast_error);
                System.out.println(t);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addBankAccount(Map<String, Object> data, Context context, AppCompatActivity intent) {
        BankAccountAPIService.service.addBankAccount(data).enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body()!= null){
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        getBankAccounts(UserSessionManager.getUsername(), context, intent);
                    }
                    else{
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                context.getResources().getString(R.string.toast_error);
                System.out.println(t);
            }
        });
    }
}
