package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Breakfast;
import com.obnoxious.ecatering.models.Dinner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DinnerService {

    @GET("api/dinner/{packageId}")
    Call<List<Dinner>> getAllDinner(
            @Path("packageId") Long packageId,
            @Header("Authorization") String authHeader
    );
}
