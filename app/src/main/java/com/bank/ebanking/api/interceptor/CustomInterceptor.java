package com.bank.ebanking.api.interceptor;


import androidx.annotation.NonNull;

import java.io.IOException;

import com.bank.ebanking.services.Services.UserSessionManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String accessToken = "";

        try{
            accessToken = UserSessionManager.getToken();
        }
        catch (Exception e){
            System.out.println("can't find access token");
        }
        if (accessToken.isEmpty()) {
            Request modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(modifiedRequest);
        } else {
            Request modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Accept", "application/json")
                    .build();
            // CONTINUE TO EXECUTE REQUEST.
            return chain.proceed(modifiedRequest);
        }
    }
}
