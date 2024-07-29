package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.SavingAccountType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface SavingAccountTypeAPIService extends APIService {
    SavingAccountTypeAPIService service = BUILDER.create(SavingAccountTypeAPIService.class);
    @GET("api/savingAccountTypes")
    Call<List<SavingAccountType>> getSavingAccountTypes();
}
