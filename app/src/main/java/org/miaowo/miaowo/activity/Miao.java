package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.event.ExceptionEvent;
import org.miaowo.miaowo.bean.data.event.FileEvent;
import org.miaowo.miaowo.bean.data.event.UserEvent;
import org.miaowo.miaowo.bean.data.event.VersionEvent;
import org.miaowo.miaowo.fragment.ListFragment;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.fragment.SearchFragment;
import org.miaowo.miaowo.fragment.SquareFragment;
import org.miaowo.miaowo.fragment.TopicFragment;
import org.miaowo.miaowo.fragment.UserFragment;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.set.Callbacks;
import org.miaowo.miaowo.set.windows.StateWindows;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Miao extends BaseActivity
        implements MiaoFragment.OnFragmentInteractionListener {

    // 视图
    private BaseFragment fg_square, fg_search, fg_topic, fg_unread, fg_user;
    private MiaoFragment fg_miao;

    // 组件
    private State mState;
    private StateWindows mStateWindows;
    private SpUtil mDefaultSp;

    // 加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao);
        prepareValues();
        EventBus.getDefault().register(this);
        // 绑定Fragment
        initFragment();
        // 显示对话框
        showFirstUseDialog();
    }
    private void prepareValues() {
        mState = new StateImpl(this);
        mStateWindows = StateWindows.windows(this);
        mDefaultSp = SpUtil.defaultSp(this);
    }
    private void initFragment() {
        fg_square = SquareFragment.newInstance();
        fg_search = SearchFragment.newInstance();
        fg_topic = TopicFragment.newInstance();
        fg_unread = ListFragment.FragmentGetter.UNREAD.get();
        fg_user = UserFragment.newInstance();
        fg_miao = MiaoFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.container, fg_miao).show(fg_miao).commit();
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

    // 消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLogin(UserEvent event) {
        if (event.user.getUid() == 0) {
            processError(new Exception("登录失败, 请重新登录再试一次"));
            return;
        }
        mState.setUser(event.user);
        setProcess(100, "欢迎回来, " + event.user.getUsername());
        fg_miao.loginSucceed();
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
        processError(event.e);
    }

    @Override
    public void onChooserClick(int position, View sharedView) {
        switch (position) {
            case 0:
                loadFragment(fg_square, sharedView);
                break;
            case 1:
                loadFragment(fg_unread, sharedView);
                break;
            case 2:
                loadFragment(fg_topic, sharedView);
                break;
            case 3:
                loadFragment(fg_user, sharedView);
                break;
            case 4:
                loadFragment(fg_search, sharedView);
                break;
            case 5:
                startActivity(new Intent(this, Setting.class));
                break;
            case 6:
                mState.logout();
                fg_miao.prepareLogin();
                fg_miao.getProcessController().stopProcess();
                break;
        }
    }
    private void loadFragment(BaseFragment fragment, View sharedView) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public List<String> setItemNames() {
        return Arrays.asList(
                getString(R.string.square), getString(R.string.unread), getString(R.string.topic),
                getString(R.string.user), getString(R.string.search), getString(R.string.setting),
                getString(R.string.logout));
    }

    @Override
    public List<Drawable> setItemIcons() {
        return Arrays.asList(
                getIcon(FontAwesome.Icon.faw_calendar_check_o), getIcon(FontAwesome.Icon.faw_inbox),
                getIcon(FontAwesome.Icon.faw_tags), getIcon(FontAwesome.Icon.faw_github_alt),
                getIcon(FontAwesome.Icon.faw_search), getIcon(FontAwesome.Icon.faw_wrench),
                getIcon(FontAwesome.Icon.faw_sign_out));
    }
    private Drawable getIcon(IIcon iicon) {
        return new IconicsDrawable(Miao.this, iicon).actionBar();
    }
}
