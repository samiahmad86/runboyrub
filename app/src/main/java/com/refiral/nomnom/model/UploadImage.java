package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.refiral.nomnom.config.Constants;

import retrofit.mime.TypedFile;

/**
 * Created by tanay on 24/8/15.
 */
public class UploadImage {

    @Expose
    @SerializedName(Constants.Keys.KEY_ORDER_ID)
    public long id;

    @Expose
    @SerializedName(Constants.Keys.KEY_BILL_IMAGE)
    public TypedFile billImage;

    public UploadImage(long id, TypedFile file) {
        this.billImage = file;
        this.id = id;
    }
}
