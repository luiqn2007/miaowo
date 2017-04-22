package org.miaowo.miaowo.custom;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Picasso 加载剪裁圆形
 * Created by luqin on 17-4-11.
 */

public class CircleTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        int size = Math.min(width, height);
        float r = size / 2;
        float dw = width - size;
        float dh = height - size;

        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        paint.setDither(true);

        if (dw != 0 || dh != 0) {
            Matrix centerMatrix = new Matrix();
            centerMatrix.setTranslate(-dw/2, -dh/2);
            canvas.setMatrix(centerMatrix);
        }
        canvas.drawCircle(r, r, r, paint);

        source.recycle();
        return result;
    }

    @Override
    public String key() {
        return "toCircle()";
    }
}
