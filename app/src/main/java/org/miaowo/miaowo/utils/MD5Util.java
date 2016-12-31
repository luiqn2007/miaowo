package org.miaowo.miaowo.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Util {

    /**
     * MD5 加密密码
     * @param pwd 真实密码
     * @param key 组合密码的一部分
     * @return 经过 MD5 加密后的密码
     */
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