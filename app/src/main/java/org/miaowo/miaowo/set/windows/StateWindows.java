package org.miaowo.miaowo.set.windows;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.ui.FloatView;
import org.miaowo.miaowo.Miao;

/**
 * 与用户登录状态有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class StateWindows {
    private boolean isLogin = true;
    private State mState = new StateImpl();
    private D d = D.getInstance();

    public FloatView showLogin() {
        isLogin = true;

        final FloatView view = new FloatView(R.layout.window_login);
        View v = view.getView();

        final EditText user = (EditText) v.findViewById(R.id.et_user);
        final EditText pwd = (EditText) v.findViewById(R.id.et_password);
        final EditText summary = (EditText) v.findViewById(R.id.et_summary);
        final View label = v.findViewById(R.id.tv_label1);
        final Button login = (Button) v.findViewById(R.id.btn_login);
        login.setOnClickListener(v1 -> new AsyncTask<String, Void, Exception>() {
            @Override
            protected Exception doInBackground(String... params) {
                if (isLogin) {
                    try {
                        mState.login(new User(params[0], params[1]));
                    } catch (Exception e) {
                        return e;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (isLogin) {
                    if (e == null) {
                        ((Miao) d.activeActivity).setUserMsg();
                        view.dismiss();
                    } else {
                        D.getInstance().activeActivity.handleError(e);
                    }
                } else {
                    ((Button) v1).setText("登录");
                    label.setVisibility(View.GONE);
                    summary.setVisibility(View.GONE);
                    isLogin = true;
                }
            }
        }.execute(user.getText().toString(), pwd.getText().toString()));
        v.findViewById(R.id.btn_regist).setOnClickListener(v12 -> new AsyncTask<String, Void, Exception>() {
            @Override
            protected Exception doInBackground(String... params) {
                if (!isLogin) {
                    try {
                        mState.regist(new User(params[0], params[1], params[2]));
                    } catch (final Exception e) {
                        return e;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (isLogin) {
                    login.setText("返回");
                    label.setVisibility(View.VISIBLE);
                    summary.setVisibility(View.VISIBLE);
                    isLogin = false;
                } else {
                    if (e == null) {
                        ((Miao) d.activeActivity).setUserMsg();
                        view.dismiss();
                    } else {
                        d.activeActivity.handleError(e);
                    }
                }
            }
        }.execute(user.getText().toString(), summary.getText().toString(), pwd.getText().toString()));

        return view.defaultCloseButton().show();
    }
}
