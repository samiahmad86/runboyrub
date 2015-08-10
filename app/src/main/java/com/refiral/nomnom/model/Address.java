package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 10/8/15.
 */
public class Address {
    @Expose
    public long id;
    @Expose
    public Locality locality;
    @SerializedName("created_on")
    @Expose
    public String createdOn;
    @SerializedName("updated_on")
    @Expose
    public String updatedOn;
    @SerializedName("complete_address")
    @Expose
    public String completeAddress;
    @SerializedName("address_type")
    @Expose
    public String addressType;
    @Expose
    public long customer;
    @Expose
    public long city;}