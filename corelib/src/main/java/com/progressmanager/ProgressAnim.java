package com.progressmanager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

class ProgressAnim {

    public interface AnimListener {
        void onAnimationEnd();
    }

    public static void startAlphaAnimation(View v, int visibility) {
        startAlphaAnimation(v, 400, visibility);
    }

    private static void startAlphaAnimation(final View v, long duration, final int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visibility == View.VISIBLE) {
                    v.setVisibility(visibility);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(visibility);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public static void slideDownAlpha(final View view) {
        startAlphaAnimation(view,View.VISIBLE);
    }


    public static void slideUp(final View view, final AnimListener animListener) {
        int height = view.getHeight();
        ValueAnimator heightAnimation = ValueAnimator.ofInt(height, 0);
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                int val = (Integer) updatedAnimation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }
        });
        heightAnimation.setDuration(400);
        heightAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                animListener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        heightAnimation.start();
    }

}
