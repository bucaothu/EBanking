package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.Transaction;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface TransactionAPIService extends APIService {
    TransactionAPIService service = BUILDER.create(TransactionAPIService.class);
    @GET("api/transactions/username/{username}")
    Call<List<Transaction>> getTransactions(@Path("username") String username);
    @POST("api/transactions")
    Call<String> transfer(@Body Map<String, Object> data);
    @GET("api/transactions/otp/{username}")
    Call<String> otp(@Path("username") String username);
}