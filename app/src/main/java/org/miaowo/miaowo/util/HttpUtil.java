package org.miaowo.miaowo.util;

import android.content.ContentValues;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.miaowo.miaowo.bean.data.EventMsg;
import org.miaowo.miaowo.bean.data.User;

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

import static org.miaowo.miaowo.bean.data.EventMsg.DATA_TYPE;

/**
 * 对 OKHttp 的二次封装
 * 用于网络信息的获取和处理
 * Created by luqin on 17-3-5.
 */

public class HttpUtil {
    private HttpUtil() {
        cookieStore = new HashMap<>();
        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookie = cookieStore.get(url.host());
                        return cookie == null ? new ArrayList<>() : cookie;
                    }
                })
                .retryOnConnectionFailure(true)
                .build();
        bean = BeanUtil.utils();
    }
    public static HttpUtil utils() {
        return new HttpUtil();
    }

    private OkHttpClient client;
    private HashMap<String, List<Cookie>> cookieStore;
    private BeanUtil bean;

    public void login(User u) {
        csrf(DATA_TYPE.LOGIN, u);
    }
    public void register(User u) {
        csrf(DATA_TYPE.REGISTER, u);
    }
    private void relLogin(ContentValues loginMsg) {
        String user = loginMsg.getAsString("user");
        String pwd = loginMsg.getAsString("password");
        String csrf = loginMsg.getAsString("csrf");

        FormBody msg = new FormBody.Builder()
                .add("username", user)
                .add("password", pwd)
                .add("remember", "on").build();
        Request login = new Request.Builder().url("https://miaowo.org/login")
                .post(msg).addHeader("x-csrf-token", csrf)
                .build();
        client.newCall(login).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventUtil.post(new EventMsg<>(DATA_TYPE.EXCEPTION, call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventUtil.post(new EventMsg<>(DATA_TYPE.LOGIN, call, response, bean.buildUser(response)));
            }
        });
    }
    private void relRegister(ContentValues loginMsg) {
        String user = loginMsg.getAsString("user");
        String pwd = loginMsg.getAsString("password");
        String email = loginMsg.getAsString("email");
        String csrf = loginMsg.getAsString("csrf");

        FormBody msg = new FormBody.Builder()
                .add("username", user)
                .add("password", pwd)
                .add("email", email).build();
        Request login = new Request.Builder().url("https://miaowo.org/register")
                .post(msg).addHeader("x-csrf-token", csrf)
                .build();
        client.newCall(login).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventUtil.post(new EventMsg<>(DATA_TYPE.EXCEPTION, call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventUtil.post(new EventMsg<>(DATA_TYPE.REGISTER, call, response, bean.buildUser(response)));
            }
        });
    }
    private void csrf(DATA_TYPE type, User user) {
        Request csrf = new Request.Builder().url("https://miaowo.org/login").build();
        client.newCall(csrf).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventUtil.post(new EventMsg<>(DATA_TYPE.EXCEPTION, call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String csrf = "";
                ArrayList<String> jsons = bean.getJsons(response);
                for (String json : jsons) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.has("csrf_token")) {
                            csrf = jsonObject.getString("csrf_token");
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (TextUtils.isEmpty(csrf)) {
                    ContentValues cv = new ContentValues();
                    switch (type) {
                        case LOGIN:
                            cv.put("user", user.username);
                            cv.put("password", user.password);
                            cv.put("csrf", csrf);
                            relLogin(cv);
                            break;
                        case REGISTER:
                            cv.put("user", user.username);
                            cv.put("password", user.password);
                            cv.put("email", user.email);
                            cv.put("csrf", csrf);
                            relRegister(cv);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    public void post(String url, EventMsg.DATA_TYPE type) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventUtil.post(new EventMsg<>(DATA_TYPE.EXCEPTION, call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                switch (type) {
                    case ANSWER:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildAnswer(response)));
                        break;
                    case CHAT:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildQuestion(response)));
                        break;
                    case QUESTION:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildChat(response)));
                        break;
                    case USER:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildUser(response)));
                        break;
                    case VERSION:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildVersion(response)));
                        break;
                    case CATEGORY:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildCategory(response)));
                        break;
                    case SEARCH_QUESTION:
                    case SEARCH_TOPIC:
                    case SEARCH_USER:
                        EventUtil.post(new EventMsg<>(type, call, response, bean.buildSearch(response)));
                        break;
                    case TEST:
                        EventUtil.post(new EventMsg<>(type, call, response, request));
                    default:
                }
            }
        });
    }
}
