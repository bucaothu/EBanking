package com.bank.ebanking.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bank.ebanking.model.BankAccount;
import com.bank.ebanking.model.Transaction;
import com.bank.ebanking.model.User;
import com.bank.ebanking.model.UserProfile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

@RequiresApi(api = Build.VERSION_CODES.O)
public interface UserAPIService extends APIService {
    UserAPIService service = BUILDER.create(UserAPIService.class);
    @GET("api/users")
    Call<User> getUser(@Query("username[eq]") String username);
    @GET("api/userProfiles/{username}")
    Call<UserProfile> getUserProfile(@Path("username") String username);
    @PATCH("api/userProfiles/{username}")
    Call<String> updateUserProfile(@Path("username") String username, @Body Map<String, String> account);
}
