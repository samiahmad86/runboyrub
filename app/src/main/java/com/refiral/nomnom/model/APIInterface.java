package com.refiral.nomnom.model;

import com.refiral.nomnom.config.Constants;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by tanay on 7/8/15.
 */
public interface APIInterface {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("/agent_login/")
    LoginResponse login(@Header(Constants.Keys.KEY_DEVICE_ID) String mDeviceID, @Header(Constants.Keys.KEY_DEVICE_TYPE) String mDeviceTpe, @Header(Constants.Keys.KEY_PUSH_ID) String mPushID, @Body User user);

    @Headers({
            "Content-Type: application/json"
    })
    @DELETE("/agent_logout/")
    SimpleResponse logout(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken);

    @Headers({
            "Content-Type: application/json"
    })
    @GET("/delivery/")
    Order getOrder(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderId);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/delivery/")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderID, @Query(Constants.Keys.KEY_DELIVERY_STATUS) String deliveryStatus);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/delivery/")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderID, @Query(Constants.Keys.KEY_DELIVERY_STATUS) String deliveryStatus, @Part(Constants.Keys.KEY_BILL_PHOTO) TypedFile billImage, @Query(Constants.Keys.KEY_AMOUNT_PAID) String ammount);


    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/delivery/")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Query(Constants.Keys.KEY_ORDER_ID) int orderID, @Query(Constants.Keys.KEY_DELIVERY_STATUS) String deliveryStatus, @Query(Constants.Keys.KEY_PAYMENT_CARD) String cardPayment, @Query(Constants.Keys.KEY_PAYMENT_CASH) String cashPayment);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("/delivery_boy/")
    SimpleResponse updateLocation(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Body DeliveryBoyLocation location);

}
