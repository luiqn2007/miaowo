package org.miaowo.miaowo.impl.interfaces;


import org.miaowo.miaowo.bean.data.web.User;

/**
 * 用于用户的查询，点赞等相关操作
 * Created by lq2007 on 16-11-23.
 */

public interface Users {
    /**
     * 获取一个用户的信息
     * 获取的 User 包含除 password 的所有信息
     * @return 返回的用户，若未登录则返回用户id为-1的用户(Guest)
     */
    User getUser(String name);

    /**
     * 关注
     * @param u 用户
     * @throws Exception 申请失败返回结果
     */
    void focusUser(User u);

    /**
     * 更新用户信息
     */
    void updateUser(String user, String pwd, String email);

    /**
     * 更新用户头像信息
     */
    void updateUserHead(String headString);
}
