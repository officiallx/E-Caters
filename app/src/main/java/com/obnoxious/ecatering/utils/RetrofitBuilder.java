package com.obnoxious.ecatering.utils;

import com.obnoxious.ecatering.services.BreakfastService;
import com.obnoxious.ecatering.services.DinnerService;
import com.obnoxious.ecatering.services.EventService;
import com.obnoxious.ecatering.services.EventTimeService;
import com.obnoxious.ecatering.services.LoginService;
import com.obnoxious.ecatering.services.LunchService;
import com.obnoxious.ecatering.services.OrderService;
import com.obnoxious.ecatering.services.PackageService;
import com.obnoxious.ecatering.services.RegisterService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    public EventTimeService eventTimeService(){
        return retrofit.create(EventTimeService.class);
    }

    public OrderService orderService(){
        return retrofit.create(OrderService.class);
    }

    public PackageService packageService(){
        return retrofit.create(PackageService.class);
    }

    public BreakfastService breakfastService(){
        return retrofit.create(BreakfastService.class);
    }

    public LunchService lunchService(){return retrofit.create(LunchService.class);}

    public DinnerService dinnerService(){return retrofit.create(DinnerService.class);}

}
