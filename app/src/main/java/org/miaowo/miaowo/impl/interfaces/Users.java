package org.miaowo.miaowo.impl.interfaces;


import org.miaowo.miaowo.bean.data.Search;
import org.miaowo.miaowo.bean.data.User;

/**
 * 用于用户的查询，点赞等相关操作
 * Created by lq2007 on 16-11-23.
 */

public interface Users {
    /**
     * 获取一个用户的信息
     * 获取的 User 包含除 password 的所有信息
     * @param id 获取用户的ID
     * @return 返回的用户，若未登录则返回用户id为-1的用户(Guest)
     */
    User getUser(int id);

    /**
     * 根据用户名获取用户信息
     * 获取的 User 包含除 password 的所有信息
     * @param userName 要搜索的用户名
     */
    void searchUsers(String userName);

    /**
     * 关注
     * @param u 用户
     * @throws Exception 申请失败返回结果
     */
    void focusUser(User u);

    /**
     * 更新用户信息
     * @param u 新用户信息
     */
    void updateUser(User u);
}
