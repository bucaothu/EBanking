package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.SavingAccountType;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface SavingAccountTypeAPIService extends APIService {
    SavingAccountTypeAPIService service = BUILDER.create(SavingAccountTypeAPIService.class);
    @GET("api/savingAccountTypes")
    Call<List<SavingAccountType>> getSavingAccountTypes();
}
