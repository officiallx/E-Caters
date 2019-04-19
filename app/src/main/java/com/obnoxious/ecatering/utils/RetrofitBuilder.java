package com.obnoxious.ecatering.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class RetrofitBuilder {

    private final String baseUrl = "http://192.168.100.24:8080/";

    public void getAllEvents() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
