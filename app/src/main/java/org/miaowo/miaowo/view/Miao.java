package org.miaowo.miaowo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.bean.VersionMessage;
import org.miaowo.miaowo.fragment.HotFragment;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.fragment.NewestFragment;
import org.miaowo.miaowo.fragment.SearchFragment;
import org.miaowo.miaowo.fragment.SquareFragment;
import org.miaowo.miaowo.fragment.TopicFragment;
import org.miaowo.miaowo.fragment.UnreadFragment;
import org.miaowo.miaowo.fragment.UserFragment;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.set.ChatWindows;
import org.miaowo.miaowo.set.MessageWindows;
import org.miaowo.miaowo.set.StateWindows;
import org.miaowo.miaowo.util.FragmentUtil;
import org.miaowo.miaowo.util.SpUtil;
import org.miaowo.miaowo.util.ThemeUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

// Android Studio自动生成的，用了一大堆支持库的东西，详见
// http://wuxiaolong.me/2015/11/06/DesignSupportLibrary/
public class Miao extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    // 视图
    private NavigationView navigationView;
    private Fragment fg_square, fg_hot, fg_newest, fg_search, fg_topic, fg_unread, fg_user;

    // 组件
    private State mState;
    private ChatWindows mChatWindows;
    private StateWindows mStateWindows;
    private MessageWindows mMessageWindows;
    private AlertDialog closeDialog;
    private FragmentManager mManager;

    long lastExit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_miao);
        super.onCreate(savedInstanceState);

        mState = new StateImpl();
        mChatWindows = new ChatWindows();
        mStateWindows = new StateWindows();
        mMessageWindows = new MessageWindows();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        closeDialog = (new AlertDialog.Builder(this)).create();
        closeDialog.setMessage("关闭后是否继续接收消息？");
        closeDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(new Intent(Miao.this, WebService.class));
                dialog.dismiss();
                finish();
            }
        });
        closeDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        // 这里是在 Activity 完全加载后调用
        super.onPostCreate(savedInstanceState);
        // 加载用户信息
        setUserMsg();
        // 显示对话框
        showAppDialog();
        // 绑定Fragment
        initFragment();
    }

    private void initFragment() {
        mManager = getSupportFragmentManager();

        fg_square = SquareFragment.newInstance();
        fg_hot = HotFragment.newInstance();
        fg_newest = NewestFragment.newInstance();
        fg_search = SearchFragment.newInstance();
        fg_topic = TopicFragment.newInstance();
        fg_unread = UnreadFragment.newInstance();
        fg_user = UserFragment.newInstance();

        FragmentUtil.showFragment(mManager, R.id.container, MiaoFragment.newInstance());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 侧栏的动作响应
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_square:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_square);
                break;
            case R.id.nav_newest:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_newest);
                break;
            case R.id.nav_hot:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_hot);
                break;
            case R.id.nav_search:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_search);
                break;
            case R.id.nav_topic:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_topic);
                break;
            case R.id.nav_unread:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_unread);
                break;
            case R.id.nav_user:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_user);
                break;
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
            case R.id.nav_state:
                break;
            case R.id.nav_new:
                try {
                    mMessageWindows.showNewQuestion();
                } catch (Exception e) {
                    handleError(e);
                }
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
    public void onBackPressed() {
        // 返回键
        // 有侧滑打开，则关闭
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mState.getLocalUser().getId() < 0) {
                long thisClick = System.currentTimeMillis();
                if (thisClick - lastExit <= 500) {
                    super.onBackPressed();
                } else {
                    lastExit = thisClick;
                    Snackbar.make(getWindow().getDecorView(), "再按一次返回键退出", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                closeDialog.show();
            }
        }
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
