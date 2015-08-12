package com.refiral.nomnom.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tanay on 11/8/15.
 */
public class AtomicUtils {

    private final static AtomicInteger mId = new AtomicInteger(0);
    private final static AtomicInteger mRequestCode = new AtomicInteger(100);

    public static int getId() {
        return mId.incrementAndGet();
    }


    public static int getRequestCode() {
        return mRequestCode.incrementAndGet();
    }

}
