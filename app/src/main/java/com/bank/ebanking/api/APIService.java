package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.api.interceptor.CustomInterceptor;
import com.bank.ebanking.api.interceptor.DateDeserializer;
import com.bank.ebanking.api.interceptor.OkHttpProvider;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface APIService {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor())
            .addInterceptor(OkHttpProvider.getOkHttpClient())
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();
    Retrofit BUILDER = new Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080/") //nếu chạy máy ảo
            .baseUrl("http://192.168.1.6:8000/") //địa chỉ ip của máy tính
            .client(okHttpClient)
            .addConverterFactory(
                    GsonConverterFactory.create(new GsonBuilder()
                            .setDateFormat("dd-MM-yyyy")
                            .registerTypeAdapter(Date.class, new DateDeserializer())
                            .create()))
            .build();
}
