package org.miaowo.miaowo.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.FileProvider
import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.Picasso
//import com.tencent.bugly.crashreport.CrashReport
import okhttp3.Request
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.MyActivityLifecycle
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.util.call
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
        const val APP_KEY = "83f06505-78a1-43ee-89c3-168c4464c2d8"
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

        DrawerImageLoader.init(object : DrawerImageLoader.IDrawerImageLoader {
            override fun placeholder(ctx: Context?) = placeholder(ctx, null)
            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) = set(imageView, uri, placeholder, null)

            override fun placeholder(ctx: Context?, tag: String?): Drawable {
                return ResourcesCompat
                        .getDrawable(this@App.resources, R.drawable.ic_loading, null)!!
            }


            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?, tag: String?) {
                var creator = Picasso.with(imageView?.context)
                        .load(uri)
                        .placeholder(placeholder).error(R.drawable.ic_error).fit()
                if (tag != null) creator = creator.tag(tag)
                creator.into(imageView)
            }

            override fun cancel(imageView: ImageView?) {
                Picasso.with(imageView?.context).cancelRequest(imageView)
            }

        })
//        CrashReport.initCrashReport(applicationContext, APP_KEY, false)
    }

    /**
     * 进行升级操作
     */
    fun update(url: String) {
        Request.Builder().url(url).build().call { _, response ->
            val dir = getDir("update", Context.MODE_PRIVATE)
            if (!dir.isDirectory) dir.mkdirs()
            val file = File(dir, "miaowo.apk")
            if (file.exists()) file.delete()
            if (!file.createNewFile()) throw Exception(getString(R.string.err_apk))
            val fis = FileOutputStream(file)
            val bis = BufferedInputStream(response?.body()!!.byteStream())
            val bs = ByteArray(1024 * 512)
            var length = bis.read(bs)
            while (length > 0) {
                fis.write(bs, 0, length)
                length = bis.read(bs)
            }
            bis.close()
            fis.close()
            installApk(file)
        }
    }

    private fun installApk(file: File) {
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