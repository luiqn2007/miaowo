package org.miaowo.miaowo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.event.ExceptionEvent;
import org.miaowo.miaowo.bean.data.event.FileEvent;
import org.miaowo.miaowo.bean.data.event.UserEvent;
import org.miaowo.miaowo.bean.data.event.VersionEvent;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.fragment.ListFragment;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.fragment.SearchFragment;
import org.miaowo.miaowo.fragment.SquareFragment;
import org.miaowo.miaowo.fragment.TopicFragment;
import org.miaowo.miaowo.fragment.UserFragment;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.set.Callbacks;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.ChatWindows;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.set.windows.StateWindows;
import org.miaowo.miaowo.util.FragmentUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.io.File;
import java.util.ArrayList;

// Android Studio自动生成的，用了一大堆支持库的东西，详见
// http://wuxiaolong.me/2015/11/06/DesignSupportLibrary/
public class Miao extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    // 视图
    private Drawer drawer = null;
    private CrossfadeDrawerLayout cdl;
    private Fragment fg_square, fg_search, fg_topic, fg_unread, fg_user, fg_miao;

    // 组件
    private State mState;
    private ChatWindows mChatWindows;
    private StateWindows mStateWindows;
    private MessageWindows mMessageWindows;
    private FragmentUtil mManager;
    private SpUtil mDefaultSp;

    // 数据
    private AccountHeader userHeader = null;
    private ProfileDrawerItem guestMsg = null;
    private ProfileDrawerItem userMsg = null;
    private IDrawerItem[] loginItems = null;
    private IDrawerItem[] logoutItems = null;
    private IDrawerItem[] fragmentItems = null;

    // 加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao);
        prepareValues();
        loadUserMsg();
        initDrawer(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        // 这里是在 Activity 完全加载后调用
        super.onPostCreate(savedInstanceState);
        // 绑定Fragment
        initFragment();
        // 显示对话框
        showFirstUseDialog();
    }
    private void prepareValues() {
        mState = new StateImpl(this);
        mChatWindows = ChatWindows.windows(this);
        mStateWindows = StateWindows.windows(this);
        mMessageWindows = MessageWindows.windows(this);
        mDefaultSp = SpUtil.defaultSp(this);
        mManager = FragmentUtil.manager(getSupportFragmentManager());

        cdl = new CrossfadeDrawerLayout(this);

        loginItems = new IDrawerItem[] {
                new SecondaryDrawerItem().withName(R.string.ask).withIcon(FontAwesome.Icon.faw_plus).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new SecondaryDrawerItem().withName(R.string.chat).withIcon(FontAwesome.Icon.faw_comment_o).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new SecondaryDrawerItem().withName(R.string.setting).withIcon(FontAwesome.Icon.faw_cog).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new SecondaryDrawerItem().withName(R.string.logout).withIcon(FontAwesome.Icon.faw_sign_out).withSelectedColor(getResources().getColor(R.color.md_amber_A400))};
        logoutItems = new IDrawerItem[] {
                new SecondaryDrawerItem().withName(R.string.login).withIcon(FontAwesome.Icon.faw_user_circle).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new SecondaryDrawerItem().withName(R.string.setting).withIcon(FontAwesome.Icon.faw_cog).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new SecondaryDrawerItem().withName(R.string.exit).withIcon(FontAwesome.Icon.faw_sign_out).withSelectedColor(getResources().getColor(R.color.md_amber_A400))};
        fragmentItems = new IDrawerItem[] {
                new PrimaryDrawerItem().withName(R.string.square).withIcon(FontAwesome.Icon.faw_calendar_check_o).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new PrimaryDrawerItem().withName(R.string.unread).withIcon(FontAwesome.Icon.faw_inbox).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new PrimaryDrawerItem().withName(R.string.topic).withIcon(FontAwesome.Icon.faw_tags).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new PrimaryDrawerItem().withName(R.string.user).withIcon(FontAwesome.Icon.faw_github_alt).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new PrimaryDrawerItem().withName(R.string.search).withIcon(FontAwesome.Icon.faw_search).withSelectedColor(getResources().getColor(R.color.md_amber_A400)),
                new SectionDrawerItem().withDivider(true).withName("其他")};
        userMsg = new ProfileDrawerItem();
        guestMsg = new ProfileDrawerItem().withName("流浪喵").withEmail("").withIcon(ImageUtil.utils(this).textIcon("default", null));
        userHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .withTextColor(Color.BLACK)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .build();
        userHeader.addProfile(guestMsg, 0);
        userHeader.addProfile(userMsg, 1);
        userHeader.setActiveProfile(guestMsg);
    }
    private void initDrawer(Bundle savedInstanceState) {
        drawer = new DrawerBuilder().withActivity(this)
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .withAccountHeader(userHeader)
                .withDrawerLayout(cdl)
                .withDrawerWidthDp(72)
                .withHasStableIds(true)
                .withGenerateMiniDrawer(true)
                .withSliderBackgroundColorRes(R.color.md_amber_100)
                .withShowDrawerOnFirstLaunch(true)
                .addDrawerItems(fragmentItems)
                .build();
        setMenu();
        cdl.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        MiniDrawer miniResult = drawer.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, R.color.md_amber_A400));
        cdl.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                cdl.crossfade(400);
                //only close the drawer if we were already faded and want to close it now
                if (isCrossfaded()) {
                    drawer.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return cdl.isCrossfaded();
            }
        });
    }
    private void initFragment() {
        fg_square = SquareFragment.newInstance();
        fg_search = SearchFragment.newInstance();
        fg_topic = TopicFragment.newInstance();
        fg_unread = ListFragment.FragmentGetter.UNREAD.get();
        fg_user = UserFragment.newInstance();
        fg_miao = MiaoFragment.newInstance();

        mManager.show(R.id.container, fg_miao);
    }

    // 退出
    @Override
    public void onBackPressed() {
        // 返回键
        // 有侧滑打开，则关闭
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            buildCloseDialog().show();
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // 弹窗
    private void showFirstUseDialog() {
        if (!mDefaultSp.getBoolean(Splish.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("人人都有\"萌\"的一面");
            builder.setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%");
            builder.setPositiveButton("我最萌(•‾̑⌣‾̑•)✧˖°", (dialogInterface, i) -> {
                mDefaultSp.putBoolean(Splish.SP_FIRST_BOOT, false);
                mStateWindows.showLogin();
                dialogInterface.dismiss();
            });
            builder.show();
        }
    }
    private AlertDialog buildCloseDialog() {
        AlertDialog ad = (new AlertDialog.Builder(this)).create();
        ad.setMessage("关闭后是否继续接收消息？ 暂时无用");
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "否", (dialog, which) -> {
            stopService(new Intent(Miao.this, WebService.class));
            dialog.dismiss();
            finish();
        });
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "是", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        return ad;
    }

    // 边栏
    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (!mState.isLogin() && position <= fragmentItems.length) {
            handleError(Exceptions.E_NON_LOGIN);
            return true;
        }
        switch (position) {
            case 1:
                mManager.show(R.id.container, fg_square);
                break;
            case 2:
                mManager.show(R.id.container, fg_unread);
                break;
            case 3:
                mManager.show(R.id.container, fg_topic);
                break;
            case 4:
                mManager.show(R.id.container, fg_user);
                break;
            case 5:
                mManager.show(R.id.container, fg_search);
                break;
            default:
                doAction(position - 7);
        }
        drawer.closeDrawer();
        return true;
    }
    private void loadUserMsg() {
        User u = mState.loginedUser();
        setMenu();
        if (u == null) {
            userHeader.setActiveProfile(guestMsg);
            return;
        }
        userMsg.withName(u.getUsername()).withEmail(u.getEmail());
        if (!TextUtils.isEmpty(u.getPicture())) userMsg.withIcon(Uri.parse(getString(R.string.url_home) + u.getPicture()));
        else userMsg.withIcon(ImageUtil.utils(this).textIcon(u));
        userHeader.updateProfile(userMsg);
        userHeader.setActiveProfile(userMsg);
    }
    private void setMenu() {
        if (drawer == null) {
            return;
        }
        for (IDrawerItem item : logoutItems) {
            drawer.removeItem(item.getIdentifier());
        }
        for (IDrawerItem item : loginItems) {
            drawer.removeItem(item.getIdentifier());
        }

        if (mState.isLogin()) drawer.addItems(loginItems);
        else drawer.addItems(logoutItems);
    }
    private void doAction(int position) {
        if (mState.isLogin()) {
            switch (position) {
                case 0:
                    mMessageWindows.showNewQuestion();
                    break;
                case 1:
                    mChatWindows.showChatList(new ArrayList<>());
                    break;
                case 2:
                    startActivity(new Intent(this, Setting.class));
                    break;
                case 3:
                    mState.logout();
                    mManager.show(R.id.container, fg_miao);
                    loadUserMsg();
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    mStateWindows.showLogin();
                    break;
                case 1:
                    break;
                case 2:
                    finish();
                    break;
            }
        }
    }

    // 消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLogin(UserEvent event) {
        if (event.user.getUid() == 0) {
            TastyToast.makeText(this, "登录失败", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
            return;
        }
        mState.setUser(event.user);
        loadUserMsg();
        TastyToast.makeText(this, "欢迎回来, " + event.user.getUsername(), TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onVersion(VersionEvent event) {
        PackageManager pm = getPackageManager();
        int version;
        try {
            version = pm.getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            handleError(e);
            return;
        }
        if (event.message.getVersion() > version) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(event.message.getVersionName());
            builder.setMessage(event.message.getMessage());
            builder.setPositiveButton("升级! (•‾̑⌣‾̑•)✧˖°", (dialog, i) -> {
                HttpUtil.utils().post(event.message.getUrl(), Callbacks.UPDATE);
                dialog.dismiss();
            });
            builder.setNegativeButton("以后再说吧", null);
            builder.show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdate(FileEvent event) {
        File apk = event.file;
        TastyToast.makeText(this, "模拟升级", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onError(ExceptionEvent event) {
        handleError(event.e);
    }
}
