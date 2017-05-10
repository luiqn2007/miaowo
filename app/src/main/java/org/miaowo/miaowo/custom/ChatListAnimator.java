package org.miaowo.miaowo.custom;

import android.support.annotation.NonNull;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import org.miaowo.miaowo.adapter.ChatMsgAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天消息列表动画
 * Created by luqin on 17-4-26.
 */

public class ChatListAnimator extends SimpleItemAnimator {
    // 本次将执行的动画列表
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();
    // 动画栈
    private ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    private ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    private ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();
    // 正在执行的动画列表
    private ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();
    // 视图移动动画信息
    private static class MoveInfo {
        RecyclerView.ViewHolder holder;
        int fromX, fromY, toX, toY;

        MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        int getDeltaX() {
            return toX - fromX;
        }
        int getDeltaY() {
            return toY - fromY;
        }
    }
    // 视图改变动画信息
    private static class ChangeInfo {
        RecyclerView.ViewHolder oldHolder, newHolder;
        int fromX, fromY, toX, toY;
        ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder,
                   int fromX, int fromY, int toX, int toY) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        int getDeltaX() {
            return toX - fromX;
        }
        int getDeltaY() {
            return toY - fromY;
        }

        boolean hasOldHolder() {
            return oldHolder != null;
        }
        boolean hasNewHolder() {
            return newHolder != null;
        }
        boolean noHolder() {
            return oldHolder == null && newHolder == null;
        }
    }

    @Override
    public void runPendingAnimations() {
        boolean removalsPending = !mPendingRemovals.isEmpty();
        boolean movesPending = !mPendingMoves.isEmpty();
        boolean changesPending = !mPendingChanges.isEmpty();
        boolean additionsPending = !mPendingAdditions.isEmpty();
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) return;
        // remove
        mPendingRemovals.forEach(this::animateRemoveImpl);
        mPendingRemovals.clear();
        // move
        if (movesPending) {
            final ArrayList<MoveInfo> moves = new ArrayList<>();
            moves.addAll(mPendingMoves);
            mMovesList.add(moves);
            mPendingMoves.clear();
            Runnable mover = () -> {
                moves.forEach(this::animateMoveImpl);
                moves.clear();
                mMovesList.remove(moves);
            };
            if (removalsPending) {
                View view = moves.get(0).holder.itemView;
                ViewCompat.postOnAnimationDelayed(view, mover, getRemoveDuration());
            } else mover.run();
        }
        // change
        if (changesPending) {
            final ArrayList<ChangeInfo> changes = new ArrayList<>();
            changes.addAll(mPendingChanges);
            mChangesList.add(changes);
            mPendingChanges.clear();
            Runnable changer = () -> {
                changes.forEach(this::animateChangeImpl);
                changes.clear();
                mChangesList.remove(changes);
            };
            if (removalsPending) {
                RecyclerView.ViewHolder holder = changes.get(0).oldHolder;
                ViewCompat.postOnAnimationDelayed(holder.itemView, changer, getRemoveDuration());
            } else changer.run();
        }
        // add
        if (additionsPending) {
            final ArrayList<RecyclerView.ViewHolder> additions = new ArrayList<>();
            additions.addAll(mPendingAdditions);
            mAdditionsList.add(additions);
            mPendingAdditions.clear();
            Runnable adder = () -> {
                additions.forEach(this::animateAddImpl);
                additions.clear();
                mAdditionsList.remove(additions);
            };
            if (removalsPending || movesPending || changesPending) {
                long removeDuration = removalsPending ? getRemoveDuration() : 0;
                long moveDuration = movesPending ? getMoveDuration() : 0;
                long changeDuration = changesPending ? getChangeDuration() : 0;
                long totalDelay = removeDuration + Math.max(moveDuration, changeDuration);
                View view = additions.get(0).itemView;
                ViewCompat.postOnAnimationDelayed(view, adder, totalDelay);
            } else adder.run();
        }
    }

    @Override
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        mPendingRemovals.add(holder);
        return true;
    }
    private void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration())
                .alpha(0).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                dispatchRemoveStarting(holder);
            }

            @Override
            public void onAnimationEnd(View view) {
                animation.setListener(null);
                resetView(view);
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
    }

    @Override
    public boolean animateAdd(final RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        ViewCompat.setAlpha(holder.itemView, 0);
        ViewCompat.setTranslationX(holder.itemView, holder.getItemViewType() == ChatMsgAdapter.MY ? 320 : -320);
        ViewCompat.setTranslationY(holder.itemView, 320);
        mPendingAdditions.add(holder);
        return true;
    }
    private void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mAddAnimations.add(holder);
        animation.alpha(1).translationX(0).translationY(0).setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        dispatchAddStarting(holder);
                    }
                    @Override
                    public void onAnimationCancel(View view) {
                        resetView(view);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        animation.setListener(null);
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

    @Override
    public boolean animateMove(final RecyclerView.ViewHolder holder, int fromX, int fromY,
                               int toX, int toY) {
        final View view = holder.itemView;
        fromX += ViewCompat.getTranslationX(holder.itemView);
        fromY += ViewCompat.getTranslationY(holder.itemView);
        resetAnimation(holder);
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) ViewCompat.setTranslationX(view, -deltaX);
        if (deltaY != 0) ViewCompat.setTranslationY(view, -deltaY);
        mPendingMoves.add(new MoveInfo(holder, fromX, fromY, toX, toY));
        return true;
    }
    private void animateMoveImpl(final MoveInfo info) {
        final View view = info.holder.itemView;
        final int deltaX = info.getDeltaX();
        final int deltaY = info.getDeltaY();
        if (deltaX != 0) ViewCompat.animate(view).translationX(0);
        if (deltaY != 0) ViewCompat.animate(view).translationY(0);
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mMoveAnimations.add(info.holder);
        animation.setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                dispatchMoveStarting(info.holder);
            }
            @Override
            public void onAnimationCancel(View view) {
                if (deltaX != 0) ViewCompat.setTranslationX(view, 0);
                if (deltaY != 0) ViewCompat.setTranslationY(view, 0);
            }
            @Override
            public void onAnimationEnd(View view) {
                animation.setListener(null);
                dispatchMoveFinished(info.holder);
                mMoveAnimations.remove(info.holder);
                dispatchFinishedWhenDone();
            }
        }).start();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder,
                                 int fromX, int fromY, int toX, int toY) {
        if (oldHolder == newHolder) return animateMove(oldHolder, fromX, fromY, toX, toY);

        final float prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView);
        final float prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView);
        final float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);
        resetAnimation(oldHolder);
        int deltaX = (int) (toX - fromX - prevTranslationX);
        int deltaY = (int) (toY - fromY - prevTranslationY);
        // recover prev translation state after ending animation
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX);
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY);
        ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);
        if (newHolder != null) {
            // carry over translation values
            resetAnimation(newHolder);
            ViewCompat.setTranslationX(newHolder.itemView, -deltaX);
            ViewCompat.setTranslationY(newHolder.itemView, -deltaY);
            ViewCompat.setAlpha(newHolder.itemView, 0);
        }
        mPendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }
    private void animateChangeImpl(final ChangeInfo changeInfo) {
        if (changeInfo.hasOldHolder()) {
            final View view = changeInfo.oldHolder.itemView;
            final ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view).setDuration(
                    getChangeDuration());
            mChangeAnimations.add(changeInfo.oldHolder);
            oldViewAnim.translationX(changeInfo.getDeltaX());
            oldViewAnim.translationY(changeInfo.getDeltaY());
            oldViewAnim.alpha(0).setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                @Override
                public void onAnimationEnd(View view) {
                    oldViewAnim.setListener(null);
                    resetView(view);
                    dispatchChangeFinished(changeInfo.oldHolder, true);
                    mChangeAnimations.remove(changeInfo.oldHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
        if (changeInfo.hasNewHolder()) {
            final View newView = changeInfo.newHolder.itemView;
            final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
            mChangeAnimations.add(changeInfo.newHolder);
            newViewAnimation.translationX(0).translationY(0).setDuration(getChangeDuration()).
                    alpha(1).setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchChangeStarting(changeInfo.newHolder, false);
                }
                @Override
                public void onAnimationEnd(View view) {
                    newViewAnimation.setListener(null);
                    resetView(newView);
                    dispatchChangeFinished(changeInfo.newHolder, false);
                    mChangeAnimations.remove(changeInfo.newHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> infoList, RecyclerView.ViewHolder item) {
        for (int i = infoList.size() - 1; i >= 0; i--) {
            ChangeInfo changeInfo = infoList.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item))
                if (changeInfo.noHolder()) infoList.remove(changeInfo);
        }
    }
    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.hasOldHolder()) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.hasNewHolder()) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }
    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item)
            changeInfo.newHolder = null;
        else if (changeInfo.oldHolder == item) {
            changeInfo.oldHolder = null;
            oldItem = true;
        } else return false;
        resetView(item.itemView);
        dispatchChangeFinished(item, oldItem);
        return true;
    }
    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        final View view = item.itemView;
        // this will trigger end callback which should set properties to their target values.
        ViewCompat.animate(view).cancel();
        for (int i = mPendingMoves.size() - 1; i >= 0; i--) {
            MoveInfo moveInfo = mPendingMoves.get(i);
            if (moveInfo.holder == item) {
                resetView(view);
                dispatchMoveFinished(item);
                mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(mPendingChanges, item);
        if (mPendingRemovals.remove(item)) {
            resetView(view);
            dispatchRemoveFinished(item);
        }
        if (mPendingAdditions.remove(item)) {
            resetView(view);
            dispatchAddFinished(item);
        }

        for (int i = mChangesList.size() - 1; i >= 0; i--) {
            ArrayList<ChangeInfo> changes = mChangesList.get(i);
            endChangeAnimation(changes, item);
            if (changes.isEmpty()) mChangesList.remove(i);
        }
        for (int i = mMovesList.size() - 1; i >= 0; i--) {
            ArrayList<MoveInfo> moves = mMovesList.get(i);
            for (int j = moves.size() - 1; j >= 0; j--) {
                MoveInfo moveInfo = moves.get(j);
                if (moveInfo.holder == item) {
                    ViewCompat.setTranslationY(view, 0);
                    ViewCompat.setTranslationX(view, 0);
                    dispatchMoveFinished(item);
                    moves.remove(j);
                    if (moves.isEmpty()) mMovesList.remove(i);
                    break;
                }
            }
        }
        for (int i = mAdditionsList.size() - 1; i >= 0; i--) {
            ArrayList<RecyclerView.ViewHolder> additions = mAdditionsList.get(i);
            if (additions.remove(item)) {
                resetView(view);
                dispatchAddFinished(item);
                if (additions.isEmpty()) mAdditionsList.remove(i);
            }
        }

    }

    private void resetAnimation(RecyclerView.ViewHolder holder) {
        AnimatorCompatHelper.clearInterpolator(holder.itemView);
        endAnimation(holder);
    }

    private void resetView(View view) {
        ViewCompat.setAlpha(view, 1);
        ViewCompat.setTranslationX(view, 0);
        ViewCompat.setTranslationY(view, 0);
    }

    @Override
    public boolean isRunning() {
        return (!mPendingAdditions.isEmpty() ||
                !mPendingChanges.isEmpty() ||
                !mPendingMoves.isEmpty() ||
                !mPendingRemovals.isEmpty() ||
                !mMoveAnimations.isEmpty() ||
                !mRemoveAnimations.isEmpty() ||
                !mAddAnimations.isEmpty() ||
                !mChangeAnimations.isEmpty() ||
                !mMovesList.isEmpty() ||
                !mAdditionsList.isEmpty() ||
                !mChangesList.isEmpty());
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    @Override
    public void endAnimations() {
        for (int i = mPendingMoves.size() - 1; i >= 0; i--) {
            MoveInfo item = mPendingMoves.get(i);
            View view = item.holder.itemView;
            ViewCompat.setTranslationY(view, 0);
            ViewCompat.setTranslationX(view, 0);
            dispatchMoveFinished(item.holder);
            mPendingMoves.remove(i);
        }
        for (int i = mPendingRemovals.size() - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mPendingRemovals.get(i);
            dispatchRemoveFinished(item);
            mPendingRemovals.remove(i);
        }
        for (int i = mPendingAdditions.size() - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mPendingAdditions.get(i);
            View view = item.itemView;
            resetView(view);
            dispatchAddFinished(item);
            mPendingAdditions.remove(i);
        }
        for (int i = mPendingChanges.size() - 1; i >= 0; i--)
            endChangeAnimationIfNecessary(mPendingChanges.get(i));
        mPendingChanges.clear();
        if (!isRunning()) return;

        for (int i = mMovesList.size() - 1; i >= 0; i--) {
            ArrayList<MoveInfo> moves = mMovesList.get(i);
            for (int j = moves.size() - 1; j >= 0; j--) {
                MoveInfo moveInfo = moves.get(j);
                RecyclerView.ViewHolder item = moveInfo.holder;
                View view = item.itemView;
                ViewCompat.setTranslationY(view, 0);
                ViewCompat.setTranslationX(view, 0);
                dispatchMoveFinished(moveInfo.holder);
                moves.remove(j);
                if (moves.isEmpty()) mMovesList.remove(moves);
            }
        }
        for (int i = mAdditionsList.size() - 1; i >= 0; i--) {
            ArrayList<RecyclerView.ViewHolder> additions = mAdditionsList.get(i);
            for (int j = additions.size() - 1; j >= 0; j--) {
                RecyclerView.ViewHolder item = additions.get(j);
                resetView(item.itemView);
                dispatchAddFinished(item);
                additions.remove(j);
                if (additions.isEmpty()) mAdditionsList.remove(additions);
            }
        }
        for (int i = mChangesList.size() - 1; i >= 0; i--) {
            ArrayList<ChangeInfo> changes = mChangesList.get(i);
            for (int j = changes.size() - 1; j >= 0; j--) {
                endChangeAnimationIfNecessary(changes.get(j));
                if (changes.isEmpty()) mChangesList.remove(changes);
            }
        }

        cancelAll(mRemoveAnimations);
        cancelAll(mMoveAnimations);
        cancelAll(mAddAnimations);
        cancelAll(mChangeAnimations);

        dispatchAnimationsFinished();
    }

    private void cancelAll(List<RecyclerView.ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ViewCompat.animate(viewHolders.get(i).itemView).cancel();
        }
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,
                                             @NonNull List<Object> payloads) {
        return !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads);
    }

    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        VpaListenerAdapter() {}
        @Override
        public void onAnimationStart(View view) {}
        @Override
        public void onAnimationEnd(View view) {}
        @Override
        public void onAnimationCancel(View view) {}
    }
}
