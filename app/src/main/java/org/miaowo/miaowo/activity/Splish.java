package org.miaowo.miaowo.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.util.SpUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 这是加载开始时那个图片的。
 * 等以后要加入检查更新
 */
public class Splish extends BaseActivity {
    final public static String SP_FIRST_BOOT = "first_boot";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome);

        final View welcome_eyes = findViewById(R.id.welcome_eyes);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(welcome_eyes, "scaleY", 0, 1);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(welcome_eyes, "scaleY", 1, 0);
        final ObjectAnimator animator3 = ObjectAnimator.ofFloat(welcome_eyes, "alpha", 0, 1);

        animator.setDuration(200);
        animator.setStartDelay(200);
        animator2.setStartDelay(800);
        animator2.setDuration(200);
        animator3.setDuration(200);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    animator.start();
                    animator2.start();
                    animator3.start();
                });
            }
        }, 0, 1000);

        checkFirst();
        start();
    }

    private void start() {
        new MsgImpl(this).checkUpdate();
        startService(new Intent(Splish.this, WebService.class));
        Intent intent = new Intent(Splish.this, Miao.class);
        startActivity(intent);
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
}
