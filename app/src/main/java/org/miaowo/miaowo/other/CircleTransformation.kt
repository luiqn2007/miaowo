package org.miaowo.miaowo.other

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader

import com.squareup.picasso.Transformation

/**
 * Picasso 加载剪裁圆形
 * Created by luqin on 17-4-11.
 */

class CircleTransformation : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val size = Math.min(width, height)
        val r = (size / 2).toFloat()
        val dw = (width - size).toFloat()
        val dh = (height - size).toFloat()

        val result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        val paint = Paint()
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        paint.isDither = true

        if (dw != 0f || dh != 0f) {
            val centerMatrix = Matrix()
            centerMatrix.setTranslate(-dw / 2, -dh / 2)
            canvas.matrix = centerMatrix
        }
        canvas.drawCircle(r, r, r, paint)

        source.recycle()
        return result
    }

    override fun key() = "toCircle()"
}
