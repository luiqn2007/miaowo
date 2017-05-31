package org.miaowo.miaowo.util

import android.content.Intent
import android.support.v4.content.FileProvider
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import java.io.File

/**
 * 用于更新
 * Created by luqin on 17-4-23.
 */

object UpdateUtil {

    fun install(file: File) {
        if (file.exists()) {
            val uri = FileProvider.getUriForFile(App.i,
                    App.i.getString(R.string.file_provider), file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            App.i.startActivity(intent)
        }
    }
}
