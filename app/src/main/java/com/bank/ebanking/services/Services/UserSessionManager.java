package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ACCESSTOKEN="accessToken";
    private static SharedPreferences sharedPreferences;

    public static void innit(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUsername(String username) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public static void saveToken(String token) {
        sharedPreferences.edit().putString(KEY_ACCESSTOKEN, token).apply();
    }

    public static String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public static String getToken() {
        return sharedPreferences.getString(KEY_ACCESSTOKEN, null);
    }


    public void clearUsername() {
        sharedPreferences.edit().remove(KEY_USERNAME).apply();
    }
}
