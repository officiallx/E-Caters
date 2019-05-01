package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Breakfast;
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("/login")
    Call<Void> login(
            @Body User user
    );

    @GET("api/users/{username}")
    Call <User> getUserbyUsername(
            @Path("username") String username
    );
}
