package org.miaowo.miaowo.other

import android.support.v4.animation.AnimatorCompatHelper
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import java.util.*

/**
 * 聊天消息列表动画
 * Created by luqin on 17-4-26.
 */

class ChatListAnimator : SimpleItemAnimator() {
    // 本次将执行的动画列表
    private val mPendingRemovals = ArrayList<RecyclerView.ViewHolder>()
    private val mPendingAdditions = ArrayList<RecyclerView.ViewHolder>()
    private val mPendingMoves = ArrayList<MoveInfo>()
    private val mPendingChanges = ArrayList<ChangeInfo>()
    // 动画栈
    private val mAdditionsList = ArrayList<ArrayList<RecyclerView.ViewHolder>>()
    private val mMovesList = ArrayList<ArrayList<MoveInfo>>()
    private val mChangesList = ArrayList<ArrayList<ChangeInfo>>()
    // 正在执行的动画列表
    private val mAddAnimations = ArrayList<RecyclerView.ViewHolder>()
    private val mMoveAnimations = ArrayList<RecyclerView.ViewHolder>()
    private val mRemoveAnimations = ArrayList<RecyclerView.ViewHolder>()
    private val mChangeAnimations = ArrayList<RecyclerView.ViewHolder>()

    // 视图移动动画信息
    private data class MoveInfo constructor(var holder: RecyclerView.ViewHolder, var fromX: Int, var fromY: Int, var toX: Int, var toY: Int) {
        val deltaX by lazyOf(toX - fromX)
        val deltaY by lazyOf(toY - fromY)
    }

    // 视图改变动画信息
    private data class ChangeInfo constructor(var oldHolder: RecyclerView.ViewHolder?, var newHolder: RecyclerView.ViewHolder?,
                                                  var fromX: Int, var fromY: Int, var toX: Int, var toY: Int) {
        val deltaX by lazyOf(toX - fromX)
        val deltaY by lazyOf(toY - fromY)
        fun hasOldHolder() = oldHolder != null
        fun hasNewHolder() = newHolder != null
        fun noHolder() = oldHolder == null && newHolder == null
    }

