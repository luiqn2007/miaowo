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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.T;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.beans.VersionMessage;
import org.miaowo.miaowo.fragment.AnnouncementFragment;
import org.miaowo.miaowo.fragment.AskFragment;
import org.miaowo.miaowo.fragment.DailyFragment;
import org.miaowo.miaowo.fragment.WaterFragment;
import org.miaowo.miaowo.impls.StateImpl;
import org.miaowo.miaowo.impls.interfaces.NotSingle.Handled;
import org.miaowo.miaowo.impls.interfaces.State;
import org.miaowo.miaowo.set.ChatWindows;
import org.miaowo.miaowo.set.StateWindows;
import org.miaowo.miaowo.utils.SpUtil;
import org.miaowo.miaowo.utils.ThemeUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

// Android Studio自动生成的，用了一大堆支持库的东西，详见
// http://wuxiaolong.me/2015/11/06/DesignSupportLibrary/
public class Miao extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, Handled{
    private static final String TAG = "Miao";

    // 视图
    private TextView tv_daily, tv_announcement, tv_ask, tv_water;
    private Fragment fg_daily, fg_announcement, fg_ask, fg_water;
    private NavigationView navigationView;

    // 组件
    private FragmentManager fm;
    private State mState;
    private ChatWindows mChatWindows;
    private StateWindows mStateWindows;

    // 数据
    private long backTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_miao);
        super.onCreate(savedInstanceState);

        mState = new StateImpl();
        mChatWindows = new ChatWindows(this);
        mStateWindows = new StateWindows(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 绑定控件
        initViews();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        // 这里是在 Activity 完全加载后调用
        super.onPostCreate(savedInstanceState);
        // 加载用户信息
        setUserMsg();
        // 显示 每日 页面
        onClick(findViewById(R.id.tv_daily));
        // 显示对话框
        showAppDialog();
        // 记录APP打开时间
        backTime = System.currentTimeMillis();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 侧栏的动作响应
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
                mStateWindows.showLogin();
                break;
            case R.id.nav_chat:
                mChatWindows.showChatList(new ArrayList<User>());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    弹窗
     */
    private void showFirstUseDialog() {
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
                    showFirstUseDialog();
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else {
            showFirstUseDialog();
        }
    }

    @Override
    public void onClick(View view) {
        // 处理点击事件， 跳转不同 碎片
        // 关闭所有 Fragment
        hideAllFragment();

        // 加载相应的 Fragment
        FragmentTransaction transaction = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.tv_daily:
                if (fg_daily == null) {
                    fg_daily = DailyFragment.newInstance();
                }
                if(!fg_daily.isAdded()) {
                    transaction.add(R.id.container, fg_daily);
                }
                transaction.show(fg_daily);
                transaction.commit();
                break;
            case R.id.tv_announcement:
                if (fg_announcement == null) {
                    fg_announcement = AnnouncementFragment.newInstance();
                }
                if(!fg_announcement.isAdded()) {
                    transaction.add(R.id.container, fg_announcement);
                }
                transaction.show(fg_announcement);
                transaction.commit();
                break;
            case R.id.tv_ask:
                if (fg_ask == null) {
                    fg_ask = AskFragment.newInstance();
                }
                if(!fg_ask.isAdded()) {
                    transaction.add(R.id.container, fg_ask);
                }
                transaction.show(fg_ask);
                transaction.commit();
                break;
            case R.id.tv_water:
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
        if (fg_ask != null) {
            transaction.hide(fg_ask);
        }
        if (fg_water != null) {
            transaction.hide(fg_water);
        }
        transaction.commit();

        tv_daily.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        tv_announcement.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        tv_ask.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        tv_water.setBackgroundColor(SpUtil.getInt(this, C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
    }

    private void initViews() {
        tv_daily = (TextView) findViewById(R.id.tv_daily);
        tv_water = (TextView) findViewById(R.id.tv_water);
        tv_ask = (TextView) findViewById(R.id.tv_ask);
        tv_announcement = (TextView) findViewById(R.id.tv_announcement);

        tv_daily.setOnClickListener(this);
        tv_announcement.setOnClickListener(this);
        tv_ask.setOnClickListener(this);
        tv_water.setOnClickListener(this);

        fm = getSupportFragmentManager();
    }

    @Override
    public void onBackPressed() {
        // 返回键
        // 有侧滑打开，则关闭
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

    @Override
    public void handleError(Exception e) {
        // 错误处理
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
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                ImageView iv_user = (ImageView) headerView.findViewById(R.id.iv_user);
                TextView tv_user = (TextView) headerView.findViewById(R.id.tv_user);
                TextView tv_summary = (TextView) headerView.findViewById(R.id.tv_summary);

                Log.i(TAG, "onPostExecute: " + u.getName() + " / " + u.getSummary() + " / " + u.getHeadImg());

                if (u.getId() == -1) {
                    Picasso.with(Miao.this).load(R.drawable.def_user)
                            .fit().transform(new CropCircleTransformation())
                            .into(iv_user);
                    getMenuInflater().inflate(R.menu.inmenu_logout, navigationView.getMenu());
                } else {
                    Picasso.with(Miao.this).load(u.getHeadImg())
                            .fit().transform(new CropCircleTransformation())
                            .into(iv_user);
                    getMenuInflater().inflate(R.menu.inmenu_login, navigationView.getMenu());
                }
                tv_summary.setText(u.getSummary());
                tv_user.setText(u.getName());
            }
        }.execute();
    }
}
