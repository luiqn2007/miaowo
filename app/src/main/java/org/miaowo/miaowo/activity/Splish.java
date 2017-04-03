package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.util.SpUtil;

import java.util.List;

/**
 * 这是加载开始时那个图片的。
 * 等以后要加入检查更新
 */
public class Splish extends BaseActivity implements MiaoFragment.OnFragmentInteractionListener {
    final public static String SP_FIRST_BOOT = "first_boot";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome);
        checkFirst();
        start();
    }

    private void start() {
        new MsgImpl(this).checkUpdate();
        startService(new Intent(Splish.this, WebService.class));
        Intent intent = new Intent(Splish.this, Miao.class);
        startActivity(intent);
        stopProcess();
        finish();
        overridePendingTransition(0, 0);
    }

    // 用于第一次打开初始化参数
    private void checkFirst() {
        if (SpUtil.defaultSp(this).getBoolean(SP_FIRST_BOOT, false)) {
            return;
        }
        firstInit();
        // 设置完毕
        SpUtil.defaultSp(this).putBoolean(SP_FIRST_BOOT, true);
    }
    private void firstInit() {
    }

    @Override
    public void onChooserClick(int position, View shared) {

    }

    @Override
    public List<String> setItemNames() {
        return null;
    }

    @Override
    public List<Drawable> setItemIcons() {
        return null;
    }
}
