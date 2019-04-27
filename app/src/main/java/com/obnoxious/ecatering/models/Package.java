package com.obnoxious.ecatering.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Package {

    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("packageType")
    @Expose
    private String packageType;
    @SerializedName("packageDescription")
    @Expose
    private String packageDescription;
    @SerializedName("packagePrice")
    @Expose
    private String packagePrice;
    @SerializedName("profilePath")
    @Expose
    private String profilePath;
    @SerializedName("event")
    @Expose
    private Event event;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}