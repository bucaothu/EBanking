package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.InterestRate;
import com.bank.ebanking.model.SavingAccount;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface InterestRateAPIService extends APIService {
    InterestRateAPIService service = BUILDER.create(InterestRateAPIService.class);
    @GET("api/interestRates")
    Call<List<InterestRate>> getInterestRates();
}
