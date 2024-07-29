package com.bank.ebanking.api.interceptor;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpProvider {
    public static Interceptor getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        return loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}

