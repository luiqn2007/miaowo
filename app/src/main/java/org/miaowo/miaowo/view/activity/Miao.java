package org.miaowo.miaowo.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
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

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.VersionMessage;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.fragment.SearchFragment;
import org.miaowo.miaowo.fragment.SquareFragment;
import org.miaowo.miaowo.fragment.TopicFragment;
import org.miaowo.miaowo.fragment.UnreadFragment;
import org.miaowo.miaowo.fragment.UserFragment;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.root.MyApp;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.set.windows.ChatWindows;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.set.windows.StateWindows;
import org.miaowo.miaowo.util.FragmentUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.util.ArrayList;

// Android Studio自动生成的，用了一大堆支持库的东西，详见
// http://wuxiaolong.me/2015/11/06/DesignSupportLibrary/
public class Miao extends BaseActivity implements Drawer.OnDrawerItemClickListener {
    final public static String FRAGMENT_DAILY = "daily";
    final public static String FRAGMENT_ANNOUNCEMENT = "announcement";
    final public static String FRAGMENT_QUESTION = "question";
    final public static String FRAGMENT_WATER = "water";
    final public static String FRAGMENT_TOPIC = "topic";
    final public static String FRAGMENT_U_QUESTION = "u_question";
    final public static String FRAGMENT_U_ANSWER = "u_answer";
    final public static String FRAGMENT_U_REPLY = "u_reply";

    // 视图
    private Drawer drawer = null;
    private CrossfadeDrawerLayout cdl;
    private Fragment fg_square, fg_search, fg_topic, fg_unread, fg_user;

    // 组件
    private State mState;
    private ChatWindows mChatWindows;
    private StateWindows mStateWindows;
    private MessageWindows mMessageWindows;
    private FragmentManager mManager;

    // 数据
    private long lastExit = 0;
    private AccountHeader userHeader = null;
    private ProfileDrawerItem guestMsg = null;
    private ProfileDrawerItem userMsg = null;
    private IDrawerItem[] loginItems = null;
    private IDrawerItem[] logoutItems = null;
    private IDrawerItem[] fragmentItems = null;

    // 加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_miao);
        super.onCreate(savedInstanceState);
        prepareValues();
        loadUserMsg();
        initDrawer(savedInstanceState);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        // 这里是在 Activity 完全加载后调用
        super.onPostCreate(savedInstanceState);
        // 显示对话框
        showAppDialog();
        // 绑定Fragment
        initFragment();
    }
    private void prepareValues() {
        D.getInstance().miaoActivity = this;
        mState = new StateImpl();
        mChatWindows = new ChatWindows();
        mStateWindows = new StateWindows();
        mMessageWindows = new MessageWindows();

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
        User guest = D.getInstance().guest;
        userMsg = new ProfileDrawerItem();
        guestMsg = new ProfileDrawerItem().withName(guest.getName()).withEmail(guest.getSummary()).withIcon(ImageUtil.getDrawable(guest.getHeadImg()));
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
        mManager = getSupportFragmentManager();

        fg_square = SquareFragment.newInstance();
        fg_search = SearchFragment.newInstance();
        fg_topic = TopicFragment.newInstance();
        fg_unread = UnreadFragment.newInstance();
        fg_user = UserFragment.newInstance();

        onItemClick(null, 1, null);
    }

    // 退出
    @Override
    public void onBackPressed() {
        // 返回键
        // 有侧滑打开，则关闭
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            if (mState.getLocalUser().getId() < 0) {
                long thisClick = System.currentTimeMillis();
                if (thisClick - lastExit <= 1000) {
                    super.onBackPressed();
                } else {
                    lastExit = thisClick;
                    Snackbar.make(getWindow().getDecorView(), "再按一次返回键退出", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                buildCloseDialog().show();
            }
        }
    }
    @Override
    protected void destory() {
        D.getInstance().miaoActivity = null;
    }

    // 弹窗
    private void showFirstUseDialog() {
        if (!SpUtil.getBoolean(this, Splish.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("人人都有\"萌\"的一面");
            builder.setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%");
            builder.setPositiveButton("我最萌(•‾̑⌣‾̑•)✧˖°", (dialogInterface, i) -> {
                SpUtil.putBoolean(Miao.this, Splish.SP_FIRST_BOOT, false);
                dialogInterface.dismiss();
            });
            builder.show();
        }
    }
    private void showAppDialog() {
        VersionMessage versionMessage = getIntent().getParcelableExtra(MyApp.EXTRA_ITEM);
        if (SpUtil.getBoolean(Miao.this, Splish.SP_FIRST_UPDATE, true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Miao.this);
            builder.setTitle(versionMessage.getVersionName());
            builder.setMessage(versionMessage.getMessage());
            builder.setPositiveButton("知道了(•‾̑⌣‾̑•)✧˖°", (dialogInterface, i) -> {
                SpUtil.putBoolean(Miao.this, Splish.SP_FIRST_UPDATE, false);
                showFirstUseDialog();
                dialogInterface.dismiss();
            });
            builder.show();
        } else {
            showFirstUseDialog();
        }
    }
    private AlertDialog buildCloseDialog() {
        AlertDialog ad = (new AlertDialog.Builder(this)).create();
        ad.setMessage("关闭后是否继续接收消息？");
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
        LogUtil.i(position + "");
        switch (position) {
            case 1:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_square);
                break;
            case 2:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_unread);
                break;
            case 3:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_topic);
                break;
            case 4:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_user);
                break;
            case 5:
                FragmentUtil.hideAllFragment(mManager);
                FragmentUtil.showFragment(mManager, R.id.container, fg_search);
                break;
            default:
                doAction(position - 7);
        }
        drawer.closeDrawer();
        return true;
    }
    public void loadUserMsg() {
        User u = mState.getLocalUser();
        setMenu();
        if (u.getId() < 0) {
            userHeader.setActiveProfile(guestMsg);
            return;
        }
        userMsg.withName(u.getName()).withEmail(u.getSummary());
        Drawable headIcon = ImageUtil.getDrawable(u.getHeadImg());
        if (headIcon == null) userMsg.withIcon(Uri.parse(u.getHeadImg()));
        else userMsg.withIcon(headIcon);
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

        if (mState.getLocalUser().getId() >= 0) drawer.addItems(loginItems);
        else drawer.addItems(logoutItems);
    }
    private void doAction(int position) {
        if (mState.getLocalUser().getId() >= 0) {
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
}
