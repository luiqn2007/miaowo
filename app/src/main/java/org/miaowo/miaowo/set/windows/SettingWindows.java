package org.miaowo.miaowo.set.windows;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.ui.FloatView;

/**
 * 设置 对话框
 * Created by luqin on 17-2-3.
 */

public class SettingWindows {
    private Users mUsers;

    public SettingWindows() {
        mUsers = new UsersImpl();
    }

    public FloatView showUserSetting() {
        FloatView view = new FloatView(R.layout.window_setting_user);
        View v = view.getView();

        EditText et_name = (EditText) v.findViewById(R.id.et_user);
        EditText et_pwd = (EditText) v.findViewById(R.id.et_password);
        EditText et_summary = (EditText) v.findViewById(R.id.et_summary);
        Button btn_ok = (Button) v.findViewById(R.id.btn_send);
        Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        Button btn_empty = (Button) v.findViewById(R.id.btn_empty);
        Button btn_reset = (Button) v.findViewById(R.id.btn_reset);

        User u = D.getInstance().thisUser;

        btn_empty.setOnClickListener(v1 -> {
            et_name.setText("");
            et_pwd.setText("");
            et_summary.setText("");
        });
        btn_reset.setOnClickListener(v1 -> {
            et_name.setText(u.getName());
            et_pwd.setText("");
            et_summary.setText(u.getSummary());
        });
        btn_cancel.setOnClickListener(v1 -> view.dismiss());
        btn_ok.setOnClickListener(v1 -> {
            User user = new User(et_name.getText().toString(), et_summary.getText().toString(), et_pwd.getText().toString());
            new AsyncTask<User, Void, Exception>() {

                @Override
                protected Exception doInBackground(User... params) {
                    try {
                        mUsers.updateUser(params[0]);
                    } catch (Exception e) {
                        return e;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Exception e) {
                    if (e != null) {
                        D.getInstance().activeActivity.handleError(e);
                        return;
                    }
                    view.dismiss();
                }
            }.execute(user);
        });

        return view.defaultCloseButton().show();
    }
}
