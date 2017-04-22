package org.miaowo.miaowo.impl;

import android.text.TextUtils;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.ChatButton;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * {@link State} 的具体实现类
 * Created by luqin on 16-12-30.
 */

public class StateImpl implements State {

    private HttpUtil http;
    private JsonUtil json;
    private static User thisUser;

    public StateImpl() {
        http = HttpUtil.utils();
        json = JsonUtil.utils();
    }

    @Override
    public User loginUser() {
        return thisUser;
    }

    @Override
    public void setUser(User user) {
        thisUser = user;
    }

    @Override
    public void login(String user, String pwd) {
        if (checkUser(user, pwd)) {
            BaseActivity.get.setProcess(0, "获取服务器信息...");
            http.post(BaseActivity.get.getString(R.string.url_login), (call, response) -> {
                try {
                    String token = json.getToken(response);
                    FormBody body = new FormBody.Builder()
                            .add("username", user)
                            .add("password", pwd)
                            .add("remember", "on").build();
                    Request login = new Request.Builder().url(BaseActivity.get.getString(R.string.url_login))
                            .post(body)
                            .addHeader("x-csrf-token", token)
                            .build();
                    BaseActivity.get.setProcess(33, "正在登录...");
                    http.newCall(login).enqueue(new HttpUtil.MyCallback((call1, response1) -> {
                        String msg = response1.body().string();
                        if (msg.startsWith("[[error")) {
                            throw new IOException(msg.substring(2, msg.length() - 2));
                        }
                        BaseActivity.get.setProcess(66, "正在获取用户信息...");
                        Request userR = new Request.Builder().url(String.format(BaseActivity.get.getString(R.string.url_user), user)).build();
                        loginResult(json.buildFromAPI(http.newCall(userR).execute(), User.class));
                    }));
                } catch (JSONException e) {
                    throw new IOException(BaseActivity.get.getString(R.string.err_token));
                }
            }, (call, e) -> BaseActivity.get.processError(e));
        }
    }

    @Override
    public void logout() {
        HttpUtil.utils().clearCookies();
        ChatButton.hide();
        Miao.fg_miao.prepareLogin();
        thisUser = null;
    }

    @Override
    public void register(String user, String pwd, String email) {
        if (checkUser(user, pwd, email)) {
            BaseActivity.get.setProcess(0, "获取服务器信息...");
            http.post(BaseActivity.get.getString(R.string.url_login), (call, response) -> {
                try {
                    String token = json.getToken(response);
                    FormBody body = new FormBody.Builder()
                            .add("username", user)
                            .add("password", pwd)
                            .add("email", email)
                            .add("remember", "on").build();
                    Request login = new Request.Builder().url(BaseActivity.get.getString(R.string.url_register))
                            .post(body).addHeader("x-csrf-token", token)
                            .build();
                    BaseActivity.get.setProcess(33, "正在登录...");
                    http.newCall(login).enqueue(new HttpUtil.MyCallback((call1, response1) -> {
                        String msg = response.body().string();
                        if (msg.startsWith("[[error")) {
                            throw new IOException(msg.substring(2, msg.length() - 2));
                        }
                        BaseActivity.get.setProcess(66, "正在获取用户信息...");
                        Request userR = new Request.Builder().url(String.format(BaseActivity.get.getString(R.string.url_user), user)).build();
                        loginResult(json.buildFromAPI(http.newCall(userR).execute(), User.class));
                    }));
                } catch (JSONException e) {
                    throw new IOException(BaseActivity.get.getString(R.string.err_token));
                }
            }, (call, e) -> BaseActivity.get.processError(e));
        }
    }

    @Override
    public void remove(String user, String pwd) {
        // TODO: 注销用户
    }

    @Override
    public boolean isLogin() {
        return thisUser != null;
    }

    private void loginResult(User user) {
        BaseActivity activity = BaseActivity.get;
        if (user.getUid() == 0) {
            activity.processError(new Exception("登录失败, 请重新登录再试一次"));
            return;
        }
        setUser(user);
        activity.setProcess(100, "欢迎回来, " + user.getUsername());
        if (activity instanceof Miao) {
            BaseActivity.get.runOnUiThreadIgnoreError(() -> Miao.fg_miao.loginSucceed());
        }
    }
    private boolean checkUser(String username, String password, String email) {
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            BaseActivity.get.toast(BaseActivity.get.getString(R.string.err_email), TastyToast.ERROR);
            return false;
        }
        return checkUser(username, password);
    }
    private boolean checkUser(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            BaseActivity.get.toast(BaseActivity.get.getString(R.string.err_username), TastyToast.ERROR);
            return false;
        }
        if (TextUtils.isEmpty(password) || username.equals(password) || password.length() < 6) {
            BaseActivity.get.toast(BaseActivity.get.getString(R.string.err_password), TastyToast.ERROR);
            return false;
        }
        return true;
    }
}
