package org.miaowo.miaowo.other

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

/**
 * 当 RecyclerView 滑动时, 控制 Fab 按钮隐藏/显示
 * Created by luqin on 17-4-20.
 */

@Suppress("unused", "UNUSED_PARAMETER")
class FabScrollBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                     directTargetChild: View, target: View, axes: Int, type: Int) = true

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
                                dyUnconsumed: Int, type: Int) {
        if (dyConsumed > 0 && child.isEnabled) {
            ObjectAnimator.ofPropertyValuesHolder(child,
                    PropertyValuesHolder.ofFloat("translationY", 0f, 500f),
                    PropertyValuesHolder.ofFloat("alpha", 1f, 0f)).setDuration(300).start()
            child.isEnabled = false
        } else if (dyConsumed < 0 && !child.isEnabled) {
            ObjectAnimator.ofPropertyValuesHolder(child,
                    PropertyValuesHolder.ofFloat("translationY", 500f, 0f),
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f)).setDuration(300).start()
            child.isEnabled = true
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                    target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }
}
