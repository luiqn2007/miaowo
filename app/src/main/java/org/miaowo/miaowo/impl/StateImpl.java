package org.miaowo.miaowo.impl;

import android.text.TextUtils;

import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.HttpUtil;

/**
 * {@link State} 的具体实现类
 * Created by luqin on 16-12-30.
 */

public class StateImpl implements State {

    @Override
    public void login(User u) {
        u.email = "login@login";
        try {
            checkUser(u);
            HttpUtil.utils().login(u);
        } catch (Exception e) {
            D.getInstance().activeActivity.handleError(e);
        }
    }

    @Override
    public void logout() {
        D.getInstance().thisUser = D.getInstance().guest;
    }

    @Override
    public void register(User u) {
        try {
            checkUser(u);
            HttpUtil.utils().register(u);
        } catch (Exception e) {
            D.getInstance().activeActivity.handleError(e);
        }
    }

    @Override
    public void remove(User u) {
        D.getInstance().activeActivity.handleError(new Exception("暂时搞不懂怎么消除的"));
    }

    @Override
    public boolean isLogin() {
        return D.getInstance().thisUser.uid > 0;
    }

    private void checkUser(User u) throws Exception {
        if (TextUtils.isEmpty(u.username))
            throw Exceptions.E_ILL_USER_PWD;
        if (TextUtils.isEmpty(u.password) || u.username.equals(u.password)) {
            throw Exceptions.E_ILL_USER_PWD;
        }
        if (TextUtils.isEmpty(u.email) || !u.email.contains("@")) {
            throw Exceptions.E_ILL_USER_EMAIL;
        }
    }
}
