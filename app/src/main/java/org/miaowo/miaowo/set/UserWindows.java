package org.miaowo.miaowo.set;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.UsersImpl;
import org.miaowo.miaowo.impls.interfaces.NotSingle.Handled;
import org.miaowo.miaowo.impls.interfaces.Users;
import org.miaowo.miaowo.utils.PopupUtil;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 与用户有关的弹窗
 * Created by luqin on 17-1-1.
 */

public class UserWindows {
    private Activity context;
    private Users mUsers;
    private ChatWindows mChatWindows;

    public UserWindows(Activity context) {
        this.context = context;
        mUsers = new UsersImpl();
        mChatWindows = new ChatWindows(context);
    }

    public PopupWindow showUserWindow(final User u) {
        return PopupUtil.showPopupWindowInCenter(context, C.PW_USER, R.layout.window_user, new PopupUtil.PopupWindowInit() {
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
                                    Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    ((Handled) context).handleError(e);
                                }
                            }
                        }.execute();
                    }
                });
                v.findViewById(R.id.btn_chat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChatWindows.showChatDialog(u);
                    }
                });
            }
        });
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
