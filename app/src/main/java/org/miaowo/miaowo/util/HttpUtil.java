package org.miaowo.miaowo.util;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.event.ExceptionEvent;
import org.miaowo.miaowo.bean.data.event.LoginEvent;
import org.miaowo.miaowo.bean.data.event.RegisterEvent;
import org.miaowo.miaowo.bean.data.event.UserEvent;
import org.miaowo.miaowo.root.BaseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 对 OKHttp 的二次封装
 * 用于网络信息的获取和处理
 * Created by luqin on 17-3-5.
 */

public class HttpUtil {
    private HttpUtil() {
        bean = BeanUtil.utils();
        eventBus = EventBus.getDefault();
        mCookieJar = new MyCookieJar();
        client = new OkHttpClient.Builder().cookieJar(mCookieJar).build();
    }
    private static HttpUtil util;
    public static HttpUtil utils() {
        if (util == null) {
            synchronized (HttpUtil.class) {
                if (util == null) {
                    util = new HttpUtil();
                }
            }
        }
        return util;
    }

    private MyCookieJar mCookieJar;
    private OkHttpClient client;
    private BeanUtil bean;
    private EventBus eventBus;

    public HttpUtil login(Context context, LoginEvent u) {
        openCookiesWrite();
        csrf(context, u);
        return this;
    }
    public HttpUtil register(Context context, RegisterEvent u) {
        openCookiesWrite();
        csrf(context, u);
        return this;
    }
    private void relLogin(Context context, ContentValues loginMsg) {
        openCookiesWrite();
        String user = loginMsg.getAsString("user");
        String pwd = loginMsg.getAsString("password");
        String csrf = loginMsg.getAsString("csrf");

        FormBody msg = new FormBody.Builder()
                .add("username", user)
                .add("password", pwd)
                .add("remember", "on").build();
        Request login = new Request.Builder().url(context.getString(R.string.url_login))
                .post(msg).addHeader("x-csrf-token", csrf)
                .build();
        LogUtil.i("连接Url: " + login.url());
        client.newCall(login).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                eventBus.post(new ExceptionEvent(call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Request index = new Request.Builder().url(context.getString(R.string.url_home)).build();
                LogUtil.i("连接Url: " + context.getString(R.string.url_home));
                eventBus.post(new UserEvent(call, bean.buildUser(client.newCall(index).execute())));
            }
        });
    }
    private void relRegister(Context context, ContentValues loginMsg) {
        openCookiesWrite();
        String user = loginMsg.getAsString("user");
        String pwd = loginMsg.getAsString("password");
        String email = loginMsg.getAsString("email");
        String csrf = loginMsg.getAsString("csrf");

        FormBody msg = new FormBody.Builder()
                .add("username", user)
                .add("password", pwd)
                .add("email", email).build();
        Request login = new Request.Builder().url(context.getString(R.string.url_register))
                .post(msg).addHeader("x-csrf-token", csrf)
                .build();
        LogUtil.i("连接Url: " + login.url());
        client.newCall(login).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                eventBus.post(new ExceptionEvent(call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                if (msg.startsWith("[[error")) {
                    throw new IOException(msg.substring(2, msg.length() - 2));
                }
                Request index = new Request.Builder().url(context.getString(R.string.url_home)).build();
                eventBus.post(new UserEvent(call, bean.buildUser(client.newCall(index).execute())));
            }
        });
    }
    private void csrf(Context context, BaseEvent user) {
        post(context.getString(R.string.url_login), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                eventBus.post(new ExceptionEvent(call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String csrf = "";
                ArrayList<String> jsons = bean.getJsons(response);
                for (String json : jsons) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(json);
                        if (jsonObject.has("csrf_token")) {
                            csrf = jsonObject.getString("csrf_token");
                            break;
                        }
                    } catch (JSONException e) {
                        eventBus.post(new ExceptionEvent(call, e));
                    }
                }
                if (!TextUtils.isEmpty(csrf)) {
                    ContentValues cv = new ContentValues();
                    if (user instanceof LoginEvent) {
                        cv.put("user", ((LoginEvent) user).username);
                        cv.put("password", ((LoginEvent) user).password);
                        cv.put("csrf", csrf);
                        relLogin(context, cv);
                    } else if (user instanceof RegisterEvent) {
                        cv.put("user", ((RegisterEvent) user).username);
                        cv.put("password", ((RegisterEvent) user).password);
                        cv.put("email", ((RegisterEvent) user).email);
                        cv.put("csrf", csrf);
                        relRegister(context, cv);
                    }
                }
            }
        });
    }

    public HttpUtil openCookiesWrite() {
        mCookieJar.writable(true);
        return this;
    }
    public HttpUtil clearCookies() {
        mCookieJar.clear();
        return this;
    }
    public HttpUtil post(String url, Callback callback) {
        LogUtil.i("连接Url: " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
        return this;
    }

    private static class MyCookieJar implements CookieJar {
        final private static HashMap<String, List<Cookie>> mCookies = new HashMap<>();

        private boolean writable = false;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (writable) {
                mCookies.put(url.host(), cookies);
                writable = false;
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = mCookies.get(url.host());
            return cookies != null ? cookies : new ArrayList<>();
        }

        void writable(boolean writable) {
            this.writable = writable;
        }

        void clear() {
            mCookies.clear();
        }
    }
}
