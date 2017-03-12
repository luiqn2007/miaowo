package org.miaowo.miaowo.set;

/**
 * 错误集合
 * Created by luqin on 17-1-15.
 */

public class Exceptions {
    public static Exception E_NON_LOGIN = new Exception("请先登录");
    public static Exception E_WRONG_USER_PWD = new Exception("用户名或密码错误");
    public static Exception E_ILL_USER_PWD = new Exception("用户名或密码不合法");
    public static Exception E_ILL_USER_EMAIL = new Exception("电子邮件不合法");
    public static Exception E_HAD_USER = new Exception("当前账户已被注册");

    public static Exception E_NO_TYPE = new Exception("未知类别");
    public static Exception E_EMPTY_MESSAGE = new Exception("内容不能为空");

    public static Exception E_BAD_CAMERA = new Exception("无法打开摄像头应用");

    public static Exception E_NONE = new Exception("暂未实现");
}