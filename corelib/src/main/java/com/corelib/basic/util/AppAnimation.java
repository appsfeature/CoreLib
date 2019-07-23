package com.corelib.basic.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.transition.ChangeBounds;

/**
 * @author Created by Abhijit on 22-Dec-16.
 */
public class AppAnimation {

    public interface Listener {
        void onAnimationEnd();
    }

    private static final int ZOOM_IN = 1, ZOOM_IN_BOUNCE = 2, FLIP_HORIZONTAL = 3, FLIP_VERTICAL = 4, HIDE_FLIP_HORIZONTAL = 5, EXPEND = 6, COLLAPSE = 7;
    private static final long ANIM_DURATION_MEDIUM = 500;
    private static final long ANIM_DURATION_LONG = 500;


    private void setProgressBarAnim(ProgressBar pb, int max, int progressTo) {
        pb.setMax(max * 100);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    class ProgressBarAnim extends Animation {
        /* usage
        ProgressBarAnim anim = new ProgressBarAnim(progress, from, to);
        anim.setDuration(1000);
        progress.startAnimation(anim);
         */

        private final ProgressBar progressBar;
        private final float from;
        private final float to;

        public ProgressBarAnim(ProgressBar progressBar, int value) {
            this.progressBar = progressBar;
            this.from = (float) 0;
            this.to = (float) value;
        }

        public ProgressBarAnim(ProgressBar progressBar, int from, int to) {
            super();
            this.progressBar = progressBar;
            this.from = (float) from;
            this.to = (float) to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }

    // zoom in out button(circle) animation
    public static void zoomInOutForButton(final View v) {
        Animation anim = new ScaleAnimation(1f, 0.70f, 1f, 0.70f, 50, 50); // Pivot point of Y scaling
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(300);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(0.70f, 1f, 0.70f, 1f, 50, 50); // Pivot point of Y scaling
                anim.setInterpolator(new AccelerateInterpolator());
                anim.setDuration(100);
                v.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }

    // zoom in out button(rectangle) animation
    public static void zoomInOutForRectButton(final View view, final Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();
        ObjectAnimator scaleXAnimator, scaleYAnimator;
        scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 1f, 0.90f, 1f);
        animatorList.add(scaleXAnimator);
        scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 1f, 0.90f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        view.setVisibility(View.VISIBLE);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    // zoom in out button(rectangle) animation
    public static void zoomInForButton(final View view, final Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();
        ObjectAnimator scaleXAnimator, scaleYAnimator;
        scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 1f, 0f);
        animatorList.add(scaleXAnimator);
        scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 1f, 0f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        view.setVisibility(View.VISIBLE);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    // zoom in out button animation
    public static void zoomOutForButton(final View view, final Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();
        ObjectAnimator scaleXAnimator, scaleYAnimator;
        scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1f);
        animatorList.add(scaleXAnimator);
        scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        view.setVisibility(View.VISIBLE);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    public static void setDrawerAnimation(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide(Gravity.END);
            slideTransition.setDuration(ANIM_DURATION_MEDIUM);
            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(ANIM_DURATION_MEDIUM);
            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void setDialogAnimation(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setStartDelay(50);
//            ChangeBounds changeBoundsTransition = new ChangeBounds();
//            changeBoundsTransition.setDuration(ANIM_DURATION_MEDIUM);
            fragment.setEnterTransition(fade);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
//            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void setExplodeAnimation(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode enterTransition = new Explode();
            enterTransition.setDuration(ANIM_DURATION_LONG);
            fragment.setEnterTransition(enterTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
        }

    }


    public static ObjectAnimator setViewAnimation(final View view, int type) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();
        ObjectAnimator scaleXAnimator, scaleYAnimator = null;
        switch (type) {
            case ZOOM_IN:
                scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1f, 1f);
                animatorList.add(scaleXAnimator);
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
                animatorList.add(scaleYAnimator);
                animatorSet.playTogether(animatorList);
                break;
            case ZOOM_IN_BOUNCE:
                scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1.2f, 1f);
                animatorList.add(scaleXAnimator);
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1.2f, 1f);
                animatorList.add(scaleYAnimator);
                animatorSet.playTogether(animatorList);
                break;
            case FLIP_VERTICAL:
                scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1.2f, 1f);
                animatorSet.playTogether(scaleXAnimator);
                break;
            case FLIP_HORIZONTAL:
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
                animatorSet.playTogether(scaleYAnimator);
//                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
//                animatorSet.playTogether(scaleYAnimator);
                break;
            case HIDE_FLIP_HORIZONTAL:
                scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 1f, 0f, 0f);
                scaleYAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animatorSet.playTogether(scaleYAnimator);
                break;
        }
        view.setVisibility(View.VISIBLE);

        animatorSet.start();
        return scaleYAnimator;
    }

    public static ObjectAnimator slideDown(final View view) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator scaleYAnimator;
        scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0f, 1f, 1f);
        animatorSet.playTogether(scaleYAnimator);
        view.setVisibility(View.VISIBLE);
        animatorSet.start();
        return scaleYAnimator;
    }


    public static ObjectAnimator slideUp(final View view) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator scaleYAnimator;
        scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 1f, 0f, 0f);
        scaleYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.playTogether(scaleYAnimator);
        animatorSet.start();
        return scaleYAnimator;
    }


    public static void setSlideAnimation(Fragment fragment, int gravity) {
        setSlideAnimation(fragment, gravity, null);
    }

    private static void setSlideAnimation(Fragment fragment, int gravity, final Listener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide(gravity);
            slideTransition.setDuration(ANIM_DURATION_MEDIUM);
            slideTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (listener != null) {
                        listener.onAnimationEnd();
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
//            ChangeBounds changeBoundsTransition = new ChangeBounds();
//            changeBoundsTransition.setDuration(ANIM_DURATION_MEDIUM);
            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
//            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void setFadeAnimation(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(ANIM_DURATION_MEDIUM);
            fragment.setEnterTransition(fade);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
        }
    }

    public static void setupWindowAnimations(Activity activity) {
        Visibility enterTransition = buildEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && enterTransition != null) {
            activity.getWindow().setEnterTransition(enterTransition);
        }
    }

    public static void setupWindowExplodeAnimations(Activity activity) {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = buildEnterExplodeTransition();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transition != null) {
            activity.getWindow().setEnterTransition(transition);
        }
    }

    private static Transition buildEnterExplodeTransition() {
        Explode enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Explode();
            enterTransition.setDuration(ANIM_DURATION_LONG);
        }
        return enterTransition;
    }

    private static Visibility buildEnterTransition() {
        Fade enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            enterTransition = new Fade();
            enterTransition.setDuration(ANIM_DURATION_LONG);
            // This view will not be affected by enter transition animation
//            enterTransition.excludeTarget(R.id.square_red, true);
//            enterTransition.excludeTarget(nextScreenViewId, true);
        }
        return enterTransition;
    }

    private static Visibility buildReturnTransition() {
        Visibility enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Slide();
            enterTransition.setDuration(ANIM_DURATION_LONG);
        }
        return enterTransition;
    }

