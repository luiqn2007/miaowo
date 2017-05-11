package org.miaowo.miaowo.set;

import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.ServerMessage;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.FloatView;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 与用户有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class UserWindows {

    private API mApi;
    private JsonUtil mJson;

    private UserWindows() {
        mApi = new API();
        mJson = JsonUtil.utils();
    }
    public static UserWindows windows() { return new UserWindows(); }

    public FloatView showUserWindow(String username) {
        FloatView view = new FloatView("用户信息: " + username, R.layout.window_user);
        final View v = view.getView();

        ImageUtil.utils().fill((ImageView) v.findViewById(R.id.iv_user), "default", null);
        ((TextView) v.findViewById(R.id.tv_user)).setText(username);
        ((TextView) v.findViewById(R.id.tv_email)).setText("加载中");
        ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载中");
        fillCount(v, R.id.tv_ask, 0);
        fillCount(v, R.id.tv_scan,0);
        fillCount(v, R.id.tv_like, 0);
        fillCount(v, R.id.tv_focus, 0);

        Request request = new Request.Builder().url(String.format(BaseActivity.get.getString(R.string.url_user), username)).build();
        HttpUtil.utils().post(request, (call, response) -> {
                    User user = JsonUtil.utils().buildFromAPI(response, User.class);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        ImageUtil.utils().setUser((ImageView) v.findViewById(R.id.iv_user), user, false);
                        ((TextView) v.findViewById(R.id.tv_user)).setText(username);
                        ((TextView) v.findViewById(R.id.tv_email)).setText(user.getEmail());
                        ((TextView) v.findViewById(R.id.tv_regist_time)).setText(FormatUtil.format().time(user.getJoindate()));
                        fillCount(v, R.id.tv_ask, user.getPostcount());
                        fillCount(v, R.id.tv_scan, user.getProfileviews());
                        fillCount(v, R.id.tv_like, user.getFollowerCount());
                        fillCount(v, R.id.tv_focus, user.getFollowingCount());
                        Button focus = (Button) v.findViewById(R.id.btn_focus);
                        focus.setText(user.isIsFollowing() ? "取消关注" : "关注");
                        focus.setOnClickListener(v1 -> {
                            FormBody body = new FormBody.Builder().build();
                            mApi.useAPI(API.APIType.USERS, user.getUid() + "/follow", API.Method.POST, true, body, (call1, response1) -> {
                                ServerMessage message = mJson.buildFromAPI(response1, ServerMessage.class);
                                BaseActivity activity = BaseActivity.get;
                                if (message == null) {
                                    activity.toast("未知错误", TastyToast.ERROR);
                                }
                                else if ("ok".equals(message.getCode())) {
                                    activity.toast("关注成功", TastyToast.SUCCESS);
                                    user.setFollowerCount(user.getFollowerCount() + 1);
                                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                                        fillCount(v, R.id.tv_like, user.getFollowerCount());
                                        focus.setText("取消关注");
                                    });
                                }
                                else if ("already-following".equals(message.getMessage())) {
                                    mApi.useAPI(API.APIType.USERS, user.getUid() + "/follow", API.Method.DELETE, true, null, (call2, response2) -> {
                                        ServerMessage nMessage = mJson.buildFromAPI(response2, ServerMessage.class);
                                        if ("ok".equals(nMessage.getCode())) {
                                            activity.toast("已取消关注", TastyToast.SUCCESS);
                                            user.setFollowerCount(user.getFollowerCount() - 1);
                                            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                                                fillCount(v, R.id.tv_like, user.getFollowerCount());
                                                focus.setText("关注");
                                            });
                                        }
                                    });
                                }
                                else if ("you-cant-follow-yourself".equals(message.getMessage())) activity.toast("无法关注自身", TastyToast.ERROR);
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
        return view.defaultBar().show();
    }

    // 填充数字
    private void fillCount(View v, int viewId, int count) {
        TextView tv = (TextView) v.findViewById(viewId);
        int n = tv.getText().length();
        if (n == 3) {
            if (count <= 99) {
                tv.setText(String.valueOf(count));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * 3 / 2);
            }
        } else {
            if (count > 99) {
                tv.setText(R.string.more99);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() / 3 * 2);
            } else {
                tv.setText(String.valueOf(count));
            }
        }
    }
}
