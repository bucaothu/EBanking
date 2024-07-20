package com.bank.ebanking.api.interceptor;

import com.bank.ebanking.api.interceptor.CustomInterceptor;

import java.io.IOException;

import com.bank.ebanking.services.Services.UserSessionManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // GET ORIGINAL REQUEST.
        Request originalRequest = chain.request();
        // ADD HEADER TO REQUEST.
        String accessToken = "";

        try{
            UserSessionManager userSessionManager = new UserSessionManager(MyApp.getContext());
            accessToken = userSessionManager.getToken();
        }
        catch (Exception e){
        }
        if(accessToken.equals("")){
            Request modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build();
            // CONTINUE TO EXECUTE REQUEST.
            return chain.proceed(modifiedRequest);
        }
        else{
            Request modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Accept", "application/json")
                    .build();
            // CONTINUE TO EXECUTE REQUEST.
            return chain.proceed(modifiedRequest);
        }
    }
}
