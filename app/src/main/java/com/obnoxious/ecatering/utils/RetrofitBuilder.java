package com.obnoxious.ecatering.utils;

import com.obnoxious.ecatering.services.EventService;
import com.obnoxious.ecatering.services.LoginService;
import com.obnoxious.ecatering.services.RegisterService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class RetrofitBuilder {

    private final String baseUrl = "http://192.168.100.24:8080/";
    private static RetrofitBuilder mInstance;
    private Retrofit retrofit;

    private RetrofitBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitBuilder getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitBuilder();
        }
        return mInstance;
    }

    public EventService eventService(){
        return retrofit.create(EventService.class);
    }

    public LoginService loginService(){
        return retrofit.create(LoginService.class);
    }

    public RegisterService registerService(){
        return retrofit.create(RegisterService.class);
    }

}
