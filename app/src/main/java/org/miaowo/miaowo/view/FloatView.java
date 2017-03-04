package org.miaowo.miaowo.view;

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
import android.widget.LinearLayout;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.D;

import java.util.ArrayList;

/**
 * 悬浮窗
 * Created by luqin on 17-1-21.
 */

public class FloatView extends LinearLayout {
    public static ArrayList<FloatView> shownWindows = new ArrayList<>();

    private PointF startTouch = new PointF();
    private PointF endTouch = new PointF();
    private PointF change = new PointF();
    private WindowManager mManager;
    private View v;
    private boolean isShowing = false;
    private Point position;
    private BaseActivity bindActivity;

    public FloatView(@LayoutRes int layout) {
        super(D.getInstance().activeActivity);
        position = new Point(0, 0);
        setLayout(layout);
        init();
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
        if (position != null) {
            this.position = position;
        }

        if (bindActivity == null) {
            return null;
        }

        WindowManager.LayoutParams params = buildLayoutParams(gravity);
        shownWindows.add(this);
        isShowing = true;
        bindActivity.getWindowManager().addView(this, params);
        return this;
    }
    public FloatView show() {
        return show(Gravity.CENTER, null);
    }
    public FloatView dismiss() {
        if (isShowing) {
            mManager.removeView(this);
            shownWindows.remove(this);
            isShowing = false;
            position = new Point(0, 0);
        }
        return this;
    }

    private void init() {
        bindActivity = D.getInstance().activeActivity;
        mManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    private void setLayout(@LayoutRes int layout) {
        v = LayoutInflater.from(getContext()).inflate(layout, this);
    }

    public View getView() {
        return v;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                endTouch.set(event.getX(), event.getY());
                change.set(endTouch.x - startTouch.x, endTouch.y - startTouch.y);
                if (Math.sqrt(Math.pow(change.x, 2) + Math.pow(change.y, 2)) >= 20) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
                    params.x += change.x;
                    params.y += change.y;
                    position.set(params.x, params.y);
                    mManager.updateViewLayout(this, params);
                }
                break;
        }
        return true;
    }

    public FloatView defaultBar() {
        View close = findViewById(R.id.iv_close);
        if (close != null) {
            close.setOnClickListener(view -> dismiss());
        }
        return this;
    }

    private WindowManager.LayoutParams buildLayoutParams(int gravity) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.RGBA_8888);
        params.gravity = gravity;
        params.x = this.position.x;
        params.y = this.position.y;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return params;
    }

    public static FloatView searchByTag(Object tag) {
        if (tag == null) {
            return null;
        }
        for (FloatView floatView : shownWindows) {
            if (tag.equals(floatView)) {
                return floatView;
            }
        }
        return null;
    }
}
