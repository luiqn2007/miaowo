package org.miaowo.miaowo.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Chat;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.SpUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 悬浮窗
 * Created by luqin on 17-1-21.
 */

public class ChatButton extends View {
    private static ChatButton chat;

    private Point mPosition = new Point();
    private Point mScreenSize = new Point();
    private PointF mStartTouch = new PointF();
    private PointF mEndTouch = new PointF();
    private PointF mChange = new PointF();
    private PointF mSize = new PointF(194, 194);
    private Paint mPaintFill;
    private WindowManager mManager;
    private SpUtil mSpUtil;
    private Drawable mLogo;
    private boolean mMoved = false;
    private Timer mMoveBack;

    public static void show(Context context) {
        if (chat == null) {
            chat = new ChatButton(context);
        }
        chat.show();
    }
    public static void hide() {
        chat.dismiss();
    }

    public ChatButton(Context context) {
        super(context);
        init();
    }
    public ChatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ChatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void show() {
        WindowManager.LayoutParams params = buildLayoutParams();
        try {
            mManager.addView(this, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void dismiss() {
        try {
            mManager.removeViewImmediate(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private WindowManager.LayoutParams buildLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.RGBA_8888);
        params.gravity = Gravity.LEFT|Gravity.TOP;
        params.x = this.mPosition.x;
        params.y = this.mPosition.y;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return params;
    }
    private void init() {
        mSpUtil = SpUtil.defaultSp();
        mManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mManager.getDefaultDisplay().getSize(mScreenSize);
        mPosition.set(mSpUtil.getInt("btn_x", mScreenSize.x / 2), mSpUtil.getInt("btn_y", mScreenSize.y / 2));
        mPaintFill = new Paint();
        mPaintFill.setStyle(Paint.Style.FILL);
        int color = getResources().getColor(R.color.md_deep_purple_400);
        mPaintFill.setColor(Color.argb(100, Color.red(color), Color.green(color), Color.blue(color)));
        mLogo = getResources().getDrawable(R.drawable.chat);
    }

    private void move(int dx, int dy) {
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
        params.x += dx;
        params.y += dy;
        mPosition.set(params.x, params.y);
        mManager.updateViewLayout(this, params);
    }

    private void clickAction(int x, int y, boolean moved) {
        int xTo, yTo;
        if (!moved) {
            int finalY = y;
            if (x < 0 || x > mScreenSize.x - mSize.x) {
                if (x < 0) {
                    xTo = 0;
                    if (mMoveBack != null) {
                        mMoveBack.cancel();
                    }
                    mMoveBack = new Timer();
                    mMoveBack.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            BaseActivity.get.runOnUiThreadIgnoreError(() -> clickAction(-1, finalY, true));
                            mMoveBack = null;
                        }
                    }, 600);
                } else {
                    xTo = (int) (mScreenSize.x - mSize.x);
                    if (mMoveBack != null) {
                        mMoveBack.cancel();
                    }
                    mMoveBack = new Timer();
                    mMoveBack.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            BaseActivity.get.runOnUiThreadIgnoreError(() ->
                                    clickAction((int) (mScreenSize.x - mSize.x + 1), finalY, true));
                            mMoveBack = null;
                        }
                    }, 600);
                }
            } else {
                getContext().startActivity(new Intent(getContext(), Chat.class));
                return;
            }
        } else {
            xTo = x < 0 ? (int) (-mSize.x * 2 / 3) :
                    (int) (x > mScreenSize.x - mSize.x ? mScreenSize.x - mSize.x / 3 : x);
        }
        yTo = y < 0 ? 1 :
                (int) (y > mScreenSize.y - mSize.y ? mScreenSize.y - mSize.y : y);
        int dx = x > xTo ? -1 : x < xTo ? 1 : 0;
        int dy = y > yTo ? -1 : y < yTo ? 1 : 0;
        while (x != xTo || y != yTo) {
            move(dx, dy);
            x += dx;
            y += dy;
            dx = x > xTo ? -1 : x < xTo ? 1 : 0;
            dy = y > yTo ? -1 : y < yTo ? 1 : 0;
        }
        mPosition.x = xTo;
        mPosition.y = yTo;
        mSpUtil.putInt("btn_x", mPosition.x);
        mSpUtil.putInt("btn_y", mPosition.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSize.set(getMeasuredWidth(), getMeasuredHeight());
        canvas.drawCircle(mSize.x / 2, mSize.y / 2, Math.min(mSize.x, mSize.y) / 2, mPaintFill);
        mLogo.setBounds((int) (mSize.x / 4), (int) (mSize.y / 4)
                , (int) (mSize.x * 3 / 4), (int) (mSize.y * 3 / 4));
        mLogo.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
                        ? MeasureSpec.getSize(widthMeasureSpec) : (int) mSize.x,
                MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                        ? MeasureSpec.getSize(heightMeasureSpec) : (int) mSize.y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartTouch.set(event.getX(), event.getY());
                mMoved = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                mEndTouch.set(event.getX(), event.getY());
                mChange.set(mEndTouch.x - mStartTouch.x, mEndTouch.y - mStartTouch.y);
                if (Math.sqrt(Math.pow(mChange.x, 2) + Math.pow(mChange.y, 2)) >= 3) {
                    move((int) mChange.x, (int) mChange.y);
                    mMoved = true;
                }
                return true;
            case MotionEvent.ACTION_UP:
                clickAction(mPosition.x, mPosition.y, mMoved);
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BaseActivity.get.onKeyDown(keyCode, event);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        BaseActivity.get.onKeyUp(keyCode, event);
        return true;
    }
}
