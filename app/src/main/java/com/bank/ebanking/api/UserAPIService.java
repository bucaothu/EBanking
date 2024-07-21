package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.Transaction;
import com.bank.ebanking.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface UserAPIService extends APIService {
    UserAPIService service = BUILDER.create(UserAPIService.class);
    @GET("api/users")
    Call<User> getUser(@Query("username[eq]") String username);
}
