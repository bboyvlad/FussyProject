package com.twocheckout;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;

public abstract class TwocheckoutNotification {

    public static Boolean check(HashMap<String, String> args, String secret) {
        Boolean response;
        String hashString = args.get("sale_id")+args.get("vendor_id")+args.get("invoice_id")+secret;
        String hash = DigestUtils.md5Hex( hashString ).toUpperCase();
        if (args.get("md5_hash").equals(hash)) {
            response = true;
        } else {
            response = false;
        }
        return response;
    }

}
