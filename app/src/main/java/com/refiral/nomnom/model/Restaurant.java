package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanay on 10/8/15.
 */
public class Restaurant {

    @Expose
    public Brand brand;
    @Expose
    public String address;
    @Expose
    public long id;
}