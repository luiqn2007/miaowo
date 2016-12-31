package org.miaowo.miaowo.impls;


import org.miaowo.miaowo.T;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.interfaces.Users;

import java.util.ArrayList;


/**
 * {@link Users} 的具体实现类
 * Created by lq2007 on 16-11-23.
 */

public class UsersImpl implements Users {

    @Override
    public User getUser(long id) {
        if (id < 0) {
            return null;
        } else {
            return new User(id, "实例用户", "的点点滴滴多多多多多多多多多多多多多多", 0, 1, 2, 3, true, 4, false, 500, T.getRadomImgUrl());
        }
    }

    @Override
    public ArrayList<User> searchUsers(String userName) {
        return null;
    }

    @Override
    public void likeUser(User u) throws Exception {
        u.setFavorite(!u.isFavorite());
    }

    @Override
    public void focusUser(User u) throws Exception {
        u.setFocus(!u.isFocus());
    }
}
