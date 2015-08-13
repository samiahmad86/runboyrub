package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanay on 7/8/15.
 */
public class Order {
    @Expose
    public Customer customer;
    @Expose
    public Address address;
    @Expose
    public Restaurant restaurant;
    @SerializedName("order_items")
    @Expose
    public ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    @SerializedName("vat_tax")
    @Expose
    public String vatTax;
    @SerializedName("service_tax")
    @Expose
    public String serviceTax;
    @SerializedName("discount_amount")
    @Expose
    public long discountAmount;
    @SerializedName("delivery_charges")
    @Expose
    public long deliveryCharges;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("special_instructions")
    @Expose
    public String specialInstructions;
    @Expose
    public String status;
    @SerializedName("expected_delivery_time")
    @Expose
    public String expectedDeliveryTime;
    @SerializedName("expected_pickup_time")
    @Expose
    public String expectedPickupTime;
    @SerializedName("payment_cash")
    @Expose
    public long paymentCash;
    @SerializedName("payment_card")
    @Expose
    public long paymentCard;
}