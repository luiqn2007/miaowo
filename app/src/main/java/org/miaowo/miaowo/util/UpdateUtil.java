package org.miaowo.miaowo.util;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;

import java.io.File;

/**
 * 用于更新
 * Created by luqin on 17-4-23.
 */

public class UpdateUtil {
    private static UpdateUtil util;
    private UpdateUtil() {}
    public static UpdateUtil utils() {
        if (util == null) {
            synchronized (UpdateUtil.class) {
                if (util == null) {
                    util = new UpdateUtil();
                }
            }
        }
        return util;
    }

    public void install(File file) {
        if (file.exists()) {
            Uri uri = FileProvider.getUriForFile(BaseActivity.get,
                    BaseActivity.get.getString(R.string.file_provider), file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            BaseActivity.get.startActivity(intent);
        }
    }
}
