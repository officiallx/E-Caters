package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public interface EventService {

    @GET("api/events")
    Call<List<Event>> getAllMenu(@Header("Authorization") String authHeader);
}
