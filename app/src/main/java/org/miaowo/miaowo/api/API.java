package org.miaowo.miaowo.api;

import android.text.TextUtils;

import org.json.JSONException;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.ChatButton;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class API {
    public static User loginUser;
    public static String token;

    private String mUrl;
    private HttpUtil mHttp;
    private JsonUtil mJson;

    public API() {
        mUrl = BaseActivity.get.getString(R.string.url_api);
        mHttp = HttpUtil.utils();
        mJson = JsonUtil.utils();
    }

    public void useAPI(APIType api, String sub, Method method, boolean useToken, FormBody body, HttpUtil.CallbackRun callback) {
        String url = String.format(mUrl, api.api(), sub);
        Request.Builder request = new Request.Builder()
                .url(url)
                .method(method.method(), body);
        if (useToken) request.addHeader("Authorization", "Bearer " + token);
        mHttp.post(request.build(), callback);
    }
    public enum Method {
        POST() {
            @Override
            String method() {
                return "POST";
            }
        }, PUT() {
            @Override
            String method() {
                return "PUT";
            }
        }, GET() {
            @Override
            String method() {
                return "GET";
            }
        }, DELETE() {
            @Override
            String method() {
                return "DELETE";
            }
        };

        String method() {
            return "";
        }
    }
    public enum APIType {
        USERS() {
            @Override
            String api() {
                return "users";
            }
        }, CATEGORIES() {
            @Override
            String api() {
                return "categories";
            }
        }, GROUPS() {
            @Override
            String api() {
                return "groups";
            }
        }, TOPICS() {
            @Override
            String api() {
                return "topics";
            }
        }, POSTS() {
            @Override
            String api() {
                return "posts";
            }
        }, UTIL() {
            @Override
            String api() {
                return "util";
            }
        };

        String api() {
            return "";
        }
    }

    public void login(String user, String pwd) {
        if (checkUser(user, pwd)) {
            BaseActivity.get.setProcess(0, "获取服务器信息...");
            Request request = new Request.Builder().url(BaseActivity.get.getString(R.string.url_login)).build();
            mHttp.post(request, (call, response) -> {
                try {
                    String token = mJson.getCsrf(response);
                    FormBody body = new FormBody.Builder()
                            .add("username", user)
                            .add("password", pwd)
                            .add("remember", "on").build();
                    Request login = new Request.Builder().url(BaseActivity.get.getString(R.string.url_login))
                            .post(body)
                            .addHeader("x-csrf-token", token)
                            .build();
                    BaseActivity.get.setProcess(25, "正在登录...");
                    mHttp.post(login, (call1, response1) -> {
                        String msg = response1.body().string();
                        if (msg.startsWith("[[error")) {
                            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                                Miao.fg_miao.prepareLogin();
                                BaseActivity.get.processError(new Exception(msg.substring(2, msg.length() - 2)));
                            });
                            return;
                        }
                        BaseActivity.get.setProcess(50, "正在获取用户信息...");
                        Request userR = new Request.Builder().url(BaseActivity.get.getString(R.string.url_user, user)).build();
                        mHttp.post(userR, (call2, response2) -> {
                            User l = mJson.buildFromAPI(response2, User.class);
                            l.setPassword(pwd);
                            loginResult(l);
                        });
                    });
                } catch (JSONException e) {
                    throw new IOException(BaseActivity.get.getString(R.string.err_token));
                }
            });
        }
    }
    public void logout() {
        HttpUtil.utils().clearCookies();
        BaseActivity.get.runOnUiThreadIgnoreError(ChatButton::hide);
        MiaoFragment miao = Miao.fg_miao;
        if (miao != null) BaseActivity.get.runOnUiThreadIgnoreError(miao::prepareLogin);
        new Thread(API::removeLogin);
        loginUser = null;
    }
    public void register(String user, String pwd, String email) {
        if (checkUser(user, pwd, email)) {
            BaseActivity.get.setProcess(0, "获取服务器信息...");
            Request request = new Request.Builder().url(BaseActivity.get.getString(R.string.url_login)).build();
            mHttp.post(request, (call, response) -> {
                try {
                    String token = mJson.getCsrf(response);
                    FormBody body = new FormBody.Builder()
                            .add("username", user)
                            .add("password", pwd)
                            .add("email", email)
                            .add("remember", "on").build();
                    Request reg = new Request.Builder().url(BaseActivity.get.getString(R.string.url_register))
                            .post(body).addHeader("x-csrf-token", token)
                            .build();
                    BaseActivity.get.setProcess(25, "正在注册...");
                    mHttp.post(reg, (call1, response1) -> {
                        String msg = response1.body().string();
                        if (msg.startsWith("[[error")) {
                            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                                Miao.fg_miao.prepareLogin();
                                BaseActivity.get.processError(new Exception(msg.substring(2, msg.length() - 2)));
                            });
                            return;
                        }
                        BaseActivity.get.setProcess(50, "正在获取用户信息...");
                        Request userR = new Request.Builder().url(BaseActivity.get.getString(R.string.url_user, user)).build();
                        mHttp.post(userR, (call2, response2) -> {
                            User l = mJson.buildFromAPI(response2, User.class);
                            l.setPassword(pwd);
                            loginResult(l);
                        });
                    });
                } catch (JSONException e) {
                    throw new IOException(BaseActivity.get.getString(R.string.err_token));
                }
            });
        }
    }
    private void loginResult(User user) {
        BaseActivity activity = BaseActivity.get;
        if (user.getUid() == 0) {
            activity.processError(new Exception("登录失败, 请重新登录再试一次"));
            return;
        }
        activity.setProcess(75, "正在更新用户令牌");
        FormBody body = new FormBody.Builder().add("password", user.getPassword()).build();
        useAPI(APIType.USERS, user.getUid() + "/tokens", Method.POST, false, body, (call, response) -> {
            try {
                token = mJson.getToken(response);
                LogUtil.i(token);
            } catch (JSONException e) {
                activity.processError(e);
            }
        });
        loginUser = user;
        activity.setProcess(100, "欢迎回来, " + user.getUsername());
        if (activity instanceof Miao) {
            BaseActivity.get.runOnUiThreadIgnoreError(() -> Miao.fg_miao.loginSucceed());
        }
    }
    private boolean checkUser(String username, String password, String email) {
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            BaseActivity.get.handleError(R.string.err_email);
            Miao.fg_miao.prepareLogin();
            return false;
        }
        return checkUser(username, password);
    }
    private boolean checkUser(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            BaseActivity.get.handleError(R.string.err_username);
            Miao.fg_miao.prepareLogin();
            return false;
        }
        if (TextUtils.isEmpty(password) || username.equals(password) || password.length() < 6) {
            BaseActivity.get.handleError(R.string.err_password);
            Miao.fg_miao.prepareLogin();
            return false;
        }
        return true;
    }
    private static void removeLogin() {
        try {
            String url = BaseActivity.get.getString(R.string.url_api, APIType.USERS.api(), loginUser.getUid() + "/tokens/" + token);
            LogUtil.i(url);
            Request.Builder request = new Request.Builder()
                    .url(url)
                    .delete()
                    .addHeader("Authorization", "Bearer " + token);
            Response execute = new OkHttpClient().newCall(request.build()).execute();
            LogUtil.i(execute);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            token = null;
        }
    }
}
