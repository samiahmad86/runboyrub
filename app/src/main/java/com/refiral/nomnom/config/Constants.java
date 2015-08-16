package com.refiral.nomnom.config;

/**
 * Created by tanay on 7/8/15.
 */
public class Constants {

    public class Keys {
        public static final String PREF_NAME = "nonomPrefs";
        public static final String STARTER_CLASS = "starterClass";
        public static final String KEY_ACCESS_TOKEN = "token";
        public static final String KEY_ORDER_ID = "order_id";
        public static final String KEY_ORDER = "order";
        public static final String KEY_DEVICE_ID = "X-Device-id";
        public static final String KEY_DEVICE_TYPE = "X-Device-Type";
        public static final String KEY_PUSH_ID = "X-Push-Id";
        public static final String KEY_DELIVERY_STATUS = "delivery_status";
        public static final String KEY_PAYMENT_CARD = "payment_card";
        public static final String KEY_PAYMENT_CASH = "payment_cash";
        public static final String KEY_AMOUNT_PAID = "amt_paid";
        public static final String KEY_LATITUDE = "latitude";
        public static final String KEY_LONGITUDE = "longitude";
        public static final String KEY_NOTIF_ID = "notif_id";
        public static final String KEY_BILL_PHOTO = "bill_photo";
        public static final String KEY_CONTACT_NUMBER = "contact_number";
        public static final String KEY_DELIVERY_BOY_STATUS = "delivery_boy_status";
        public static final String KEY_STATUS = "status";
    }

    public class Values {
        public static final long FIVE_MINUTES_IN_MILLIS = 5 * 60 * 1000;
        public static final int STATUS_PLACEHOLDER = 0;
        public static final int STATUS_CONFIRMED = 1;
        public static final int STATUS_ARRIVED_AT_RESTAURANT = 2;
        public static final int STATUS_PICKUP_MATCH = 3;
        public static final int STATUS_REACHED_CUSTOMER_ADDRESS = 4;
        public static final int STATUS_DELIVERED = 5;
        public static final int STATUS_PICKUP_PAY = 6;
        public static final int STATUS_PICKUP_PHOTO = 7;
        public static final int STATUS_PICKUP_CONFIRM = 8;
        public static final int STATUS_STARTING_DELIVERY = 9;
        public static final String ORDER_STATUS_CANCELLED = "cancelled";
        public static final String ORDER_STATUS_UPDATE = "updated";
        public static final String ORDER_STATUS_NEW = "new";
        public static final String STATUS_STR_CONFIRMED = "delivery_confirmed";
        public static final String STATUS_STR_ARRIVED = "arrived";
        public static final String STATUS_STR_PICKUP = "pick_up";
        public static final String STATUS_STR_REACHED = "reached";
        public static final String STATUS_STR_DELIVERED = "delivered";

    }

    public class Urls {
        public static final String BASE_URL = "http://api.gonomnom.in/";
    }

    public class Regex {
        public static final String NUMBER = "^[789]\\d{9}$";
    }
}
