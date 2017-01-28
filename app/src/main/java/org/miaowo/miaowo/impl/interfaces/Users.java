package org.miaowo.miaowo.impl.interfaces;


import android.support.annotation.WorkerThread;

import org.miaowo.miaowo.bean.data.User;

import java.util.ArrayList;

/**
 * 用于用户的查询，点赞等相关操作
 * Created by lq2007 on 16-11-23.
 */

public interface Users {
    /**
     * 获取一个用户的信息
     * 获取的 User 包含除 pwd 的所有信息
     * @param id 获取用户的ID
     * @return 返回的用户，若未登录则返回用户id为-1的用户(Guest)
     */
    User getUser(long id);

    /**
     * 根据用户名获取用户信息
     * 获取的 User 包含除 pwd 的所有信息
     * @param userName 要搜索的用户名
     * @return 使用该昵称的用户集
     */
    User[] searchUsers(String userName) throws Exception;

    /**
     * 粉丝
     * 传入的 User 需要有效的 id, favorite 和 isFavorite
     * @param u 用户
     * @param auto 自动反向操作
     * @throws Exception 申请失败返回结果
     */
    void focusUser(User u, boolean auto) throws Exception;
}
