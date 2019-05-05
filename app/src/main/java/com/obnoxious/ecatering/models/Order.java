package com.obnoxious.ecatering.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("eventVenue")
    @Expose
    private String eventVenue;
    @SerializedName("eventAddress")
    @Expose
    private String eventAddress;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("eventDateTime")
    @Expose
    private EventTime eventDateTime;
    @SerializedName("selectedService")
    @Expose
    private String selectedService;
    @SerializedName("userId")
    @Expose
    private User userId;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public EventTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(EventTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "packageName='" + packageName + '\'' +
                ", eventVenue='" + eventVenue + '\'' +
                ", eventAddress='" + eventAddress + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDateTime=" + eventDateTime +
                ", userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }
}