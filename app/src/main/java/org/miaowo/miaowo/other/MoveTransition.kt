package org.miaowo.miaowo.other

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.support.transition.Transition
import android.support.transition.TransitionValues
import android.view.ViewGroup

/**
 * MiaoFragment 界面动画集合中的移动动画
 * 为了兼容老版本
 */
class MoveTransition : Transition() {
    override fun captureEndValues(transitionValues: TransitionValues) {
        transitionValues.values.put("x", transitionValues.view.translationX)
        transitionValues.values.put("y", transitionValues.view.translationY)
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        transitionValues.values.put("x", transitionValues.view.translationX)
        transitionValues.values.put("y", transitionValues.view.translationY)
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (startValues == null || endValues == null) return null
        val startX = startValues.values["x"] as Float
        val startY = startValues.values["y"] as Float
        val endX = endValues.values["x"] as Float
        val endY = endValues.values["y"] as Float
        return ObjectAnimator.ofPropertyValuesHolder(startValues.view,
                PropertyValuesHolder.ofFloat("translationX", startX, endX),
                PropertyValuesHolder.ofFloat("translationY", startY, endY))
    }
}
