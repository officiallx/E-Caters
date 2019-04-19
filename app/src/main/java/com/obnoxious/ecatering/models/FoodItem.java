package com.obnoxious.ecatering.models;

/**
 * Created by Bleeding Rain on 4/19/2019.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodItem {

    @SerializedName("itemId")
    @Expose
    private Integer itemId;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("itemGuests")
    @Expose
    private String itemGuests;
    @SerializedName("menu")
    @Expose
    private Menu menu;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemGuests() {
        return itemGuests;
    }

    public void setItemGuests(String itemGuests) {
        this.itemGuests = itemGuests;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
