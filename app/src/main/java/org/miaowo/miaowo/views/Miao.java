package org.miaowo.miaowo.views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.T;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.beans.VersionMessage;
import org.miaowo.miaowo.fragment.AnnouncementFragment;
import org.miaowo.miaowo.fragment.DailyFragment;
import org.miaowo.miaowo.fragment.QuestionFragment;
import org.miaowo.miaowo.fragment.WaterFragment;
import org.miaowo.miaowo.impls.StateImpl;
import org.miaowo.miaowo.impls.interfaces.State;
import org.miaowo.miaowo.utils.PopupUtil;
import org.miaowo.miaowo.utils.SpUtil;
import org.miaowo.miaowo.utils.ThemeUtil;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

// Android Studio自动生成的，用了一大堆支持库的东西，详见
// http://wuxiaolong.me/2015/11/06/DesignSupportLibrary/
public class Miao extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    private static final String TAG = "Miao";

    // 视图
    ImageView iv_daily, iv_announcement, iv_question, iv_water;
    Fragment fg_daily, fg_announcement, fg_question, fg_water;
    LinearLayout ll_daily, ll_announcement, ll_ask, ll_water;
    NavigationView navigationView;
    PopupWindow mWindow;

    // 组件
    FragmentManager fm;
    State mState = new StateImpl();

    // 状态
    long backTime;
    Exception e = null;
    boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_miao);
        super.onCreate(savedInstanceState);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // 初始化控件
        initViews();

        // 显示 每日 页面
        onClick(findViewById(R.id.ll_daily));

        // 显示对话框
        Log.i(TAG, "onCreate: here");
        showAppDialog();

        // 记录APP打开时间
        backTime = System.currentTimeMillis();
    }

    private void login() {
        isLogin = true;
        mWindow = PopupUtil.showPopupWindowInCenter(this, R.layout.window_login, new PopupUtil.PopupWindowInit() {
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
                        new AsyncTask<String, Void, Void>() {
                            @Override
                            protected Void doInBackground(String... params) {
                                try {
                                    Miao.this.e = null;
                                    if (isLogin) {
                                        Log.i(TAG, "doInBackground: " + params[0] + " / " + params[1]);
                                        mState.login(new User(params[0], params[1]));
                                    }
                                } catch (final Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            handleError(e);
                                        }
                                    });
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                if (isLogin) {
                                    if (e == null) {
                                        setUserMsg();
                                        mWindow.dismiss();
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
                        new AsyncTask<String, Void, Void>() {
                            @Override
                            protected Void doInBackground(String... params) {
                                if (!isLogin) {
                                    try {
                                        Miao.this.e = null;
                                        mState.regist(new User(params[0], params[2], params[1]));
                                    } catch (final Exception e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                handleError(e);
                                            }
                                        });
                                    }
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                if (isLogin) {
                                    login.setText("返回");
                                    label.setVisibility(View.VISIBLE);
                                    summary.setVisibility(View.VISIBLE);
                                    isLogin = false;
                                } else {
                                    if (e == null) {
                                        setUserMsg();
                                        mWindow.dismiss();
                                    }
                                }
                            }
                        }.execute(user.getText().toString(), pwd.getText().toString(), summary.getText().toString());
                    }
                });
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_setting:
                ThemeUtil.loadDefaultTheme(this);
                break;
            case R.id.nav_exit:
                if (T.isLogin) {
                    mState.logout();
                    setUserMsg();
                } else {
                    finish();
                }
                break;
            case R.id.nav_login:
                login();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showUsefulDialog() {
        if (!SpUtil.getBoolean(this, C.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("人人都有\"萌\"的一面");
            builder.setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%");
            builder.setPositiveButton("我最萌(•‾̑⌣‾̑•)✧˖°", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SpUtil.putBoolean(Miao.this, C.SP_FIRST_BOOT, false);
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    private void showAppDialog() {
        VersionMessage versionMessage = getIntent().getParcelableExtra(C.EXTRA_ITEM);
        if (SpUtil.getBoolean(Miao.this, C.SP_FIRST_UPDATE, true)) {
            Log.i(TAG, "onPostExecute: here");
            AlertDialog.Builder builder = new AlertDialog.Builder(Miao.this);
            builder.setTitle(versionMessage.getVersionName());
            builder.setMessage(versionMessage.getMessage());
            builder.setPositiveButton("知道了(•‾̑⌣‾̑•)✧˖°", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SpUtil.putBoolean(Miao.this, C.SP_FIRST_UPDATE, false);
                    showUsefulDialog();
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else {
            showUsefulDialog();
        }
    }

    @Override
    public void onClick(View view) {
        hideAllFragment();

        FragmentTransaction transaction = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.ll_daily:
                if (fg_daily == null) {
                    fg_daily = DailyFragment.newInstance();
                }
                if(!fg_daily.isAdded()) {
                    transaction.add(R.id.container, fg_daily);
                }
                transaction.show(fg_daily);
                transaction.commit();
                break;
            case R.id.ll_announcement:
                if (fg_announcement == null) {
                    fg_announcement = AnnouncementFragment.newInstance();
                }
                if(!fg_announcement.isAdded()) {
                    transaction.add(R.id.container, fg_announcement);
                }
                transaction.show(fg_announcement);
                transaction.commit();
                break;
            case R.id.ll_question:
                if (fg_question == null) {
                    fg_question = QuestionFragment.newInstance();
                }
                if(!fg_question.isAdded()) {
                    transaction.add(R.id.container, fg_question);
                }
                transaction.show(fg_question);
                transaction.commit();
                break;
            case R.id.ll_water:
                if (fg_water == null) {
                    fg_water = WaterFragment.newInstance();
                }
                if(!fg_water.isAdded()) {
                    transaction.add(R.id.container, fg_water);
                }
                transaction.show(fg_water);
                transaction.commit();
                break;
            default:
                transaction.commit();
                break;
        }
        view.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_SELECTED_COLOR,
                Color.rgb(255, 255, 255)));
    }

    // 隐藏所有 Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        if (fg_daily != null) {
            transaction.hide(fg_daily);
        }
        if (fg_announcement != null) {
            transaction.hide(fg_announcement);
        }
        if (fg_question != null) {
            transaction.hide(fg_question);
        }
        if (fg_water != null) {
            transaction.hide(fg_water);
        }
        transaction.commit();

        ll_daily.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        ll_announcement.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        ll_ask.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        ll_water.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
    }

    private void initViews() {
        iv_daily = (ImageView) findViewById(R.id.iv_daily);
        iv_water = (ImageView) findViewById(R.id.iv_water);
        iv_question = (ImageView) findViewById(R.id.iv_ask);
        iv_announcement = (ImageView) findViewById(R.id.iv_announcement);

        ll_daily = (LinearLayout) findViewById(R.id.ll_daily);
        ll_announcement = (LinearLayout) findViewById(R.id.ll_announcement);
        ll_ask = (LinearLayout) findViewById(R.id.ll_question);
        ll_water = (LinearLayout) findViewById(R.id.ll_water);

        ll_daily.setOnClickListener(this);
        ll_announcement.setOnClickListener(this);
        ll_ask.setOnClickListener(this);
        ll_water.setOnClickListener(this);

        fm = getSupportFragmentManager();

        setUserMsg();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // 双击结束
            long time = System.currentTimeMillis();
            // 阈值 1000ms
            if (time - backTime > 1000) {
                backTime = time;
                Toast.makeText(this, "再点一次退出应用", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

    // 错误处理
    private void handleError(Exception e) {
        this.e = e;
        e.printStackTrace();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void setUserMsg() {
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {
                return mState.getLocalUser();
            }

            @Override
            protected void onPostExecute(User u) {
                View headerView = navigationView.getHeaderView(0);
                navigationView.getMenu().clear();
                ImageView iv_user = (ImageView) headerView.findViewById(R.id.iv_user);
                TextView tv_user = (TextView) headerView.findViewById(R.id.tv_user);
                TextView tv_summary = (TextView) headerView.findViewById(R.id.tv_summary);

                Log.i(TAG, "onPostExecute: " + u.getName() + " / " + u.getSummary() + " / " + u.getHeadImg());

                if (u.getId() <= 0) {
                    Picasso.with(Miao.this).load(R.drawable.def_user)
                            .fit().transform(new CropCircleTransformation())
                            .into(iv_user);
                    if (u.getId() == -1)
                        navigationView.inflateMenu(R.menu.activity_main_drawer_no_login);
                    else navigationView.inflateMenu(R.menu.activity_main_drawer);
                } else {
                    Picasso.with(Miao.this).load(u.getHeadImg())
                            .fit().transform(new CropCircleTransformation())
                            .into(iv_user);
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                }
                tv_summary.setText(u.getSummary());
                tv_user.setText(u.getName());
            }
        }.execute();
    }
}
