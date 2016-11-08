package jd.Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by eduardom on 10/3/16.
 */
public final class AppMappings {

    public static final String GIFTCARD="/giftcard";
    public static final String LOCATION="/location";
    public static final String PRODUCTS="/products";
    public static final String MYAIRCRAFT="/myaircraft";
    public static final String AIRCRAFTYPE="/aircraftype";
    public static final String CATALOG="/catalog";
    public static final String CARD="/card";
    public static final String BANK="/bank";
    // discoverability

    public static final class Singural {

        public static final String BANK="/bank";
        public static final String CARD="/card";
        public static final String GIFTCARD="/giftcard";
        public static final String LOCATION="/location";
        public static final String PRODUCTS="/products";
        public static final String MYAIRCRAFT="/myaircraft";
        public static final String AIRCRAFTYPE="/aircraftype";
        public static final String CATALOG="/catalog";

    }

    private AppMappings() {
        throw new AssertionError();
    }

}
