package com.refiral.nomnom.service;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by tanay on 8/8/15.
 */
public class GcmTokenRefreshService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        CustomIntentService.registerGCM(this);
    }

}
