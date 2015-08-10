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
public class Customer{
    @Expose
    public long id;
    @Expose
    public String name;
    @SerializedName("primary_number")
    @Expose
    public String primaryNumber;
}