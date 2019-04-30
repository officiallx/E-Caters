package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("/login")
    Call<Void> login(
            @Body User user
    );
}
