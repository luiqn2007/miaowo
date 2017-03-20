package org.miaowo.miaowo.set.windows;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.UserPage;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.FloatView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 与用户有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class UserWindows {
    private Users mUsers;
    private State mState;
    private ChatWindows mChatWindows;
    private BaseActivity mContext;

    private UserWindows(BaseActivity mContext) {
        this.mContext = mContext;
        mUsers = new UsersImpl(mContext);
        mState = new StateImpl(mContext);
        mChatWindows = ChatWindows.windows(mContext);
    }
    public static UserWindows windows(BaseActivity context) { return new UserWindows(context); }

    public FloatView showUserWindow(String username) {
        FloatView view = new FloatView(mContext, "用户信息: " + username, R.layout.window_user);
        final View v = view.getView();

        ImageUtil.utils(mContext).fill((ImageView) v.findViewById(R.id.iv_user), "default", null);
        ((TextView) v.findViewById(R.id.tv_user)).setText(username);
        ((TextView) v.findViewById(R.id.tv_summary)).setText("加载中");
        ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载中");
        fillCount(v, R.id.tv_ask, 0);
        fillCount(v, R.id.tv_scan,0);
        fillCount(v, R.id.tv_like, 0);
        fillCount(v, R.id.tv_focus, 0);

        HttpUtil.utils().post(mContext.getString(R.string.url_home) + mContext.getString(R.string.url_user) + username, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载失败");
                ((TextView) v.findViewById(R.id.tv_summary)).setText("加载失败");
                ((TextView) v.findViewById(R.id.tv_regist_time)).setText("加载失败");
                v.findViewById(R.id.btn_chat).setOnClickListener(null);
                v.findViewById(R.id.btn_focus).setOnClickListener(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                UserPage user = BeanUtil.utils().buildFromLastJson(response, UserPage.class);
                ImageUtil.utils(mContext).setUser((ImageView) v.findViewById(R.id.iv_user), user, false);
                ((TextView) v.findViewById(R.id.tv_user)).setText(username);
                ((TextView) v.findViewById(R.id.tv_summary)).setText(user.getEmail());
                ((TextView) v.findViewById(R.id.tv_regist_time)).setText(FormatUtil.format().time(user.getJoindate()));
                fillCount(v, R.id.tv_ask, user.getPostcount());
                fillCount(v, R.id.tv_scan, user.getProfileviews());
                fillCount(v, R.id.tv_like, user.getFollowerCount());
                fillCount(v, R.id.tv_focus, user.getFollowingCount());
                v.findViewById(R.id.btn_chat).setOnClickListener(v1 -> {
                    if (mState.isLogin()) {
                        mChatWindows.showChatDialog(user);
                    }
                });
                v.findViewById(R.id.btn_focus).setOnClickListener(v1 -> mUsers.focusUser(user));
            }
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
