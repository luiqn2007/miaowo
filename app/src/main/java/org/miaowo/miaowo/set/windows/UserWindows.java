package org.miaowo.miaowo.set.windows;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.view.BaseActivity;
import org.miaowo.miaowo.ui.FloatView;
import org.miaowo.miaowo.util.ImageUtil;

/**
 * 与用户有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class UserWindows {
    private Users mUsers;
    private State mState;
    private ChatWindows mChatWindows;
    private BaseActivity context;

    public UserWindows() {
        mUsers = new UsersImpl();
        mState = new StateImpl();
        mChatWindows = new ChatWindows();
        context = D.getInstance().activeActivity;
    }

    public View showUserWindow(final User u) {
        FloatView view = new FloatView(R.layout.window_user);
        final View v = view.getView();

        ImageUtil.fillImage((ImageView) v.findViewById(R.id.iv_user), u);
        ((TextView) v.findViewById(R.id.tv_user)).setText(u.getName());
        ((TextView) v.findViewById(R.id.tv_summary)).setText(u.getSummary());
        ((TextView) v.findViewById(R.id.tv_regist_time)).setText("大约 11 小时之前");
        fillCount(v, R.id.tv_authority, u.getAuthority());
        fillCount(v, R.id.tv_ask, u.getQuestion());
        fillCount(v, R.id.tv_scan, u.getScan());
        fillCount(v, R.id.tv_like, u.getFocusMe().length);
        fillCount(v, R.id.tv_focus, u.getFocus().length);
        v.findViewById(R.id.btn_chat).setOnClickListener(v1 -> {
            if (mState.getLocalUser().getId() >= 0) {
                mChatWindows.showChatDialog(u);
            }
        });
        v.findViewById(R.id.btn_focus).setOnClickListener(v2 -> new AsyncTask<Void, Void, Exception>() {

            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    mUsers.focusUser(u);
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e == null) {
                    fillCount(v, R.id.tv_like, u.getFocusMe().length);
                    Snackbar.make(context.getWindow().getDecorView(), "操作成功", Snackbar.LENGTH_SHORT).show();
                } else {
                    context.handleError(e);
                }
            }
        }.execute());

        return view.defaultCloseButton().show();
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
