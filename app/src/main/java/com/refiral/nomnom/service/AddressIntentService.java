package com.refiral.nomnom.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.util.LocationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class AddressIntentService extends IntentService {

    public static final String TAG = AddressIntentService.class.getName();

    public AddressIntentService() {
        super("AddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (getResources().getString(R.string.intent_filter_location).equalsIgnoreCase(intent.getAction())) {
                LatLng mLatLng = LocationUtils.getLocationFromAddress
                        (this, intent.getStringExtra(Constants.Keys.KEY_ADDRESS).replace(" ", "%20"));
                Intent iLocationBroadcast = new Intent(getResources().getString(R.string.intent_filter_location));
                double lat = 0, lng = 0;
                if (mLatLng == null) {
                    Log.d(TAG, "latlng is null");
                    String address = intent.getStringExtra(Constants.Keys.KEY_ADDRESS).replace(" ", "%20");
                    String url = "http://maps.google.com/maps/api/geocode/json?address=" +
                            address + "&sensor=false";
                    StringBuilder stringBuilder = new StringBuilder();
                    try {
                        URL mURL = new URL(url);
                        Log.d(TAG, mURL.toString());
                        HttpURLConnection httpURLConnection = (HttpURLConnection) mURL.openConnection();
                        InputStream stream = httpURLConnection.getInputStream();
                        int b;
                        while ((b = stream.read()) != -1) {
                            stringBuilder.append((char) b);
                        }
                    } catch (MalformedURLException e) {
                        Log.d(TAG, "Fuck her");
                        e.printStackTrace();
                    } catch (IOException ex) {
                        Log.d(TAG, "Fuck you");
                        ex.printStackTrace();
                    }
                    try {
                       JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                        JSONObject json = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                                .getJSONObject("geometry").getJSONObject("location");

                        lat = json.getDouble("lat");
                        lng = json.getDouble("lng");

                    } catch (JSONException je) {
                        Log.d(TAG, "fuck me");
                        je.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "latitude " + (mLatLng == null));
                    lat = mLatLng.latitude;
                    lng = mLatLng.longitude;
                }
                iLocationBroadcast.putExtra(Constants.Keys.KEY_LATITUDE, lat);
                iLocationBroadcast.putExtra(Constants.Keys.KEY_LONGITUDE, lng);
                LocalBroadcastManager.getInstance(this).sendBroadcast(iLocationBroadcast);

            }
        }
    }

}
