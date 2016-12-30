package org.miaowo.miaowo.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Util {

    public static String getMD5(String pwd, String key) {
        String relPwd = "mw_p=" + pwd + "&mw_n=" + key;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(relPwd.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }
}