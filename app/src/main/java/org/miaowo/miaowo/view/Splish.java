package org.miaowo.miaowo.view;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.VersionMessage;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.util.SpUtil;
import org.miaowo.miaowo.util.ThemeUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 这是加载开始时那个图片的。
 * 等以后要加入检查更新
 */
public class Splish extends BaseActivity {
    private static final String TAG = "Splish";
    VersionMessage updateMessage = null;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: Splish");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();
                        animator2.start();
                        animator3.start();
                    }
                });
            }
        }, 0, 1000);

        firstInit();
        start();
    }

    private void start() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                checkUpdate();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                startService(new Intent(Splish.this, WebService.class));
                Intent intent = new Intent(Splish.this, Miao.class);
                intent.putExtra(C.EXTRA_ITEM, updateMessage);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        }.execute();
    }

    // 用于检查更新
    private void checkUpdate() {
        PackageManager pm = getPackageManager();
        try {
            updateMessage = new MsgImpl().getUpdateMessage(pm.getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 用于第一次打开初始化参数
    private void firstInit() {
        Log.i(TAG, "firstInit: init Theme");
        if (SpUtil.getBoolean(this, C.SP_FIRST_BOOT, false)) {
            return;
        }
        ThemeUtil.loadDefaultTheme(this);
        // 设置完毕
        SpUtil.putBoolean(this, C.SP_FIRST_BOOT, true);
    }
}
