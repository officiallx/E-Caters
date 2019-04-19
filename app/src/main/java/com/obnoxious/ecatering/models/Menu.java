package com.obnoxious.ecatering.models;

/**
 * Created by Bleeding Rain on 4/19/2019.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("menuId")
    @Expose
    private Integer menuId;
    @SerializedName("menuName")
    @Expose
    private String menuName;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
