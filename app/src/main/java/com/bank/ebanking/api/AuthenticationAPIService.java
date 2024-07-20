package com.bank.ebanking.api;

import com.bank.ebanking.model.User;
import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationAPIService extends APIService {
    AuthenticationAPIService service = BUILDER.create(AuthenticationAPIService.class);

    @POST("/login")
    Call<JsonElement> login(@Body Map<String, String> account);

    @POST("api/userProfiles")
    Call<Void> register(@Body User data);
}
