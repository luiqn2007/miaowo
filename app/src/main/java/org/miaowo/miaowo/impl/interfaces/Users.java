package org.miaowo.miaowo.impl.interfaces;


import android.graphics.Bitmap;

import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.util.HttpUtil;

/**
 * 用于用户的查询，点赞等相关操作
 * Created by lq2007 on 16-11-23.
 */

public interface Users {
    /**
     * 获取一个用户的信息
     * 获取的 User 包含除 password 的所有信息
     */
    void getUser(String name, HttpUtil.CallbackRun callback, HttpUtil.CallbackErr onErr);

    /**
     * 关注
     * @param u 用户
     */
    void focusUser(User u);

    /**
     * 更新用户信息
     */
    void updateUser(String user, String pwd, String email);

    /**
     * 更新用户头像信息
     */
    void updateUserHead(Bitmap head);
}
