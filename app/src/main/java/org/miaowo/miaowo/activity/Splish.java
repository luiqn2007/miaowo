package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.util.SpUtil;

import okhttp3.Request;

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

        new Thread(this::checkUpdate);
        startService(new Intent(Splish.this, WebService.class));
        Intent intent = new Intent(Splish.this, Miao.class);
        startActivity(intent);
        stopProcess();
        finish();
        overridePendingTransition(0, 0);
    }

    private void firstInit() {
    }

    private void checkUpdate() {
        Request check = new Request.Builder().url(getString(R.string.url_update)).build();
        HttpUtil.utils().post(check, (call, response) -> {
            VersionMessage versionMessage = JsonUtil.utils().buildVersion(response);
            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                try {
                    if (versionMessage.getVersion() >
                            BaseActivity.get.getPackageManager().getPackageInfo(BaseActivity.get.getPackageName(), 0).versionCode) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.get);
                        builder.setTitle(versionMessage.getVersionName());
                        builder.setMessage(versionMessage.getMessage() + "\n\n点击升级后, 将会在后台下载安装包, 注意流量哦");
                        builder.setPositiveButton("升级! (•‾̑⌣‾̑•)✧˖°", (dialog, i) -> {
                            Request request = new Request.Builder().url(versionMessage.getUrl()).build();
                            HttpUtil.utils().post(request, (call1, response1) -> BaseActivity.get.update(response1));
                            dialog.dismiss();
                        });
                        builder.setNegativeButton("以后再说吧", null);
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