    override fun runPendingAnimations() {
        val removalsPending = !mPendingRemovals.isEmpty()
        val movesPending = !mPendingMoves.isEmpty()
        val changesPending = !mPendingChanges.isEmpty()
        val additionsPending = !mPendingAdditions.isEmpty()
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) return
        // remove
        mPendingRemovals.forEach{ animateRemoveImpl(it) }
        mPendingRemovals.clear()
        // move
        if (movesPending) {
            val moves = ArrayList<MoveInfo>()
            moves.addAll(mPendingMoves)
            mMovesList.add(moves)
            mPendingMoves.clear()
            val mover = Runnable {
                moves.forEach { animateMoveImpl(it) }
                moves.clear()
                mMovesList.remove(moves)
            }
            if (removalsPending) {
                val view = moves[0].holder.itemView
                ViewCompat.postOnAnimationDelayed(view, mover, removeDuration)
            } else
                mover.run()
        }
        // change
        if (changesPending) {
            val changes = ArrayList<ChangeInfo>()
            changes.addAll(mPendingChanges)
            mChangesList.add(changes)
            mPendingChanges.clear()
            val changer = Runnable{
                changes.forEach { animateChangeImpl(it) }
                changes.clear()
                mChangesList.remove(changes)
            }
            if (removalsPending) {
                val holder = changes[0].oldHolder
                ViewCompat.postOnAnimationDelayed(holder!!.itemView, changer, removeDuration)
            } else
                changer.run()
        }
        // add
        if (additionsPending) {
            val additions = ArrayList<RecyclerView.ViewHolder>()
            additions.addAll(mPendingAdditions)
            mAdditionsList.add(additions)
            mPendingAdditions.clear()
            val adder = Runnable {
                additions.forEach { animateAddImpl(it) }
                additions.clear()
                mAdditionsList.remove(additions)
            }
            if (removalsPending || movesPending || changesPending) {
                val removeDuration = if (removalsPending) removeDuration else 0
                val moveDuration = if (movesPending) moveDuration else 0
                val changeDuration = if (changesPending) changeDuration else 0
                val totalDelay = removeDuration + Math.max(moveDuration, changeDuration)
                val view = additions[0].itemView
                ViewCompat.postOnAnimationDelayed(view, adder, totalDelay)
            } else
                adder.run()
        }
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        resetAnimation(holder)
        mPendingRemovals.add(holder)
        return true
    }

    private fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = ViewCompat.animate(view)
        mRemoveAnimations.add(holder)
        animation.setDuration(removeDuration)
                .alpha(0f).setListener(object : VpaListenerAdapter() {
            override fun onAnimationStart(view: View) {
                dispatchRemoveStarting(holder)
            }

            override fun onAnimationEnd(view: View) {
                animation.setListener(null)
                resetView(view)
                dispatchRemoveFinished(holder)
                mRemoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        resetAnimation(holder)
        ViewCompat.setAlpha(holder.itemView, 0f)
        ViewCompat.setTranslationX(holder.itemView, (if (holder.itemViewType == Const.MY) 320 else -320).toFloat())
        ViewCompat.setTranslationY(holder.itemView, 320f)
        mPendingAdditions.add(holder)
        return true
    }

    private fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = ViewCompat.animate(view)
        mAddAnimations.add(holder)
        animation.alpha(1f).translationX(0f).translationY(0f).setDuration(addDuration).setListener(object : VpaListenerAdapter() {
            override fun onAnimationStart(view: View) = dispatchAddStarting(holder)
            override fun onAnimationCancel(view: View) = resetView(view)
            override fun onAnimationEnd(view: View) {
                animation.setListener(null)
                dispatchAddFinished(holder)
                mAddAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int,
                             toX: Int, toY: Int): Boolean {
        var aFromX = fromX
        var aFromY = fromY
        val view = holder.itemView
        aFromX += ViewCompat.getTranslationX(holder.itemView).toInt()
        aFromY += ViewCompat.getTranslationY(holder.itemView).toInt()
        resetAnimation(holder)
        val deltaX = toX - aFromX
        val deltaY = toY - aFromY
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder)
            return false
        }
        if (deltaX != 0) ViewCompat.setTranslationX(view, (-deltaX).toFloat())
        if (deltaY != 0) ViewCompat.setTranslationY(view, (-deltaY).toFloat())
        mPendingMoves.add(MoveInfo(holder, aFromX, aFromY, toX, toY))
        return true
    }

    private fun animateMoveImpl(info: MoveInfo) {
        val view = info.holder.itemView
        val deltaX = info.deltaX
        val deltaY = info.deltaY
        if (deltaX != 0) ViewCompat.animate(view).translationX(0f)
        if (deltaY != 0) ViewCompat.animate(view).translationY(0f)
        val animation = ViewCompat.animate(view)
        mMoveAnimations.add(info.holder)
        animation.setDuration(moveDuration).setListener(object : VpaListenerAdapter() {
            override fun onAnimationStart(view: View) = dispatchMoveStarting(info.holder)
            override fun onAnimationCancel(view: View) {
                if (deltaX != 0) ViewCompat.setTranslationX(view, 0f)
                if (deltaY != 0) ViewCompat.setTranslationY(view, 0f)
            }
            override fun onAnimationEnd(view: View) {
                animation.setListener(null)
                dispatchMoveFinished(info.holder)
                mMoveAnimations.remove(info.holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder?,
                               fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        if (oldHolder === newHolder) return animateMove(oldHolder, fromX, fromY, toX, toY)

        val prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView)
        val prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView)
        val prevAlpha = ViewCompat.getAlpha(oldHolder.itemView)
        resetAnimation(oldHolder)
        val deltaX = (toX.toFloat() - fromX.toFloat() - prevTranslationX).toInt()
        val deltaY = (toY.toFloat() - fromY.toFloat() - prevTranslationY).toInt()
        // recover prev translation state after ending animation
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX)
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY)
        ViewCompat.setAlpha(oldHolder.itemView, prevAlpha)
        if (newHolder != null) {
            // carry over translation values
            resetAnimation(newHolder)
            ViewCompat.setTranslationX(newHolder.itemView, (-deltaX).toFloat())
            ViewCompat.setTranslationY(newHolder.itemView, (-deltaY).toFloat())
            ViewCompat.setAlpha(newHolder.itemView, 0f)
        }
        mPendingChanges.add(ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY))
        return true
    }

    private fun animateChangeImpl(changeInfo: ChangeInfo) {
        if (changeInfo.hasOldHolder()) {
            val view = changeInfo.oldHolder!!.itemView
            val oldViewAnim = ViewCompat.animate(view).setDuration(
                    changeDuration)
            mChangeAnimations.add(changeInfo.oldHolder!!)
            oldViewAnim.translationX(changeInfo.deltaX.toFloat())
            oldViewAnim.translationY(changeInfo.deltaY.toFloat())
            oldViewAnim.alpha(0f).setListener(object : VpaListenerAdapter() {
                override fun onAnimationStart(view: View) = dispatchChangeStarting(changeInfo.oldHolder, true)
                override fun onAnimationEnd(view: View) {
                    oldViewAnim.setListener(null)
                    resetView(view)
                    dispatchChangeFinished(changeInfo.oldHolder, true)
                    mChangeAnimations.remove(changeInfo.oldHolder!!)
                    dispatchFinishedWhenDone()
                }
            }).start()
        }
        if (changeInfo.hasNewHolder()) {
            val newView = changeInfo.newHolder!!.itemView
            val newViewAnimation = ViewCompat.animate(newView)
            mChangeAnimations.add(changeInfo.newHolder!!)
            newViewAnimation.translationX(0f).translationY(0f).setDuration(changeDuration).alpha(1f).setListener(object : VpaListenerAdapter() {
                override fun onAnimationStart(view: View) = dispatchChangeStarting(changeInfo.newHolder, false)
                override fun onAnimationEnd(view: View) {
                    newViewAnimation.setListener(null)
                    resetView(newView)
                    dispatchChangeFinished(changeInfo.newHolder, false)
                    mChangeAnimations.remove(changeInfo.newHolder!!)
                    dispatchFinishedWhenDone()
                }
            }).start()
        }
    }

    private fun endChangeAnimation(infoList: MutableList<ChangeInfo>, item: RecyclerView.ViewHolder) {
        infoList.indices.reversed()
                .map { infoList[it] }
                .filter { endChangeAnimationIfNecessary(it, item) && it.noHolder() }
                .forEach { infoList.remove(it) }
    }

    private fun endChangeAnimationIfNecessary(changeInfo: ChangeInfo) {
        if (changeInfo.hasOldHolder()) endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder!!)
        if (changeInfo.hasNewHolder()) endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder!!)
    }

    private fun endChangeAnimationIfNecessary(changeInfo: ChangeInfo, item: RecyclerView.ViewHolder): Boolean {
        var oldItem = false
        if (changeInfo.newHolder === item) changeInfo.newHolder = null
        else if (changeInfo.oldHolder === item) {
            changeInfo.oldHolder = null
            oldItem = true
        } else return false
        resetView(item.itemView)
        dispatchChangeFinished(item, oldItem)
        return true
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        val view = item.itemView
        // this will trigger end callback which should set properties to their target values.
        ViewCompat.animate(view).cancel()
        for (i in mPendingMoves.indices.reversed()) {
            val moveInfo = mPendingMoves[i]
            if (moveInfo.holder === item) {
                resetView(view)
                dispatchMoveFinished(item)
                mPendingMoves.removeAt(i)
            }
        }
        endChangeAnimation(mPendingChanges, item)
        if (mPendingRemovals.remove(item)) {
            resetView(view)
            dispatchRemoveFinished(item)
        }
        if (mPendingAdditions.remove(item)) {
            resetView(view)
            dispatchAddFinished(item)
        }

        for (i in mChangesList.indices.reversed()) {
            val changes = mChangesList[i]
            endChangeAnimation(changes, item)
            if (changes.isEmpty()) mChangesList.removeAt(i)
        }
        for (i in mMovesList.indices.reversed()) {
            val moves = mMovesList[i]
            for (j in moves.indices.reversed()) {
                val moveInfo = moves[j]
                if (moveInfo.holder === item) {
                    ViewCompat.setTranslationY(view, 0f)
                    ViewCompat.setTranslationX(view, 0f)
                    dispatchMoveFinished(item)
                    moves.removeAt(j)
                    if (moves.isEmpty()) mMovesList.removeAt(i)
                    break
                }
            }
        }
        for (i in mAdditionsList.indices.reversed()) {
            val additions = mAdditionsList[i]
            if (additions.remove(item)) {
                resetView(view)
                dispatchAddFinished(item)
                if (additions.isEmpty()) mAdditionsList.removeAt(i)
            }
        }

    }

    private fun resetAnimation(holder: RecyclerView.ViewHolder) {
        AnimatorCompatHelper.clearInterpolator(holder.itemView)
        endAnimation(holder)
    }

    private fun resetView(view: View) {
        ViewCompat.setAlpha(view, 1f)
        ViewCompat.setTranslationX(view, 0f)
        ViewCompat.setTranslationY(view, 0f)
    }

    override fun isRunning(): Boolean {
        return !mPendingAdditions.isEmpty() ||
                !mPendingChanges.isEmpty() ||
                !mPendingMoves.isEmpty() ||
                !mPendingRemovals.isEmpty() ||
                !mMoveAnimations.isEmpty() ||
                !mRemoveAnimations.isEmpty() ||
                !mAddAnimations.isEmpty() ||
                !mChangeAnimations.isEmpty() ||
                !mMovesList.isEmpty() ||
                !mAdditionsList.isEmpty() ||
                !mChangesList.isEmpty()
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) dispatchAnimationsFinished()
    }

    override fun endAnimations() {
        for (i in mPendingMoves.indices.reversed()) {
            val item = mPendingMoves[i]
            val view = item.holder.itemView
            ViewCompat.setTranslationY(view, 0f)
            ViewCompat.setTranslationX(view, 0f)
            dispatchMoveFinished(item.holder)
            mPendingMoves.removeAt(i)
        }
        for (i in mPendingRemovals.indices.reversed()) {
            val item = mPendingRemovals[i]
            dispatchRemoveFinished(item)
            mPendingRemovals.removeAt(i)
        }
        for (i in mPendingAdditions.indices.reversed()) {
            val item = mPendingAdditions[i]
            val view = item.itemView
            resetView(view)
            dispatchAddFinished(item)
            mPendingAdditions.removeAt(i)
        }
        for (i in mPendingChanges.indices.reversed())
            endChangeAnimationIfNecessary(mPendingChanges[i])
        mPendingChanges.clear()
        if (!isRunning) return

        for (i in mMovesList.indices.reversed()) {
            val moves = mMovesList[i]
            for (j in moves.indices.reversed()) {
                val moveInfo = moves[j]
                val item = moveInfo.holder
                val view = item.itemView
                ViewCompat.setTranslationY(view, 0f)
                ViewCompat.setTranslationX(view, 0f)
                dispatchMoveFinished(moveInfo.holder)
                moves.removeAt(j)
                if (moves.isEmpty()) mMovesList.remove(moves)
            }
        }
        for (i in mAdditionsList.indices.reversed()) {
            val additions = mAdditionsList[i]
            for (j in additions.indices.reversed()) {
                val item = additions[j]
                resetView(item.itemView)
                dispatchAddFinished(item)
                additions.removeAt(j)
                if (additions.isEmpty()) mAdditionsList.remove(additions)
            }
        }
        for (i in mChangesList.indices.reversed()) {
            val changes = mChangesList[i]
            for (j in changes.indices.reversed()) {
                endChangeAnimationIfNecessary(changes[j])
                if (changes.isEmpty()) mChangesList.remove(changes)
            }
        }

        cancelAll(mRemoveAnimations)
        cancelAll(mMoveAnimations)
        cancelAll(mAddAnimations)
        cancelAll(mChangeAnimations)

        dispatchAnimationsFinished()
    }

    private fun cancelAll(viewHolders: List<RecyclerView.ViewHolder>) {
        for (i in viewHolders.indices.reversed()) {
            ViewCompat.animate(viewHolders[i].itemView).cancel()
        }
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder,
                                           payloads: List<Any>) =
            !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads)

    private open class VpaListenerAdapter : ViewPropertyAnimatorListener {
        override fun onAnimationStart(view: View) {}
        override fun onAnimationEnd(view: View) {}
        override fun onAnimationCancel(view: View) {}
    }
}
