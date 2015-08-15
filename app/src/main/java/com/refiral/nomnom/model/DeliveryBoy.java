package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 14/8/15.
 */
public class DeliveryBoy {

    @Expose
    public long id;
    @Expose
    public Agent agent;
    @SerializedName("bike_number")
    @Expose
    public String bikeNumber;
    @Expose
    public String latitude;
    @Expose
    public String longitude;
    @SerializedName("latlon_timestamp")
    @Expose
    public String latlonTimestamp;

}
