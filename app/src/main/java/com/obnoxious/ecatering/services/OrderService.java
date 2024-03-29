package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.Lunch;
import com.obnoxious.ecatering.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("api/order")
    Call<Order> addOrder(
            @Body Order order,
            @Header("Authorization") String authHeader
    );

    @GET("api/order/{orderId}")
    Call<Order> getOrderById(
            @Path("orderId") Long orderId,
            @Header("Authorization") String authHeader
    );

    @DELETE("api/order/{orderId}")
    Call<Order> deleteOrderbyId(
            @Path("orderId") Long orderId,
            @Header("Authorization") String authHeader
    );
}
