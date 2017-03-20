package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.User;

/**
 * 用于注册和登录的相关操作
 * Created by luqin on 16-12-30.
 */

public interface State {

    /**
     * 当前登陆的用户
     * @return 已登录的用户
     */
    User loginedUser();

    /**
     * 登陆成功时，使用此方法记录登陆成功的用户
     */
    void setUser(User user);

    /**
     * 登录
     */
    void login(String user, String pwd);

    /**
     * 登出
     */
    void logout();

    /**
     * 注册
     */
    void register(String user, String pwd, String email);

    /**
     * 用于注销用户user
     */
    void remove(String user, String pwd);

    /**
     * 用于校验本机是否已登录
     * @return 验证是否已经登录
     */
    boolean isLogin();
}
