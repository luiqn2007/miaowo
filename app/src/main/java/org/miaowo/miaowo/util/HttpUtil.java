package org.miaowo.miaowo.util;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.root.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
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
        mCookieJar = new MyCookieJar();
        client = new OkHttpClient.Builder()
                .cookieJar(mCookieJar)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
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

    public HttpUtil clearCookies() {
        mCookieJar.clear();
        return this;
    }

    public Call post(String url, CallbackRun callback) {
        return post(url, callback, null);
    }
    public Call post(String url, CallbackRun callback, CallbackErr error) {
        if (url == null) {
            return null;
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (error != null) {
                    error.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(call, response);
            }
        });
        return call;
    }
    public Call newCall(Request request) {
        LogUtil.i("URL: " + request.url());
        return client.newCall(request);
    }

    private static class MyCookieJar implements CookieJar {
        final private static HashMap<String, List<Cookie>> mCookies = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            mCookies.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = mCookies.get(url.host());
            return cookies != null ? cookies : new ArrayList<>();
        }

        void clear() {
            mCookies.clear();
        }
    }
    public static class MyCallback implements Callback {
        private CallbackErr error;
        private CallbackRun action;

        public MyCallback(CallbackRun action) {
            this.action = action;
            this.error = (call, e) -> BaseActivity.get.toast(e.getMessage(), TastyToast.ERROR);
        }

        @Override
        public void onFailure(Call call, IOException e) {
            error.onFailure(call, e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            action.onResponse(call, response);
        }

    }
    public interface CallbackRun {
        void onResponse(Call call, Response response) throws IOException;
    }
    public interface CallbackErr {
        void onFailure(Call call, IOException e);
    }
}
