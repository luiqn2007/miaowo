package org.miaowo.miaowo.set;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.StateImpl;
import org.miaowo.miaowo.impls.interfaces.NotSingle.Handled;
import org.miaowo.miaowo.impls.interfaces.State;
import org.miaowo.miaowo.utils.PopupUtil;
import org.miaowo.miaowo.views.Miao;

/**
 * 与用户登录状态有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class StateWindows {
    private Activity context;
    private boolean isLogin = true;
    private State mState = new StateImpl();

    public StateWindows(Activity context) {
        this.context = context;
    }

    public PopupWindow showLogin() {
        isLogin = true;
        return PopupUtil.showPopupWindowInCenter(context, C.PW_LOGIN, R.layout.window_login, new PopupUtil.PopupWindowInit() {
            @Override
            public void init(View v, PopupWindow window) {
                final EditText user = (EditText) v.findViewById(R.id.et_user);
                final EditText pwd = (EditText) v.findViewById(R.id.et_password);
                final EditText summary = (EditText) v.findViewById(R.id.et_summary);
                final View label = v.findViewById(R.id.tv_label1);
                final Button login = (Button) v.findViewById(R.id.btn_login);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new AsyncTask<String, Void, Exception>() {
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
                                        PopupUtil.closePopupWindow();
                                        ((Miao) context).setUserMsg();
                                    } else {
                                        ((Handled) context).handleError(e);
                                    }
                                } else {
                                    ((Button) v).setText("登录");
                                    label.setVisibility(View.GONE);
                                    summary.setVisibility(View.GONE);
                                    isLogin = true;
                                }
                            }
                        }.execute(user.getText().toString(), pwd.getText().toString());
                    }
                });
                v.findViewById(R.id.btn_regist).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AsyncTask<String, Void, Exception>() {
                            @Override
                            protected Exception doInBackground(String... params) {
                                if (!isLogin) {
                                    try {
                                        mState.regist(new User(params[0], params[2], params[1]));
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
                                    if (e != null) {
                                        ((Handled) context).handleError(e);
                                        ((Miao) context).setUserMsg();
                                        PopupUtil.closePopupWindow();
                                    }
                                }
                            }
                        }.execute(user.getText().toString(), pwd.getText().toString(), summary.getText().toString());
                    }
                });
            }
        });
    }

}