//    public static void expandOrCollapse(final View v, int exp_or_collapse) {
//        expandOrCollapse(v, exp_or_collapse, null);
//    }


    public static void expandOrCollapse(final View v, int exp_or_collapse, Animation.AnimationListener collapseListener) {
        TranslateAnimation anim = null;
        if (exp_or_collapse == EXPEND) {
            anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), v.getHeight());
            v.setVisibility(View.VISIBLE);
            anim.setAnimationListener(null);

        } else if (exp_or_collapse == COLLAPSE) {
            anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, v.getHeight());
            anim.setAnimationListener(collapseListener);
        }
        // To Collapse
        //
        if (anim != null) {
            anim.setDuration(500);
            anim.setInterpolator(new AccelerateInterpolator());
            v.startAnimation(anim);
        }
    }

    public static void fadeInAnimation(final View view, long animationDuration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(animationDuration);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(fadeIn);
    }

    public static void fadeOutAnimation(final View view, long animationDuration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(animationDuration);
        fadeOut.setDuration(animationDuration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeOut);
    }

    public static void startAlphaAnimation(View v, int visibility) {
        startAlphaAnimation(v, 100, visibility);
    }

    public static void startAlphaAnimation(View v, int visibility, Listener listener) {
        startAlphaAnimation(v, 100, visibility, listener);
    }

    public static void startAlphaAnimation(final View v, long duration, final int visibility) {
        startAlphaAnimation(v,duration,visibility,null);
    }
    public static void startAlphaAnimation(final View v, long duration, final int visibility, final Listener listener) {
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
                if(listener!=null){
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void showViewAction(final View view,int fromWidth, int toWidth, int fromHeight, int toHeight) {
        if (view != null) {

            ValueAnimator heightAnimation = ValueAnimator.ofInt(fromHeight, toHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = val;
                    view.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = val;
                    view.setLayoutParams(layoutParams);
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            view.setVisibility(View.VISIBLE);
            widthAnimation.start();
        }
    }

    private void hideViewAction(final View view, int fromWidth, int toWidth, int fromHeight, int toHeight) {

        if (view != null) {
            ValueAnimator heightAnimation = ValueAnimator.ofInt(fromHeight, toHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = val;
                    view.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = val;
                    view.setLayoutParams(layoutParams);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(
                    heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            widthAnimation.start();
        }
    }
}
