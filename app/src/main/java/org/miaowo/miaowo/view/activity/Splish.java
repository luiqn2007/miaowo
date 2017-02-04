package org.miaowo.miaowo.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.VersionMessage;
import org.miaowo.miaowo.impl.QuestionsImpl;
import org.miaowo.miaowo.root.MyApplication;
import org.miaowo.miaowo.root.BaseActivity;
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
    final public static String SP_FIRST_BOOT = "first_boot";
    final public static String SP_FIRST_UPDATE = "first_update";

    private VersionMessage updateMessage = null;

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

        firstInit();
        start();
    }

    private void start() {
        new AsyncTask<Void, Void, Exception>() {

            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    checkUpdate();
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e != null) {
                    handleError(e);
                }

                startService(new Intent(Splish.this, WebService.class));
                Intent intent = new Intent(Splish.this, Miao.class);
                intent.putExtra(MyApplication.EXTRA_ITEM, updateMessage);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        }.execute();
    }

    // 用于检查更新
    private void checkUpdate() throws Exception {
        PackageManager pm = getPackageManager();
        updateMessage = new QuestionsImpl().getUpdateMessage(pm.getPackageInfo(getPackageName(), 0).versionCode);
    }

    // 用于第一次打开初始化参数
    private void firstInit() {
        if (SpUtil.getBoolean(this, SP_FIRST_BOOT, false)) {
            return;
        }
        ThemeUtil.loadDefaultTheme(this);
        // 设置完毕
        SpUtil.putBoolean(this, SP_FIRST_BOOT, true);
    }

    @Override
    public void handleError(Exception e) {
        super.handleError(e);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("出现错误：");
        builder.setMessage(e.getMessage());
        builder.setNegativeButton("退出", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        builder.setPositiveButton("重试", ((dialog, which) -> {
            dialog.dismiss();
            start();
        }));
        builder.show();
    }
}
