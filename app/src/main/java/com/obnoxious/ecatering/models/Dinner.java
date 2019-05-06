package com.obnoxious.ecatering.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dinner {

    @SerializedName("dinnerId")
    @Expose
    private Integer dinnerId;
    @SerializedName("dinnerName")
    @Expose
    private String dinnerName;
    @SerializedName("aPackage")
    @Expose
    private Package aPackage;

    public Integer getDinnerId() {
        return dinnerId;
    }

    public void setDinnerId(Integer dinnerId) {
        this.dinnerId = dinnerId;
    }

    public String getDinnerName() {
        return dinnerName;
    }

    public void setDinnerName(String dinnerName) {
        this.dinnerName = dinnerName;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }
}
