package com.obnoxious.ecatering.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breakfast {

    @SerializedName("breakfastId")
    @Expose
    private Integer breakfastId;
    @SerializedName("breakfastName")
    @Expose
    private String breakfastName;
    @SerializedName("aPackage")
    @Expose
    private Package aPackage;

    public Integer getBreakfastId() {
        return breakfastId;
    }

    public void setBreakfastId(Integer breakfastId) {
        this.breakfastId = breakfastId;
    }

    public String getBreakfastName() {
        return breakfastName;
    }

    public void setBreakfastName(String breakfastName) {
        this.breakfastName = breakfastName;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }
}
