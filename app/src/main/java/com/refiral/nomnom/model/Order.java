package com.refiral.nomnom.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanay on 7/8/15.
 */
public class Order implements Parcelable {

    @Override
    public int describeContents() {
        return -1;
    }

    @Override
    public void writeToParcel(Parcel in, int arg) {

    }

    protected Order(Parcel in) {

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}
