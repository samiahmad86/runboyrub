package com.refiral.nomnom.model;

import com.refiral.nomnom.config.Constants;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
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
    LoginResponse login(@Header(Constants.Keys.KEY_DEVICE_ID) String mDeviceID,
                        @Header(Constants.Keys.KEY_DEVICE_TYPE) String mDeviceTpe,
                        @Header(Constants.Keys.KEY_PUSH_ID) String mPushID, @Body User user);

    @Headers({
            "Content-Type: application/json"
    })
    @DELETE("/agent_logout/")
    SimpleResponse logout(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken);

    @Headers({
            "Content-Type: application/json",
            "X-Device-Type: android"
    })
    @GET("/order/")
    Order getOrder(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken,
                   @Query(Constants.Keys.KEY_ORDER_ID) long orderId, @Query("timestamp") long timeStamp);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/delivery/")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken, @Body Status status);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/delivery/")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken,
                                @Body StatusDelivered statusPayRestaurant);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/delivery/")
    SimpleResponse updateStatus(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken,
                                @Body StatusPayRestaurant statusDelivered);


    @Headers({
            "Content-Type: multipart/form-data",
    })
    @Multipart
    @PUT("/bill_image/")
    SimpleResponse uploadImage(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken,
                                @Part(Constants.Keys.KEY_ORDER_ID) long orderId,
                               @Part(Constants.Keys.KEY_BILL_IMAGE) TypedFile billImage);

    @Headers({
            "Content-Type: multipart/form-data",
    })
    @Multipart
    @PUT("/bill_image/")
    SimpleResponse uploadImage(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken,
                               @Part(Constants.Keys.KEY_BILL_IMAGE) TypedFile billImage);


    @Headers({
            "Content-Type: application/json"
    })
    @POST("/delivery_boy/")
    SimpleResponse updateLocation(@Header(Constants.Keys.KEY_ACCESS_TOKEN) String accessToken,
                                  @Body DeliveryBoyLocation location);

}
