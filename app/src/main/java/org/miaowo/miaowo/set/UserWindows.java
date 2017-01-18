package org.miaowo.miaowo.set;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.util.PopupUtil;
import org.miaowo.miaowo.view.BaseActivity;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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

    public PopupWindow showUserWindow(final User u) {
        return PopupUtil.showPopupWindowInCenter(R.layout.window_user, new PopupUtil.PopupWindowInit() {
            @Override
            public void init(final View v, final PopupWindow window) {
                Picasso.with(context)
                        .load(u.getHeadImg())
                        .fit().transform(new CropCircleTransformation())
                        .into((ImageView) v.findViewById(R.id.iv_user));
                ((TextView) v.findViewById(R.id.tv_user)).setText(u.getName());
                ((TextView) v.findViewById(R.id.tv_summary)).setText(u.getSummary());
                ((TextView) v.findViewById(R.id.tv_regist_time)).setText("大约 11 小时之前");
                fillCount(v, R.id.tv_authority, u.getAuthority());
                fillCount(v, R.id.tv_ask, u.getQuestion());
                fillCount(v, R.id.tv_scan, u.getScan());
                fillCount(v, R.id.tv_like, u.getFavorite());
                fillCount(v, R.id.tv_focus, u.getFocus());
                v.findViewById(R.id.btn_focus).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v2) {
                        new AsyncTask<Void, Void, Exception>() {

                            @Override
                            protected Exception doInBackground(Void... params) {
                                try {
                                    mUsers.likeUser(u);
                                } catch (Exception e) {
                                    return e;
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Exception e) {
                                if (e == null) {
                                    if (u.isFavorite()) u.setFavorite(u.getFavorite() + 1);
                                    else u.setFavorite(u.getFavorite() - 1);
                                    fillCount(v, R.id.tv_like, u.getFavorite());
                                    Snackbar.make(context.getWindow().getDecorView(), "操作成功", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    context.handleError(e);
                                }
                            }
                        }.execute();
                    }
                });
                v.findViewById(R.id.btn_chat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mState.getLocalUser().getId() < 0)
                            context.handleError(Exceptions.E_NON_LOGIN);
                        else mChatWindows.showChatDialog(u);
                    }
                });
            }
        }, null);
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
