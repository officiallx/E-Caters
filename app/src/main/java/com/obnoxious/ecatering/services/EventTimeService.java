package com.obnoxious.ecatering.services;

import com.obnoxious.ecatering.models.EventTime;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Bleeding Rain on 4/21/2019.
 */

public interface EventTimeService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("api/eventtime")
    Call<EventTime> postEventTime(
            @Body EventTime eventTime
    );
}
