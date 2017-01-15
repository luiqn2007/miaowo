package org.miaowo.miaowo.impl;

import android.graphics.Path;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.PwdUtil;

/**
 * {@link State} 的具体实现类
 * Created by luqin on 16-12-30.
 */

public class StateImpl implements State {

    @Override
    @WorkerThread
    public void login(User u) throws Exception {
        checkUser(u);
        T.isLogin = true;
        String rlPwd = PwdUtil.getMD5(u.getPwd(), u.getName());
        if (T.users.size() <= 1) {
            throw Exceptions.E_NULL_USER;
        }
        for (User user : T.users) {
            if (user.getName().equals(u.getName()) && user.getPwd().equals(rlPwd)) {
                T.localUser = user;
                return;
            }
        }
        throw Exceptions.E_WRONG_USER_PWD;
    }

    @Override
    public void logout() {
        T.localUser = T.users.get(0);
        T.isLogin = false;
    }

    @Override
    @WorkerThread
    public void regist(User u) throws Exception {
        checkUser(u);
        String pwd = PwdUtil.getMD5(u.getPwd(), u.getName());
        T.users.add(new User(T.users.size(), u.getName(), u.getSummary(), pwd, T.getRadomImgUrl()));
        T.localUser = T.users.get(T.users.size() - 1);
        T.isLogin = true;
    }

    @Override
    @WorkerThread
    public void remove(User u) throws Exception {
        T.isLogin = false;
    }

    @Override
    public User getLocalUser() {
        return T.localUser;
    }

    private void checkUser(User u) throws Exception {
        if (TextUtils.isEmpty(u.getName()))
            throw Exceptions.E_ILL_USER_PWD;
        if (TextUtils.isEmpty(u.getPwd()) || PwdUtil.getMD5("", u.getName()).equals(u.getPwd()))
            throw Exceptions.E_ILL_USER_PWD;
    }
}
