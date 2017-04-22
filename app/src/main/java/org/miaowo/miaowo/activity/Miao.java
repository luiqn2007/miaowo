package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.custom.ChatButton;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.fragment.SearchFragment;
import org.miaowo.miaowo.fragment.SquareFragment;
import org.miaowo.miaowo.fragment.TopicFragment;
import org.miaowo.miaowo.fragment.UnreadFragment;
import org.miaowo.miaowo.fragment.UserFragment;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.SpUtil;

public class Miao extends BaseActivity
        implements MiaoFragment.OnFragmentInteractionListener {

    // 视图
    private BaseFragment fg_square, fg_search, fg_topic, fg_unread, fg_user;
    public static MiaoFragment fg_miao;

    // 组件
    private State mState;
    private SpUtil mDefaultSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao);
    }
    @Override
    public void initActivity() {
        mState = new StateImpl();
        mDefaultSp = SpUtil.defaultSp();
        // 绑定Fragment
        fg_square = SquareFragment.newInstance();
        fg_search = SearchFragment.newInstance();
        fg_topic = TopicFragment.newInstance();
        fg_unread = UnreadFragment.newInstance();
        fg_user = UserFragment.newInstance();
        fg_miao = MiaoFragment.newInstance();
        loadFragment(R.id.container, fg_miao);
        // 显示对话框
        if (mDefaultSp.getBoolean(Splish.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("人人都有\"萌\"的一面");
            builder.setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%");
            builder.setPositiveButton("我最萌(•‾̑⌣‾̑•)✧˖°", (dialogInterface, i) -> {
                mDefaultSp.putBoolean(Splish.SP_FIRST_BOOT, false);
                dialogInterface.dismiss();
            });
            builder.show();
        }
    }

    @Override
    public void onChooserClick(int position) {
        switch (position) {
            case 0:
                loadFragment(R.id.container, fg_square);
                break;
            case 1:
                loadFragment(R.id.container, fg_unread);
                break;
            case 2:
                loadFragment(R.id.container, fg_topic);
                break;
            case 3:
                loadFragment(R.id.container, fg_user);
                break;
            case 4:
                loadFragment(R.id.container, fg_search);
                break;
            case 5:
                startActivity(new Intent(this, Setting.class));
                break;
            case 6:
                mState.logout();
                fg_miao.prepareLogin();
                fg_miao.getProcessController().stopProcess();
                ChatButton.hide();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!fg_miao.isVisible()) {
            loadFragment(R.id.container, fg_miao);
        } else if (mState.isLogin()) {
            mState.logout();
        } else {
            super.onBackPressed();
        }
    }
}
