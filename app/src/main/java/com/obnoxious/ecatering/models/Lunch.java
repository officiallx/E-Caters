package com.obnoxious.ecatering.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Lunch {

    @SerializedName("lunchId")
    @Expose
    private Integer lunchId;
    @SerializedName("lunchName")
    @Expose
    private String lunchName;
    @SerializedName("aPackage")
    @Expose
    private Package aPackage;

    public Integer getLunchId() {
        return lunchId;
    }

    public void setLunchId(Integer lunchId) {
        this.lunchId = lunchId;
    }

    public String getLunchName() {
        return lunchName;
    }

    public void setLunchName(String lunchName) {
        this.lunchName = lunchName;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }
}
