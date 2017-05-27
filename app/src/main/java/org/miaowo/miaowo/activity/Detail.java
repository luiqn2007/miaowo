package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.ServerMessage;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseRecyclerAdapter;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.Request;

public class Detail extends BaseActivity {
    final public static String TITLE = "title";

    @BindView(R.id.title) RecyclerView title;
    private API mApi;
    private HttpUtil mHttp;
    private JsonUtil mJson;
    private BaseRecyclerAdapter<Post> mAdapter;
    private Title mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    @Override
    public void initActivity() {
        mApi = new API();
        mHttp = HttpUtil.utils();
        mJson = JsonUtil.utils();
        mAdapter = new BaseRecyclerAdapter<Post>(R.layout.list_question) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                FormatUtil format = FormatUtil.format();
                if (position != 0)
                    ((CardView) holder.getView(R.id.rl_item)).setCardBackgroundColor(ResourcesCompat.getColor(BaseActivity.get.getResources(), R.color.md_lime_A400, null));
                Post item = getItem(position);
                User u = item.getUser();
                ImageUtil.utils().setUser((ImageView) holder.getView(R.id.iv_user), u, true);
                holder.setText(R.id.tv_user, u.getUsername());
                holder.setText(R.id.tv_time, format.time(item.getTimestamp()));
                format.parseHtml(item.getContent(), spanned -> holder.setText(R.id.tv_page, spanned));
                holder.setClickListener(R.id.tv_reply, v -> {
                    Intent intent = new Intent(BaseActivity.get, Add.class);
                    intent.putExtra(Add.TAG, -1);
                    intent.putExtra(Add.TITLE, item);
                    BaseActivity.get.startActivityForResult(intent, 0);
                });
            }
        };
        title.setLayoutManager(new LinearLayoutManager(this));
        title.setAdapter(mAdapter);
        mTitle = getIntent().getParcelableExtra(TITLE);
        load();
    }

    private void load() {
        Request request = new Request.Builder().url(getString(R.string.url_topic, mTitle.getSlug())).build();
        mHttp.post(request,
                (call, response) -> mAdapter.update(mJson.buildFromAPI(response, Question.class).getPosts()),
                (call, e) -> handleError(e));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String content = data.getStringExtra(Add.TITLE) + '\n' + data.getStringExtra(Add.CONTENT);
            int tid = data.getIntExtra(Add.TAG, -1);
            if (tid != -1) {
                FormBody body = new FormBody.Builder()
                        .add("content", content)
                        .build();
                mApi.useAPI(API.APIType.TOPICS, String.valueOf(tid), API.Method.POST, true, body, (call, response) -> load());
            }
        }
    }

    public static void showTitle(Title title) {
        Intent intent = new Intent(BaseActivity.get, Detail.class);
        intent.putExtra(TITLE, title);
        BaseActivity.get.startActivity(intent);
    }

    public static void showUser(String username) {
        PopupWindow window = new PopupWindow();
        FormatUtil format = FormatUtil.format();
        JsonUtil json = JsonUtil.utils();
        API api = new API();

        View v = View.inflate(BaseActivity.get, R.layout.window_user, null);
        ImageUtil.utils().fill((ImageView) v.findViewById(R.id.iv_user), "default", null);
        ((TextView) v.findViewById(R.id.tv_user)).setText(username);
        ((TextView) v.findViewById(R.id.tv_email)).setText("加载中");
        ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载中");
        format.fillCount(v, R.id.tv_ask, 0);
        format.fillCount(v, R.id.tv_scan,0);
        format.fillCount(v, R.id.tv_like, 0);
        format.fillCount(v, R.id.tv_focus, 0);

        Request request = new Request.Builder().url(BaseActivity.get.getString(R.string.url_user, username)).build();
        HttpUtil.utils().post(request, (call, response) -> {
                    User user = JsonUtil.utils().buildFromAPI(response, User.class);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        ImageUtil.utils().setUser((ImageView) v.findViewById(R.id.iv_user), user, false);
                        ((TextView) v.findViewById(R.id.tv_user)).setText(username);
                        ((TextView) v.findViewById(R.id.tv_email)).setText(user.getEmail());
                        ((TextView) v.findViewById(R.id.tv_regist_time)).setText(FormatUtil.format().time(user.getJoindate()));
                        format.fillCount(v, R.id.tv_ask, user.getPostcount());
                        format.fillCount(v, R.id.tv_scan, user.getProfileviews());
                        format.fillCount(v, R.id.tv_like, user.getFollowerCount());
                        format.fillCount(v, R.id.tv_focus, user.getFollowingCount());
                        Button focus = (Button) v.findViewById(R.id.btn_focus);
                        focus.setText(user.isIsFollowing() ? "取消关注" : "关注");
                        focus.setOnClickListener(v1 -> {
                            FormBody body = new FormBody.Builder().build();
                            api.useAPI(API.APIType.USERS, user.getUid() + "/follow", API.Method.POST, true, body, (call1, response1) -> {
                                ServerMessage message = json.buildFromAPI(response1, ServerMessage.class);
                                BaseActivity activity = BaseActivity.get;
                                if (message == null) {
                                    activity.handleError(R.string.err_no_err);
                                }
                                else if ("ok".equals(message.getCode())) {
                                    activity.toast("关注成功", TastyToast.SUCCESS);
                                    user.setFollowerCount(user.getFollowerCount() + 1);
                                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                                        format.fillCount(v, R.id.tv_like, user.getFollowerCount());
                                        focus.setText("取消关注");
                                    });
                                }
                                else if ("already-following".equals(message.getMessage())) {
                                    api.useAPI(API.APIType.USERS, user.getUid() + "/follow", API.Method.DELETE, true, null, (call2, response2) -> {
                                        ServerMessage nMessage = json.buildFromAPI(response2, ServerMessage.class);
                                        if ("ok".equals(nMessage.getCode())) {
                                            activity.toast("已取消关注", TastyToast.SUCCESS);
                                            user.setFollowerCount(user.getFollowerCount() - 1);
                                            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                                                format.fillCount(v, R.id.tv_like, user.getFollowerCount());
                                                focus.setText("关注");
                                            });
                                        }
                                    });
                                }
                                else if ("you-cant-follow-yourself".equals(message.getMessage())) activity.handleError(R.string.err_focus_self);
                            });
                        });
                    });
                }
                , (call, e) -> {
                    ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载失败");
                    ((TextView) v.findViewById(R.id.tv_email)).setText("加载失败");
                    ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载失败");
                    v.findViewById(R.id.btn_focus).setOnClickListener(null);
                });

        window.setContentView(v);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.showAsDropDown(BaseActivity.get.getWindow().getDecorView(), 0, 0, Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        } else {
            int w = (BaseActivity.get.getWindow().getDecorView().getWidth() - v.getMeasuredWidth()) / 2;
            window.showAsDropDown(BaseActivity.get.getWindow().getDecorView(), 0, w);
        }
    }
}
