package org.miaowo.miaowo.impl;


import org.miaowo.miaowo.bean.data.EventMsg;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * {@link Users} 的具体实现类
 * Created by lq2007 on 16-11-23.
 */

public class UsersImpl implements Users {

    @Override
    public User getUser(int id) {
        return new User();
    }

    @Override
    public void searchUsers(String userName) {
        try {
            String searchUrl = "https://miaowo.org/search?term="
                    + URLEncoder.encode(userName, "UTF-8")
                    + "&in=users";
            HttpUtil.utils().post(searchUrl, EventMsg.DATA_TYPE.SEARCH_USER);
        } catch (UnsupportedEncodingException e) {
            D.getInstance().activeActivity.handleError(e);
        }
    }

    @Override
    public void focusUser(User u) {
        D.getInstance().activeActivity.handleError(new Exception("暂时搞不懂怎么关注的"));
    }

    @Override
    public void updateUser(User u) {
        D.getInstance().activeActivity.handleError(new Exception("暂时搞不懂怎么修改的"));
    }
}
