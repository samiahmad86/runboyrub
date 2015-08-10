package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanay on 10/8/15.
 */
public class Dish {
    @SerializedName("dish_name")
    @Expose
    public String dishName;
    @SerializedName("standard_dish_name")
    @Expose
    public String standardDishName;

}