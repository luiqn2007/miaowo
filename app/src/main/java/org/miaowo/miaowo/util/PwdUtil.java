package org.miaowo.miaowo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class PwdUtil {

    /**
     * 加密密码
     * @param pwd 真实密码
     * @param key 组合密码的一部分
     * @return 经过加密后的密码
     */
    public static String toPwd(String pwd, String key) {
        String relPwd = "mw_p=" + pwd + "&mw_n=" + key;
        MessageDigest md;
        try {
            // 没办法，360代码卫士不让用 MD5
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(relPwd.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }
}