package com.bank.ebanking.api.interceptor;

import android.os.Build;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenStorage {
    private static String ACCESS_TOKEN, REFRESH_TOKEN;

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }

    public static String getRefreshToken() {
        return REFRESH_TOKEN;
    }

    public static void setRefreshToken(String refreshToken) {
        REFRESH_TOKEN = refreshToken;
    }

    public static JSONObject getTokenData() {
        // CHECK IF ACCESS TOKEN IS AVAILABLE.
        if (ACCESS_TOKEN == null) return null;
        // CHECK IF DEVICE SUPPORTS NECESSARY VERSION.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return null;
        // CHECK IF ACCESS TOKEN IS FORMATTED CORRECTLY.
        String[] tokenElements = ACCESS_TOKEN.split("\\.");
        if (tokenElements.length != 3) return null;
        // CHECK IF ACCESS TOKEN IS PARSED SUCCESSFULLY.
        try {
            String data = new String(Base64.getUrlDecoder().decode(tokenElements[1]), StandardCharsets.UTF_8);
            return new JSONObject(data);
        } catch (Exception e) {
            return null;
        }
    }
}
