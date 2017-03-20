package org.miaowo.miaowo.set.windows;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.SpUtil;
import org.miaowo.miaowo.view.FloatView;

/**
 * 与用户登录状态有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class StateWindows {
    private final String sp_save = "save_password";
    private final String sp_user = "username";
    private final String sp_pwd = "password";

    private boolean isLogin = true;
    private State mState;
    private BaseActivity mContext;
    private SpUtil mDefaultSp;

    private StateWindows(BaseActivity context) {
        mContext = context;
        mState = new StateImpl(context);
        mDefaultSp = SpUtil.defaultSp(context);
    }
    public static StateWindows windows(BaseActivity context) { return new StateWindows(context); }

    public FloatView showLogin() {
        final FloatView view = new FloatView(mContext, "登录", R.layout.window_login);
        View v = view.getView();
        isLogin = true;
        boolean save = mDefaultSp.getBoolean(sp_save, false);

        EditText user = (EditText) v.findViewById(R.id.et_user);
        EditText pwd = (EditText) v.findViewById(R.id.et_password);
        EditText email = (EditText) v.findViewById(R.id.et_email);
        CheckBox cb_save = (CheckBox) v.findViewById(R.id.cb_save);
        cb_save.setChecked(save);
        if (save) {
            user.setText(mDefaultSp.getString(sp_user, ""));
            pwd.setText(mDefaultSp.getString(sp_pwd, ""));
        }
        View label = v.findViewById(R.id.tv_label1);
        Button login = (Button) v.findViewById(R.id.btn_login);
        login.setOnClickListener(v1 -> {
            if (isLogin) {
                String username = user.getText().toString();
                String password = pwd.getText().toString();
                if (save) {
                    mDefaultSp.putString(sp_user, username);
                    mDefaultSp.putString(sp_pwd, password);
                }
                mDefaultSp.putBoolean(sp_save, cb_save.isChecked());
                try {
                    mState.login(username, password);
                    view.dismiss(false);
                } catch (Exception e) {
                    mContext.handleError(e);
                }
            } else {
                ((Button) v1).setText("登录");
                label.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                cb_save.setVisibility(View.GONE);
                isLogin = true;
            }

        });
        v.findViewById(R.id.btn_regist).setOnClickListener(v1 -> {
            if (!isLogin) {
                try {
                    String username = user.getText().toString();
                    String password = pwd.getText().toString();
                    String emaili = email.getText().toString();
                    mState.register(username, password, emaili);
                    view.dismiss(false);
                } catch (Exception e) {
                    mContext.handleError(e);
                }
            } else {
                login.setText("返回");
                label.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                cb_save.setVisibility(View.VISIBLE);
                isLogin = false;
            }
        });
        return view.defaultBar().show();
    }
}
