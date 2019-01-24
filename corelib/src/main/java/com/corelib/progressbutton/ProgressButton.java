package com.corelib.progressbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corelib.R;

import androidx.core.content.ContextCompat;


/**
 * Created by Admin on 8/28/2017.
 */
public class ProgressButton {

    //================================================================================
    // Properties
    //================================================================================
    private static final long BUTTON_PROGRESS_TIME = 500;
    private final ProgressBar pBar;
    private final Button btnAction;
    private final ImageView ivStatus;
    private final Context context;
    private int mOriginalWidth, mOriginalHeight;
    private String text;

    //================================================================================
    // Constructors
    //================================================================================
    public interface Listener {
        void onAnimationCompleted();
    }

    public static ProgressButton newInstance(Activity activity) {
        return new ProgressButton(activity, activity.getWindow().getDecorView().getRootView());
    }

    public static ProgressButton newInstance(Context context, View v) {
        return new ProgressButton(context, v);
    }

    private ProgressButton(Context context, View view) {
        this.context = context;
        ivStatus = view.findViewById(R.id.iv_status);
        pBar = view.findViewById(R.id.progressBar);
        btnAction = view.findViewById(R.id.btn_action);
        btnAction.post(new Runnable() {
            @Override
            public void run() {
                mOriginalWidth = btnAction.getWidth();
                mOriginalHeight = btnAction.getHeight();
            }
        });
    }

    //================================================================================
    // Accessors
    //================================================================================
    public ProgressButton setOnClickListener(View.OnClickListener click) {
        if (btnAction != null) {
            btnAction.setOnClickListener(click);
        }
        return this;
    }


    public ProgressButton setOnEditorActionListener(EditText editText) {
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle pressing "Enter" key here
                    executeTask();
                    handled = true;
                }
                return handled;
            }
        });
        return this;
    }

    public ProgressButton setOnEditorActionListener(EditText editText, String btnLabel) {
        editText.setImeActionLabel(btnLabel, EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle pressing "Enter" key here
                    executeTask();
                    handled = true;
                }
                return handled;
            }
        });
        return this;
    }

    public ProgressButton setTransparentBackground(int textColor) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(getColorRes(context, R.color.colorTransparent));
            pBar.setIndeterminateDrawable(getDrawableRes(context, R.drawable.progress_custom_accent));
            ivStatus.setBackgroundResource(getColorRes(context, R.color.colorTransparent));
            ivStatus.setColorFilter(getColorRes(context, R.color.colorAccent));
            btnAction.setTextColor(textColor);
        }
        return this;
    }

    public ProgressButton setText(String text) {
        this.text = text;
        if (btnAction != null) {
            btnAction.setText(text != null ? text : "");
        }
        return this;
    }

    public String getText() {
        if (btnAction != null) {
            return btnAction.getText().toString();
        } else {
            return "";
        }
    }

    public ProgressButton setBackground(int drawable) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(drawable);
            ivStatus.setBackgroundResource(drawable);
            pBar.setBackgroundResource(drawable);
        }
        return this;
    }

    public ProgressButton setDisableButton() {
        if (btnAction != null) {
            return setBackground(R.drawable.progress_button_disable);
        }
        return this;
    }

    public ProgressButton setEnableButton() {
        if (btnAction != null) {
            return setBackground(R.drawable.progress_button);
        }
        return this;
    }


    //================================================================================
    // Action
    //================================================================================
    public void startProgress() {
        hideButtonAction();
    }

    public void revertSuccessProgress(final Listener listener) {
        Handler handler = new Handler();
        Runnable runnableRevert = new Runnable() {
            @Override
            public void run() {
                listener.onAnimationCompleted();
            }
        };
        revertSuccessProgress();
        handler.postDelayed(runnableRevert, BUTTON_PROGRESS_TIME);
    }

    public void revertProgress() {
        revertProgress(null);
    }
    public void revertProgress(String buttonText) {
        if(!TextUtils.isEmpty(buttonText)){
            setText(buttonText);
        }
        showButtonAction();
    }
    public void revertSuccessProgress() {
        revertSuccessProgress(false);
    }

    public void revertSuccessProgress(Boolean btnVisibility) {
        hideProgressBar();
        if (btnVisibility) {
            showButtonAction();
        } else {
            showSuccessView();
        }
    }


    public void performClick() {
        btnAction.performClick();
    }

    public void executeTask() {
        btnAction.performClick();
    }

    //================================================================================
    // Utility
    //================================================================================


    private void showSuccessView() {
        if (ivStatus != null) {
//            ivStatus.startAnimation(inAnim);
//            ivStatus.setVisibility(View.VISIBLE);
            startAlphaAnimation(ivStatus, View.VISIBLE);
        }
    }

    private void showProgressBar() {
        if (pBar != null) {
//            pBar.startAnimation(inAnim);
//            pBar.setVisibility(View.VISIBLE);
            startAlphaAnimation(pBar, View.VISIBLE); 
        }
    }

    private void hideProgressBar() {
        if (pBar != null) {
//            pBar.startAnimation(outAnim);
//            pBar.setVisibility(View.GONE);
            startAlphaAnimation(pBar, View.GONE);
        }
    }

    private void showButtonAction() {
        if (btnAction != null) {
            int fromWidth, toWidth;
            fromWidth = mOriginalHeight;
            toWidth = mOriginalWidth;

            ValueAnimator heightAnimation = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.height = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.width = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(
                    heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    hideProgressBar();
                    btnAction.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setText(text);
                    btnAction.setVisibility(View.VISIBLE);
//                    ivStatus.setVisibility(View.GONE);
                    startAlphaAnimation(ivStatus, View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            hideProgressBar();
            btnAction.setVisibility(View.VISIBLE);
//            ivStatus.setVisibility(View.GONE);
            startAlphaAnimation(ivStatus, View.GONE);

            widthAnimation.start();
        }
    }

    private void hideButtonAction() {

        if (btnAction != null) {
            int fromWidth, toWidth;
            fromWidth = mOriginalWidth;
            toWidth = mOriginalHeight;

            ValueAnimator heightAnimation = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.height = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.width = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(
                    heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    btnAction.setText("");
                    showProgressBar();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setVisibility(View.GONE);
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

    public ProgressButton setVisibility(int visibility) {
        if (btnAction != null) {
            btnAction.setVisibility(visibility);
        }
        return this;
    }


    private Drawable getDrawableRes(Context context, int resource) {
        return ContextCompat.getDrawable(context, resource);
    }

    private int getColorRes(Context context, int resource) {
        return ContextCompat.getColor(context, resource);
    }


    public static void startAlphaAnimation(View v, int visibility) {
        startAlphaAnimation(v, 10, visibility);
    }

    public static void startAlphaAnimation(final View v, long duration, final int visibility) {
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
}
