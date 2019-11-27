package com.corelib.basic.util;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AnimationUtils {

    public static void expandView(View view, boolean isExpend, AnimationEndCallback callback) {
        expandView(view, isExpend, false, callback);
    }
    public static void expandView(View view, boolean isExpend, boolean fast, AnimationEndCallback callback) {
        if (isExpend) {
            AnimationUtils.expand(view, callback, fast);
        } else if (view.getHeight() != 0) {
//            AnimationUtils.collapse(view, callback, fast);
        }
    }

    public static void expand(final View v, final AnimationEndCallback callback, boolean fast) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = targetHeight;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : targetHeight + (int) (targetHeight * interpolatedTime);
                Log.d("@test","targetHeight:"+targetHeight +" * interpolatedTime:"+interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return false;
            }
        };

        int duration = 0;

        if (!fast) {
            duration = (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density);
        }

        a.setDuration(duration);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callback != null)
                    callback.onAnimationEnded();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public static void collapse(final View v, final AnimationEndCallback callback, boolean fast) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = targetHeight;
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if (interpolatedTime == 1) {
////                    v.setVisibility(View.GONE);
                    v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    v.requestLayout();
//                } else {
//                    v.getLayoutParams().height = targetHeight - (int) (targetHeight * interpolatedTime);
//                    v.requestLayout();
//                }
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : targetHeight + (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        int duration = 0;
        if (!fast) {
            duration = (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density);
        }

        a.setDuration(duration);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (callback != null)
                    callback.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callback != null)
                    callback.onAnimationEnded();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public interface AnimationEndCallback {
        void onAnimationStart();
        void onAnimationEnded();
    }
}