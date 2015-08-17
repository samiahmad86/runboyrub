package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 10/8/15.
 */
public class DishVariation {
    @Expose
    public Dish dish;
    @Expose
    public String variety;
    @Expose
    public String portion;
}