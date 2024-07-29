package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.BankAccount;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface BankAccountAPIService extends APIService {
    BankAccountAPIService service = BUILDER.create(BankAccountAPIService.class);
    @GET("api/bankAccounts/username/{username}")
    Call<List<BankAccount>> getBankAccounts(@Path("username") String username);
    @GET("api/bankAccounts/{accountNumber}")
    Call<BankAccount> getBankAccount(@Path("accountNumber") String accountNumber);
    @POST("api/bankAccounts")
    Call<String> addBankAccount(@Body Map<String, Object> data);
}
