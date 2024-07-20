package com.bank.ebanking.services.Services;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ACCESSTOKEN="accessToken";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void saveToken(String token) {
        editor.putString(KEY_ACCESSTOKEN, token);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_ACCESSTOKEN, null);
    }


    public void clearUsername() {
        editor.remove(KEY_USERNAME);
        editor.apply();
    }
}
