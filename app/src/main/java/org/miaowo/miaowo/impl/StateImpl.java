package org.miaowo.miaowo.impl;

import android.content.ContentValues;
import android.text.TextUtils;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.HttpUtil;

/**
 * {@link State} 的具体实现类
 * Created by luqin on 16-12-30.
 */

public class StateImpl implements State {

    private static User thisUser;
    private BaseActivity mContext;

    public StateImpl(BaseActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public User loginedUser() {
        return thisUser;
    }

    @Override
    public void setUser(User user) {
        thisUser = user;
    }

    @Override
    public void login(String user, String pwd) {
        try {
            checkUser(user, pwd, "login@miaowo.org");
            ContentValues loginMsg = new ContentValues();
            loginMsg.put("user", user);
            loginMsg.put("password", pwd);
            HttpUtil.utils().login(mContext, loginMsg);
            TastyToast.makeText(mContext, "登录中...", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING).show();
        } catch (Exception e) {
            mContext.handleError(e);
        }
    }

    @Override
    public void logout() {
        HttpUtil.utils().clearCookies();
        thisUser = null;
    }

    @Override
    public void register(String user, String pwd, String email) {
        try {
            checkUser(user, pwd, email);
            ContentValues regMsg = new ContentValues();
            regMsg.put("user", user);
            regMsg.put("password", pwd);
            regMsg.put("email", email);
            HttpUtil.utils().register(mContext, regMsg);
        } catch (Exception e) {
            mContext.handleError(e);
        }
    }

    @Override
    public void remove(String user, String pwd) {
        mContext.handleError(new Exception("暂时搞不懂怎么消除的"));
    }

    @Override
    public boolean isLogin() {
        return thisUser != null;
    }

    private void checkUser(String username, String password, String email) throws Exception {
        if (TextUtils.isEmpty(username))
            throw Exceptions.E_ILL_USER_PWD;
        if (TextUtils.isEmpty(password) || username.equals(password)) {
            throw Exceptions.E_ILL_USER_PWD;
        }
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            throw Exceptions.E_ILL_USER_EMAIL;
        }
    }
}
