package org.miaowo.miaowo.impl;

import android.support.v7.app.AlertDialog;

import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.impl.interfaces.Message;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

/**
 * {@link Message} 的具体实现类
 * Created by lq2007 on 16-11-21.
 */

public class MsgImpl implements Message {

    @Override
    public void sendQuestion(String name, String title, String content) {
        // TODO: 发送新问题
    }

    @Override
    public void sendReply(Post question, String content) {
        // TODO: 发送回复
    }

    @Override
    public void checkUpdate() {
        HttpUtil.utils().post("http://www.baidu.com", (call, response) -> {
            VersionMessage versionMessage = JsonUtil.utils().buildVersion(response);
            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                try {
                    if (versionMessage.getVersion() >
                            BaseActivity.get.getPackageManager().getPackageInfo(BaseActivity.get.getPackageName(), 0).versionCode) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.get);
                        builder.setTitle(versionMessage.getVersionName());
                        builder.setMessage(versionMessage.getMessage());
                        builder.setPositiveButton("升级! (•‾̑⌣‾̑•)✧˖°", (dialog, i) -> {
                            HttpUtil.utils().post(versionMessage.getUrl(),
                                    (call1, response1) -> BaseActivity.get.update());
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
