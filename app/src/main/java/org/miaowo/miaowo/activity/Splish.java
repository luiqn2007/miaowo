package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.os.Bundle;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.fragment.MiaoFragment;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.util.SpUtil;

/**
 * 这是加载开始时那个图片的。
 * 等以后要加入检查更新
 */
public class Splish extends BaseActivity {
    final public static String SP_FIRST_BOOT = "first_boot";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome);
    }

    @Override
    public void initActivity() {
        if (SpUtil.defaultSp().getBoolean(SP_FIRST_BOOT, true)) {
            firstInit();
        }

        new Thread(() -> new MsgImpl().checkUpdate()).run();
        startService(new Intent(Splish.this, WebService.class));
        Intent intent = new Intent(Splish.this, Miao.class);
        startActivity(intent);
        stopProcess();
        finish();
        overridePendingTransition(0, 0);
    }

    private void firstInit() {
    }
}
