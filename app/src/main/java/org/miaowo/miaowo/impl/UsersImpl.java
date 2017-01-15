package org.miaowo.miaowo.impl;


import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.set.Exceptions;

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
        User localUser = (new StateImpl()).getLocalUser();
        if (localUser.getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
        }
        u.setFavorite(!u.isFavorite());
    }

    @Override
    public void focusUser(User u) throws Exception {
        User localUser = (new StateImpl()).getLocalUser();
        if (localUser.getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
        }
        u.setFocus(!u.isFocus());
    }
}
