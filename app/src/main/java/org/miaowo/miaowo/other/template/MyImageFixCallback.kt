package org.miaowo.miaowo.other.template

import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.callback.ImageFixCallback
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.util.FormatUtil
import java.lang.Exception
import kotlin.math.max
import kotlin.math.min

/**
 * RichText 图片大小设置
 *
 * placeIcon / errorIcon: 1/4 ScreenSize
 * image: 1/2 ScreenSize
 *
 * Created by lq200 on 2018/3/28.
 */
open class MyImageFixCallback : ImageFixCallback {
    private val screenSize = FormatUtil.screenSize
    private val maxSize = min(screenSize.x, screenSize.y) / 2
    private val iconSize = maxSize / 2
    private val imgArr = arrayOf(maxSize, maxSize)

    override fun onInit(holder: ImageHolder?) {
        lInfo(holder?.source ?: "no img url", "onInit")
        holder?.isAutoFix = false
        holder?.scaleType = ImageHolder.ScaleType.center
        holder?.setSize(iconSize, iconSize)
    }

    override fun onFailure(holder: ImageHolder?, e: Exception?) {
        lInfo(holder?.source ?: "no img url", "onFailure")
        holder?.setSize(iconSize, iconSize)
    }

    override fun onLoading(holder: ImageHolder?) {
        lInfo(holder?.source ?: "no img url", "onLoading")
        holder?.setSize(iconSize, iconSize)
    }

    override fun onSizeReady(holder: ImageHolder?, imageWidth: Int, imageHeight: Int, sizeHolder: ImageHolder.SizeHolder?) {
        lInfo(holder?.source ?: "no img url", "onSizeReady")
        holder?.width = maxSize
        holder?.height = maxSize

        val maxImgSize = max(imageHeight, imageWidth)
        val rWidth: Int
        val rHeight: Int
        if (maxImgSize > maxSize) {
            val scale = maxImgSize.toFloat() / maxSize
            if (maxImgSize == imageWidth) {
                rWidth = maxSize
                rHeight = (maxSize / scale).toInt()
            } else {
                rHeight = maxSize
                rWidth = (maxSize / scale).toInt()
            }
        } else {
            rWidth = imageWidth
            rHeight = imageHeight
        }

        sizeHolder?.setSize(rWidth, rHeight)
        imgArr[0] = rWidth
        imgArr[1] = rHeight
    }

    override fun onImageReady(holder: ImageHolder?, width: Int, height: Int) {
        lInfo(holder?.source ?: "no img url", "onImageReady")
        holder?.setSize(imgArr[0], imgArr[1])
    }
}