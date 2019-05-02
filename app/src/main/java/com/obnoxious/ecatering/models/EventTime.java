package com.obnoxious.ecatering.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventTime {

    @SerializedName("eventDate")
    @Expose
    private String eventDate;
    @SerializedName("eventTime")
    @Expose
    private String eventTime;
    @SerializedName("guest_count")
    @Expose
    private String guestCount;
    @SerializedName("eventId")
    @Expose
    private Integer eventId;

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(String guestCount) {
        this.guestCount = guestCount;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "EventTime{" +
                "eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", guestCount='" + guestCount + '\'' +
                ", eventId=" + eventId +
                '}';
    }
}