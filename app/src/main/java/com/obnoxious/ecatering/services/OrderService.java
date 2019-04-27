package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("api/order")
    Call<Void> addOrder(
            @Body Order order
    );
}
