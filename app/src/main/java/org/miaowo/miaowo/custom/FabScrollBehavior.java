package org.miaowo.miaowo.custom;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

/**
 * 当 RecyclerView 滑动时, 控制 Fab 按钮隐藏/显示
 * Created by luqin on 17-4-20.
 */

public class FabScrollBehavior extends FloatingActionButton.Behavior {

    public FabScrollBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0 && child.isEnabled()) {
            ObjectAnimator.ofPropertyValuesHolder(child
                    , PropertyValuesHolder.ofFloat("translationY", 0, 500)
                    , PropertyValuesHolder.ofFloat("alpha", 1, 0)).setDuration(300).start();
            child.setEnabled(false);
        } else if (dyConsumed < 0 && !child.isEnabled()) {
            ObjectAnimator.ofPropertyValuesHolder(child
                    , PropertyValuesHolder.ofFloat("translationY", 500, 0)
                    , PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(300).start();
            child.setEnabled(true);
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
