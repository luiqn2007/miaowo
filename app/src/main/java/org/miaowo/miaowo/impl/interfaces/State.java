package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.User;

/**
 * 用于注册和登录的相关操作
 * Created by luqin on 16-12-30.
 */

public interface State {

    /**
     * 登录
     * @param u 包含登录信息的用户对象
     */
    void login(User u);

    /**
     * 登出
     */
    void logout();

    /**
     * 注册
     * @param u 包含用户注册信息的用户对象
     */
    void register(User u);

    /**
     * 用于注销用户
     * @param u 要注销的用户
     */
    void remove(User u);

    /**
     * 用于校验本机是否已登录
     * @return 验证是否已经登录
     */
    boolean isLogin();
}
