package org.miaowo.miaowo.set;

/**
 * 错误集合
 * Created by luqin on 17-1-15.
 */

public class Exceptions {
    public static Exception E_NON_LOGIN = new Exception("请先登录");

    public static Exception E_WRONG_CHAT_MSG = new Exception("无效聊天信息");

    public static Exception E_NULL_USER = new Exception("系统内无用户");
    public static Exception E_WRONG_USER_PWD = new Exception("用户名或密码错误");
    public static Exception E_ILL_USER_PWD = new Exception("用户名或密码不合法");
}