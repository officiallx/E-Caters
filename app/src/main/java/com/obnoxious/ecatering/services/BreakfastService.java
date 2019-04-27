package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Breakfast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BreakfastService {

    @GET("api/breakfast/{packageId}")
    Call<List<Breakfast>> getAllBreakfast(
            @Path("packageId") Long packageId
    );
}
