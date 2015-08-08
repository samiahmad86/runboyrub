package com.refiral.nomnom.model;

import com.refiral.nomnom.config.Constants;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by tanay on 7/8/15.
 */
public interface APIInterface {

    @GET("/agent_login")
    LoginResponse login(@Header(Constants.Keys.KEY_DEVICE_ID) String mDeviceID, @Header(Constants.Keys.KEY_DEVICE_TYPE) String mDeviceTpe, @Header(Constants.Keys.KEY_PUSH_ID) String mPushID, @Query("contact_number") String contactNumber);

    @DELETE("/agent_logout")
    SimpleResponse logout(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken);

    @GET("/delivery")
    Order getOrder(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderId);

    @PUT("/delivery")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderID, @Query(Constants.Keys.KEY_DELIVERY_STATUS) String deliveryStatus);

    @PUT("/delivery")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderID, @Query(Constants.Keys.KEY_DELIVERY_STATUS) String deliveryStatus, @Query(Constants.Keys.KEY_PAYMENT_CARD) String cardPayment, @Query(Constants.Keys.KEY_PAYMENT_CASH) String cashPayment);

    @POST("/delivery_boy_location")
    SimpleResponse updateLocation(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_LATITUDE) double latitude, @Query(Constants.Keys.KEY_LONGITUDE) double longitue);

}
