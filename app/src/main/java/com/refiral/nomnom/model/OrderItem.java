package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 10/8/15.
 */
public class OrderItem {
    @Expose
    public long id;
    @SerializedName("dish_variation")
    @Expose
    public DishVariation dishVariation;
    @Expose
    public long quantity;
    @SerializedName("unit_price_charged")
    @Expose
    public long unitPriceCharged;
    @SerializedName("total_price_charged")
    @Expose
    public long totalPriceCharged;
}