package com.obnoxious.ecatering.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class Event {

    @SerializedName("eventId")
    @Expose
    private Long eventId;
    @SerializedName("eventName")
    @Expose
    private String eventName;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
