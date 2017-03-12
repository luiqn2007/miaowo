package org.miaowo.miaowo.set.windows;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.view.FloatView;

/**
 * 与用户登录状态有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class StateWindows {
    private boolean isLogin = true;
    private State mState = new StateImpl();

    private StateWindows() {}
    public static StateWindows windows() { return new StateWindows(); }

    public FloatView showLogin() {
        isLogin = true;

        final FloatView view = new FloatView(R.layout.window_login);
        View v = view.getView();

        EditText user = (EditText) v.findViewById(R.id.et_user);
        EditText pwd = (EditText) v.findViewById(R.id.et_password);
        EditText email = (EditText) v.findViewById(R.id.et_email);
        View label = v.findViewById(R.id.tv_label1);
        Button login = (Button) v.findViewById(R.id.btn_login);
        login.setOnClickListener(v1 -> {
            if (isLogin) {
                User u = new User();
                u.username = user.getText().toString();
                u.password = pwd.getText().toString();
                try {
                    mState.login(u);
                    view.dismiss();
                } catch (Exception e) {
                    D.getInstance().activeActivity.handleError(e);
                }
            } else {
                ((Button) v1).setText("登录");
                label.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                isLogin = true;
            }

        });
        v.findViewById(R.id.btn_regist).setOnClickListener(v1 -> {
            if (!isLogin) {
                try {
                    User u = new User();
                    u.username = user.getText().toString();
                    u.password = pwd.getText().toString();
                    u.email = email.getText().toString();
                    mState.register(u);
                    view.dismiss();
                } catch (Exception e) {
                    D.getInstance().activeActivity.handleError(e);
                }
            } else {
                login.setText("返回");
                label.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                isLogin = false;
            }
        });
        return view.defaultBar().show();
    }
}
