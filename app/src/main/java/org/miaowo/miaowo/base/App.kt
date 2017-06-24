package org.miaowo.miaowo.base

import android.app.Application
import android.content.Intent
import android.os.Environment
import android.support.v4.content.FileProvider
import okhttp3.Response
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.MyActivityLifecycle
import org.miaowo.miaowo.base.extra.activity
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.lInfo
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.properties.Delegates

/**
 *
 * Created by luqin on 16-12-28.
 */

class App : Application() {

    companion object {
        var i by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        i = this
        lInfo("喵～～～ \n"
                + "凯神团队创建了好奇喵，后来团队解散，好奇喵服务器关停\n"
                + "幸好在 GC 前，小久等风纪组重新组织，系统菌重新创建网页版好奇喵，名为 喵窝\n"
                + "\n向凯神团队，好奇喵风纪组等每一位为维护好奇喵健康发展的喵们致敬\n"
                + "\n全体起立，敬礼\n")

        registerActivityLifecycleCallbacks(MyActivityLifecycle)
    }

    /**
     * 进行升级操作
     */
    fun update(response: Response) {
        Thread {
            try {
                if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
                    throw Exception(getString(R.string.err_sdcard))
                } else {
                    val dir = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "miaowo")
                    if (!dir.isDirectory) if (!dir.mkdirs()) throw Exception(getString(R.string.err_dir))
                    val app = File(dir, "miaowo.apk")
                    if (app.exists()) if (!app.delete()) throw Exception(getString(R.string.err_del))
                    if (!app.createNewFile()) throw Exception(getString(R.string.err_apk))
                    val fis = FileOutputStream(app)
                    val bis = BufferedInputStream(response.body()!!.byteStream())
                    val bs = ByteArray(1024 * 512)
                    var length = bis.read(bs)
                    while (length > 0) {
                        fis.write(bs, 0, length)
                        length = bis.read(bs)
                    }
                    bis.close()
                    fis.close()

                    if (app.exists()) {
                        val uri = FileProvider.getUriForFile(App.i,
                                App.i.getString(R.string.file_provider), app)
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        intent.setDataAndType(uri, "application/vnd.android.package-archive")
                        App.i.startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                activity?.handleError(e)
            }
        }.run()
    }
}