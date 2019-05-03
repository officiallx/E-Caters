package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Breakfast;
import com.obnoxious.ecatering.models.Lunch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LunchService {

    @GET("api/lunch/{packageId}")
    Call<List<Lunch>> getAllLunch(
            @Path("packageId") Long packageId,
            @Header("Authorization") String authHeader
    );
}
