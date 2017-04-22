package org.miaowo.miaowo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.NavigationLayoutManager;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

public class Test extends BaseActivity {

    private HttpUtil http;
    private JsonUtil json;
    private State mState;
    private Users mUsers;

    private String token = null;

    private String mAPI1 = "https://www.miaowo.org/api/v1/users/%1$s";
    private String mAPI2 = "https://www.miaowo.org/api/v1/topics/%1$s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initActivity() {
        http = HttpUtil.utils();
        json = JsonUtil.utils();

        mState = new StateImpl();
        mUsers = new UsersImpl();

        initView();
    }

    private void initView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new NavigationLayoutManager());
        rv.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(new TextView(Test.this)) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText("position: " + position);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
    }

    public void newUser(View view) {}

    public void updateUser(View view) {
        call(String.format(mAPI1, mState.loginUser().getUid() + ""), "updateUser", "PUT", true,
                new FormBody.Builder().add("signature", "APP测试_API修改"), null);
    }

    public void deleteUser(View view) {
    }

    public void changePwd(View view) {
    }

    public void sendChat(View view) {
    }

    public void deleteToken(View view) {
    }

    public void listToken(View view) {
        call(String.format(mAPI1, mState.loginUser().getUid() + "/tokens"), "listToken", "GET", false, null, null);
    }

    public void newToken(View view) {
        call(String.format(mAPI1, mState.loginUser().getUid() + "/tokens"), "newToken", "POST", false,
                new FormBody.Builder().add("password", "testapp"), null);
    }

    public void newTopic(View view) {
        call(String.format(mAPI2, mState.loginUser().getUid() + ""), "newTopic", "PUT", true,
                new FormBody.Builder().add("cid", "6")
                        .add("title", "测试_发送新主题")
                        .add("content", "若你看到了这条主题，那么就说明了喵窝 APP 已经可以发送主题了"), null);
    }

    public void followUser(View view) {
        mUsers.getUser("么么么喵", (call, response) -> {
            User user = json.buildFromAPI(response, User.class);
            call(String.format(mAPI1, user.getUid() + "/follow"), "followUser", "POST", true, null, null);
        }, (call, e) -> e.printStackTrace());
    }

    public void unfollowUser(View view) {
        mUsers.getUser("么么么喵", (call, response) -> {
            User user = json.buildFromAPI(response, User.class);
            call(String.format(mAPI1, user.getUid() + "/follow"), "unfollowUser", "DELETE", true, null, null);
        }, (call, e) -> e.printStackTrace());
    }

    public void editTopic(View view) {
    }

    public void deleteTopic(View view) {
    }

    public void registerTopic(View view) {
    }

    public void unregisterTopic(View view) {
    }

    public void editPost(View view) {
    }

    public void deletePost(View view) {
    }

    public void updateFile(View view) {
    }

    private void call(String url, String name, String method, boolean withToken, FormBody.Builder bodyBuilder, Headers headers) {
        http.post(String.format(getString(R.string.url_home), ""), (call, response) -> {
            String urlToken = "";
            try {
                urlToken = json.getToken(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (withToken) {
                if (token == null) {
                    newToken(null);
//                call(url, name, method, withToken, bodyBuilder, headers);
                } else {
                    LogUtil.i("Token: " + token);
                    rCall(url, name, method, urlToken,
                            (bodyBuilder == null ? new FormBody.Builder() : bodyBuilder).add("token", this.token).build(),
                            headers);
                }
            } else
                rCall(url, name, method, urlToken, bodyBuilder == null ? null : bodyBuilder.build(), headers);
        });
    }
    private void rCall(String url, String name, String method, String urlToken, FormBody body, Headers headers) {
        Request.Builder builder;
        if ("GET".equals(method) && body != null && body.size() != 0) {
            StringBuilder sb = new StringBuilder("?");
            sb.append(body.name(0)).append("=").append(body.value(0));
            for (int i = 1; i < body.size(); i++)
                sb.append("&").append(body.name(i)).append("=").append(body.value(i));
            builder = new Request.Builder().url(url + sb.toString()).get();
        } else builder = new Request.Builder().url(url).method(method.toUpperCase(), body);
        if (headers != null && headers.size() != 0)
            headers.toMultimap().forEach((s, strings) -> builder.addHeader(s, strings.get(0)));
        builder.addHeader("x-csrf-token", urlToken);
        Request request = builder.build();
        http.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.i(name + ": " + response.body().string());
            }
        });
    }
}
