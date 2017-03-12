package org.miaowo.miaowo.set.windows;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.FloatView;

/**
 * 与用户有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class UserWindows {
    private Users mUsers;
    private State mState;
    private ChatWindows mChatWindows;

    private UserWindows() {
        mUsers = new UsersImpl();
        mState = new StateImpl();
        mChatWindows = ChatWindows.windows();
    }
    public static UserWindows windows() { return new UserWindows(); }

    public FloatView showUserWindow(final User u) {
        FloatView view = new FloatView(R.layout.window_user);
        final View v = view.getView();

        ImageUtil.utils().setUser((ImageView) v.findViewById(R.id.iv_user), u, false);
        ((TextView) v.findViewById(R.id.tv_user)).setText(u.username);
        ((TextView) v.findViewById(R.id.tv_summary)).setText(u.signature);
        ((TextView) v.findViewById(R.id.tv_regist_time)).setText(FormatUtil.format().time(u.joindate));
        fillCount(v, R.id.tv_ask, u.postcount);
        fillCount(v, R.id.tv_scan, u.profileviews);
        fillCount(v, R.id.tv_like, u.followerCount);
        fillCount(v, R.id.tv_focus, u.followingCount);
        v.findViewById(R.id.btn_chat).setOnClickListener(v1 -> {
            if (mState.isLogin()) {
                mChatWindows.showChatDialog(u);
            }
        });
        v.findViewById(R.id.btn_focus).setOnClickListener(v1 -> mUsers.focusUser(u));
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
