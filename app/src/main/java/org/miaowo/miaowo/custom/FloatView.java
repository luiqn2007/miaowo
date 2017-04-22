package org.miaowo.miaowo.custom;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseViewHolder;

import java.util.ArrayList;

/**
 * 悬浮窗
 * Created by luqin on 17-1-21.
 */

public class FloatView extends LinearLayout {
    public static ArrayList<FloatView> shownWindows = new ArrayList<>();

    private PointF mStartTouch = new PointF();
    private PointF mEndTouch = new PointF();
    private PointF mChange = new PointF();
    private Point mPosition = new Point(0, 0);
    private int mGravity = Gravity.CENTER;
    private WindowManager mManager;
    private View mView;
    private String mTitle;
    private boolean isShowing = false;

    public FloatView(String title, @LayoutRes int layout) {
        super(BaseActivity.get);
        mTitle = title;
        init();
        setLayout(layout);
    }
    public FloatView(Context context) {
        super(context);
        init();
    }
    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FloatView show(int gravity, Point position) {
        mPosition = position;
        mGravity = gravity;
        return show();
    }
    public FloatView show() {
        if (isShowing) {
            return this;
        }
        WindowManager.LayoutParams params = buildLayoutParams();
        shownWindows.add(this);
        isShowing = true;
        mManager.addView(this, params);
        return this;
    }
    public FloatView dismiss(boolean clear) {
        if (isShowing) {
            mManager.removeView(this);
            shownWindows.remove(this);
            isShowing = false;
            if (clear) {
                mPosition.set(0, 0);
                mGravity = Gravity.CENTER;
                mTitle = "";
            }
        }
        return this;
    }
    private WindowManager.LayoutParams buildLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.RGBA_8888);
        params.gravity = mGravity;
        params.x = this.mPosition.x;
        params.y = this.mPosition.y;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return params;
    }
    private void init() {
        mManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(getContext()).inflate(R.layout.ui_window_normal, this);
    }

    private void setLayout(@LayoutRes int layout) {
        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        mView = LayoutInflater.from(getContext()).inflate(layout, container);
    }

    public View getView() {
        return mView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartTouch.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mEndTouch.set(event.getX(), event.getY());
                mChange.set(mEndTouch.x - mStartTouch.x, mEndTouch.y - mStartTouch.y);
                if (Math.sqrt(Math.pow(mChange.x, 2) + Math.pow(mChange.y, 2)) >= 20) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
                    params.x += mChange.x;
                    params.y += mChange.y;
                    mPosition.set(params.x, params.y);
                    mManager.updateViewLayout(this, params);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public FloatView defaultBar() {
        BaseViewHolder holder = new BaseViewHolder(this);
        holder.setClickListener(R.id.iv_close, view -> dismiss(false));
        holder.setText(R.id.tv_page, mTitle == null ? "" : mTitle);
        holder.getView(R.id.pb_loading).setVisibility(GONE);
        return this;
    }

    public String getTitle() {
        return mTitle;
    }
}
