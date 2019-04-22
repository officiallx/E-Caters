package com.obnoxious.ecatering.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("eventId")
    @Expose
    private Integer eventId;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("profilePath")
    @Expose
    private String profilePath;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

}
