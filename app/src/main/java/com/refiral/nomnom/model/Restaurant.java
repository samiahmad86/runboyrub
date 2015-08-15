package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by tanay on 10/8/15.
 */
public class Restaurant {

    @Expose
    public String brand;
    @Expose
    public String address;
    @Expose
    public long id;
    @Expose
    public ArrayList<Object> numbers = new ArrayList<Object>();

}