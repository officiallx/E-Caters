package com.obnoxious.ecatering.models;

/**
 * Created by Bleeding Rain on 4/21/2019.
 */

public class EventTime {

    private String eventDate;
    private String eventTime;
    private String guest_count;

//    public EventTime(int eventDatId, String eventDate, String eventTime, String guest_count) {
//        this.eventDatId = eventDatId;
//        this.eventDate = eventDate;
//        this.eventTime = eventTime;
//        this.guest_count = guest_count;
//    }


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

    public String getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(String guest_count) {
        this.guest_count = guest_count;
    }

    @Override
    public String toString() {
        return "EventTime{" +
                "eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", guest_count='" + guest_count + '\'' +
                '}';
    }
}
