package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 8/8/15.
 */
public class User {
    @Expose
    @SerializedName("contact_number")
    public String contactNumber;

    public User(String contactNum) {
        this.contactNumber = contactNum;
    }
}
