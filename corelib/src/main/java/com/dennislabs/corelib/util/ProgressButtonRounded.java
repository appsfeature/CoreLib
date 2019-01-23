package com.dennislabs.corelib.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dennislabs.corelib.R;


/**
 * Created by Admin on 8/28/2017.
 */
public class ProgressButtonRounded {

    private final ProgressBar pBar;
    private final Button btnAction;
    private final Animation inAnim;
    private final ImageView ivStatus;
    private final Context context;


    public interface ProgressButton{
        void onAnimationCompleted();
    }

    public static ProgressButtonRounded newInstance(Context context, View v) {
        return new ProgressButtonRounded(context, v);
    }

    private ProgressButtonRounded(Context context, View view) {
        this.context = context;
        ivStatus =  view.findViewById(R.id.iv_status);
        pBar =  view.findViewById(R.id.progressBar);
        btnAction =  view.findViewById(R.id.btn_action);
        inAnim = AnimationUtils.loadAnimation(context,
                android.R.anim.fade_in);
        Animation outAnim = AnimationUtils.loadAnimation(context,
                android.R.anim.fade_out);
    }


    public ProgressButtonRounded setOnClickListener(View.OnClickListener click) {
        if (btnAction != null) {
            btnAction.setOnClickListener(click);
        }
        return this;
    }



    public ProgressButtonRounded setOnEditorActionListener(EditText editText) {
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
    public ProgressButtonRounded setOnEditorActionListener(EditText editText, String btnLabel) {
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

    public void startAnimation() {
        hideButtonAction();

    }

    public void animateButtonAndRevert(final ProgressButton listener) {
        Handler handler = new Handler();
        Runnable runnableRevert = new Runnable() {
            @Override
            public void run() {
                listener.onAnimationCompleted();
            }
        };
        revertSuccessAnimation();
        handler.postDelayed(runnableRevert, CoreLibConst.BUTTON_PROGRESS_TIME);
    }

    public void revertAnimation() {
        showButtonAction();
    }

    public void revertSuccessAnimation() {
        revertSuccessAnimation(false);
    }

    public void revertSuccessAnimation(Boolean btnVisibility) {
        hideProgressBar();
        if(btnVisibility) {
            showButtonAction();
        }else{
            showSuccessView();
        }
    }

    private void showSuccessView() {
        if(ivStatus!=null) {
            ivStatus.startAnimation(inAnim);
            ivStatus.setVisibility(View.VISIBLE);
        }
    }

    private void showButtonAction() {
        if (btnAction != null) {
            ObjectAnimator transAnimation2= ObjectAnimator.ofFloat(btnAction, "ScaleX", 0, 1);
            transAnimation2.setDuration(500);//set duration
            final Animator.AnimatorListener showListener=new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    hideProgressBar();
                    btnAction.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setVisibility(View.VISIBLE);
                    ivStatus.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            };
            hideProgressBar();
            btnAction.setVisibility(View.VISIBLE);
            ivStatus.setVisibility(View.GONE);
            transAnimation2.addListener(showListener);
            transAnimation2.start();//start animation
        }
    }

    private void hideButtonAction() {

        if (btnAction != null) {
            final Animator.AnimatorListener hideButtonListener=new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
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
            };

            ObjectAnimator transAnimation1= ObjectAnimator.ofFloat(btnAction, "ScaleX", 1, 0);
            transAnimation1.setDuration(500);//set duration

            transAnimation1.addListener(hideButtonListener);

            transAnimation1.start();//start animation
        }
    }

    private void showProgressBar() {
        if (pBar != null) {
//            pBar.startAnimation(inAnim);
            pBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (pBar != null) {
//            pBar.startAnimation(outAnim);
            pBar.setVisibility(View.GONE);
        }
    }

    public ProgressButtonRounded setVisibility(int visibility) {
        if (btnAction != null) {
            btnAction.setVisibility(visibility);
        }
        return this;
    }

    public ProgressButtonRounded setTransparentBackground(int textColor) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(Utility.getColor(context,R.color.colorTransparent));
            pBar.setIndeterminateDrawable(Utility.getDrawable(context,R.drawable.progress_custom_accent));
            ivStatus.setBackgroundResource(Utility.getColor(context,R.color.colorTransparent));
            ivStatus.setColorFilter(Utility.getColor(context,R.color.colorAccent));
            btnAction.setTextColor(textColor);
        }
        return this;
    }

     public ProgressButtonRounded setText(String text) {
        if (btnAction != null) {
            btnAction.setText(text!=null?text:"");
        }
        return this;
    }

    public String getText() {
        if (btnAction != null) {
            return btnAction.getText().toString();
        }else {
            return "";
        }
    }

    public void disableButton() {
        if (btnAction != null) {
            btnAction.setBackgroundResource(R.drawable.bg_button_design_disable);
        }
    }
    public void enableButton() {
        if (btnAction != null) {
            btnAction.setBackgroundResource(R.drawable.bg_button_design);
        }
    }

    public void executeTask() {
        btnAction.performClick();
    }


}
