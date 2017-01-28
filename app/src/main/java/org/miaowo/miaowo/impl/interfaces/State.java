package org.miaowo.miaowo.impl.interfaces;

import android.support.annotation.WorkerThread;

import org.miaowo.miaowo.bean.data.User;

/**
 * 用于注册和登录的相关操作
 * Created by luqin on 16-12-30.
 */

public interface State {

    /**
     * 登录
     * @param u 包含登录信息的用户对象
     * @throws Exception 失败返回结果
     */
    @WorkerThread
    void login(User u) throws Exception;

    /**
     * 登出
     */
    void logout();

    /**
     * 注册
     * @param u 包含用户注册信息的用户对象
     * @throws Exception 失败返回结果
     */
    @WorkerThread
    void regist(User u) throws Exception;

    /**
     * 用于注销用户
     * @param u 要注销的用户
     * @throws Exception 失败返回结果
     */
    @WorkerThread
    void remove(User u) throws Exception;

    /**
     * 用于校验本机是否已登录
     * @return 登录则返回当前登陆的 User， 否则返回null
     */
    User getLocalUser();
}
