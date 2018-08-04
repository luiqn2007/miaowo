package org.miaowo.miaowo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_send.*
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.FormatUtil

/**
 * 展示图片
 * Created by lq200 on 2018/3/19.
 */
class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val screenSize = FormatUtil.screenSize
        val minSize = Math.min(screenSize.x, screenSize.y)
        Picasso.with(App.i).load(intent.getStringExtra(Const.URL))
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loading)
                .resize(minSize, minSize)
                .into(image)
    }
}