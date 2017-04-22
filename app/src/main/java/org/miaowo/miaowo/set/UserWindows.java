package org.miaowo.miaowo.set;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.custom.FloatView;

/**
 * 与用户有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class UserWindows {
    private Users mUsers;

    private UserWindows() {
        mUsers = new UsersImpl();
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

        HttpUtil.utils().post(String.format(BaseActivity.get.getString(R.string.url_user), username),
                (call, response) -> {
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
                        v.findViewById(R.id.btn_focus).setOnClickListener(v1 -> mUsers.focusUser(user));
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
                tv.setText(count + "");
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * 3 / 2);
            }
        } else {
            if (count > 99) {
                tv.setText("99+");
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() / 3 * 2);
            } else {
                tv.setText(count + "");
            }
        }
    }
}
