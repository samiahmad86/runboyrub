package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.model.UploadImage;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 24/8/15.
 */
public class UploadRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private UploadImage uploadImage;

    public UploadRequest(UploadImage uploadImage) {
        super(SimpleResponse.class, APIInterface.class);
        this.uploadImage = uploadImage;
    }

    @Override
    public SimpleResponse loadDataFromNetwork() throws Exception {
//        return getService().uploadImage(PrefUtils.getAccessToken(), uploadImage.id, uploadImage.billImage);
        return getService().uploadImage(PrefUtils.getAccessToken(), uploadImage.billImage);
    }
}
