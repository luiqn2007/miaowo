package org.miaowo.miaowo.other.template

import android.animation.Animator

open class EmptyAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
}