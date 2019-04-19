package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.FoodItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bleeding Rain on 4/19/2019.
 */

public interface FoodService {

    @GET("api/menu/{menuId}/items")
    Call<List<FoodItem>> getAllMenu(
            @Path("menuId") Long menuId
    );
}
