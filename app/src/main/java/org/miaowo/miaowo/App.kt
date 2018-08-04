package org.miaowo.miaowo

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.widget.ImageView
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import org.miaowo.miaowo.other.template.EmptyCallback
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
        val SP by lazy { SPUtils.getInstance("miao")!! }
    }

    override fun onCreate() {
        super.onCreate()
        i = this
        Log.i("MiaoApp", "喵～～～ \n"
                + "凯神团队创建了好奇喵，后来团队解散，好奇喵服务器关停\n"
                + "幸好在 GC 前，小久等风纪组重新组织，系统菌重新创建网页版好奇喵，名为 喵窝\n"
                + "\n向凯神团队，好奇喵风纪组等每一位为维护好奇喵健康发展的喵们致敬\n"
                + "\n全体起立，敬礼\n")

        DrawerImageLoader.init(object : DrawerImageLoader.IDrawerImageLoader {
            override fun placeholder(ctx: Context?) = placeholder(ctx, null)
            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) = set(imageView, uri, placeholder, null)
            override fun placeholder(ctx: Context?, tag: String?) = ResourcesCompat.getDrawable(this@App.resources, R.drawable.ic_loading, null)!!
            override fun cancel(imageView: ImageView?) = Picasso.with(imageView?.context).cancelRequest(imageView)
            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?, tag: String?) {
                var creator = Picasso.with(imageView?.context)
                        .load(uri)
                        .placeholder(placeholder).error(R.drawable.ic_error).fit()
                if (tag != null) creator = creator.tag(tag)
                creator.into(imageView)
            }
        })

        Utils.init(this)
    }

    /**
     * 进行升级操作
     */
    fun update(url: String) {
        API.okhttp.newCall(Request.Builder().url(url).build()).enqueue(object : EmptyCallback<Any>() {
            override fun onResponse(call: Call?, response: Response?) {
                val dir = getDir("update", Context.MODE_PRIVATE)
                if (!dir.isDirectory) dir.mkdirs()
                val file = File(dir, "miaowo.apk")
                if (file.exists()) file.delete()
                if (!file.createNewFile()) throw Exception(getString(R.string.err_apk))
                response?.body()?.byteStream()?.apply {
                    val out = FileOutputStream(file)
                    copyTo(out)
                    out.flush()
                    out.close()
                }?.close()
                AppUtils.installApp(file)
            }
        })
    }
}