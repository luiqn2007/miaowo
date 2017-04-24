package org.miaowo.miaowo.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.miaowo.miaowo.util.LogUtil;

/**
 * 图片编辑
 * Created by luqin on 17-4-23.
 */

public class PicEditor extends android.support.v7.widget.AppCompatImageView {


    public PicEditor(Context context) {
        super(context);
    }
    public PicEditor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public PicEditor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PointF mTouch1 = new PointF();
    private PointF mTouch2 = new PointF();
    private PointF mMove1 = new PointF();
    private PointF mMove2 = new PointF();
    private PointF mDistance = new PointF();
    private PointF mCenter = new PointF();
    private float mScale;
    private Matrix mMatrix;
    private Matrix mOriMatrix;

    private Bitmap mColorShader;
    private Paint mPaint;

    private void init(int w, int h) {
        if (mColorShader == null) {
            mPaint = new Paint();

            int r = Math.min(w, h) / 4;
            Drawable colorShader = new ColorDrawable(Color.argb(200, 0, 0, 0));
            colorShader.setBounds(0, 0, w, h);
            mColorShader = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mColorShader);
            colorShader.draw(canvas);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(100, 0, 0, 0));
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawRect(new Rect(w / 2 - r, h / 2 - r, w / 2 + r, h / 2 + r), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawCircle(w / 2, h / 2, r, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                mTouch1.set(event.getX(0), event.getY(0));
                mTouch2.set(event.getX(1), event.getY(1));
                LogUtil.i(mTouch1, mTouch2);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    mMove1.set(event.getX(0), event.getY(0));
                    mMove2.set(event.getX(1), event.getY(1));
                }
                fresh();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void fresh() {
        mMatrix = getImageMatrix();
        mCenter.set((mMove1.x + mMove2.x) / 2, (mMove1.y + mMove2.y) / 2);
        mScale = (float) Math.sqrt(Math.pow(mMove1.x - mMove2.x, 2) + Math.pow(mMove1.y - mMove2.y, 2)
                / Math.pow(mTouch1.x - mTouch2.x, 2) + Math.pow(mTouch1.y - mTouch2.y, 2));
        LogUtil.i(mScale);
        mDistance.set((mMove1.x + mMove2.x - mTouch1.x - mTouch2.x) / 2
                , (mMove1.y + mMove2.y - mTouch1.y - mTouch2.y) / 2);
        mMatrix.postScale(mScale, mScale, mCenter.x, mCenter.y);
        mMatrix.postTranslate(mDistance.x, mDistance.y);
        setImageMatrix(mMatrix);
        invalidate();
        mTouch1 = mMove1;
        mTouch2 = mMove2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init(canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(mColorShader, 0, 0, mPaint);
    }

    public Bitmap getChoiceBmp() {
        return null;
    }

    public void resetPicture() {
        setImageMatrix(mOriMatrix);
        invalidate();
    }
}
