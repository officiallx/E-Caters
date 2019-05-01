package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("/api/sign-up")
    Call<Void> register(
            @Body User user
    );
}
