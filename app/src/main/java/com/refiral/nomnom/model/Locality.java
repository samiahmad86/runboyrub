package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 11/8/15.
 */
public class Locality {
    @Expose
    public long id;
    @Expose
    public String name;
    @Expose
    public String latitude;
    @Expose
    public String longitude;
    @SerializedName("delivery_radius")
    @Expose
    public long deliveryRadius;
    @Expose
    public City city;

}
