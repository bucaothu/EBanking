package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.SavingAccount;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface SavingAccountAPIService extends APIService {
    SavingAccountAPIService service = BUILDER.create(SavingAccountAPIService.class);
    @GET("api/savingAccounts/username/{username}")
    Call<List<SavingAccount>> getSavingAccounts(@Path("username") String username);
}
