package org.miaowo.miaowo.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.view.ViewGroup;

public class MoveTransition extends Transition {
    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        transitionValues.values.put("x", transitionValues.view.getTranslationX());
        transitionValues.values.put("y", transitionValues.view.getTranslationY());
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        transitionValues.values.put("x", transitionValues.view.getTranslationX());
        transitionValues.values.put("y", transitionValues.view.getTranslationY());
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) return null;
        float startX, startY, endX, endY;
        startX = startValues.values.get("x") == null ? 0 : (float) startValues.values.get("x");
        endX = endValues.values.get("x") == null ? 0 : (float) endValues.values.get("x");
        startY = startValues.values.get("y") == null ? 0 : (float) startValues.values.get("y");
        endY = endValues.values.get("y") == null ? 0 : (float) endValues.values.get("y");
        return ObjectAnimator.ofPropertyValuesHolder(startValues.view,
                PropertyValuesHolder.ofFloat("translationX", startX, endX),
                PropertyValuesHolder.ofFloat("translationY", startY, endY));
    }
}
