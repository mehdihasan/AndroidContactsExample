package com.example.mehdi.androidcontacts.model;

/**
 * Created by Mehdi on 3/14/2015.
 */
public class ModelContactDetails {

    private String itemValue;
    private int itemType; // 0 = phone number; 1 = email address

    /*********************************************************************************/
    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    /*********************************************************************************/
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
