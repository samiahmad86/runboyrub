package com.refiral.nomnom.service;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.refiral.nomnom.config.Router;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 8/8/15.
 */
public class GCMTokenRefreshService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Logout the runner
        PrefUtils.deleteAccessToken();
        Router.registerGCM(this);
    }

}
