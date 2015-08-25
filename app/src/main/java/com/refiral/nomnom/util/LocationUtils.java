package com.refiral.nomnom.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.refiral.nomnom.service.AddressIntentService;

import java.io.IOException;
import java.util.List;

/**
 * Created by tanay on 25/8/15.
 */
public class LocationUtils {

    /**
     * create a {@link GoogleApiClient} to be used for fetching location
     * @param context of the component making the location request
     * @param connectionCallbacks actions to do when connection is successfully established
     * @param onConnectionFailedListener action when connection fails
     * @return {@link GoogleApiClient}
     */
    public static GoogleApiClient buildGoogleApiClientObject(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks,
                                                      GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * define characteristics of the request to be made
     * @param interval desired interval between location requests in millisecond, inexact
     * @param fastestInterval fastest interval between location request
     * @param distance the minimum distance covered before location request is made
     * @return {@link LocationRequest} with specified characteristics and high accuracy
     */
    public static LocationRequest getLocationRequest(long interval, long fastestInterval, float distance) {
        return new LocationRequest().setFastestInterval(fastestInterval)
                .setInterval(interval)
                .setSmallestDisplacement(distance)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     *
     * @param context of the component making the location request
     * @param address of either the customer address {@link com.refiral.nomnom.model.Address#completeAddress}
     *                or restaurant address {@link com.refiral.nomnom.model.Restaurant#address}
     * @return {@link LatLng} instance to use in maps for destination
     */
    public static LatLng getLocationFromAddress(Context context, String address) {
        Log.d(AddressIntentService.TAG, address);
        Geocoder mGeocoder = new Geocoder(context);
        try {
            List<Address> addresses = mGeocoder.getFromLocationName(address, 5);
            if (addresses == null) {
                Log.d(AddressIntentService.TAG, "addresses are null");
                return null;
            } else {
                Log.d(AddressIntentService.TAG, "addresses are not null");
            }
            if (addresses.size() > 0) {
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            } else {
                Log.d(AddressIntentService.TAG, "addresses size is zero");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Log.d(AddressIntentService.TAG, "IOException");
            return null;
        }
        Log.d(AddressIntentService.TAG, "returning null");
        return null;
    }
}
