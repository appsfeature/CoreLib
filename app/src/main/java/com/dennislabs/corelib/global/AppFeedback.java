package com.dennislabs.corelib.global;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dennislabs.corelib.R;
import com.dennislabs.corelib.util.AppAnimation;
import com.dennislabs.corelib.util.FieldValidation;
import com.dennislabs.corelib.util.ProgressButtonRounded;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


/**
 * Created by Abhijit on 14-Oct-16.
 */
@SuppressWarnings("all")
public class AppFeedback extends CompactFragment {
    private FragmentManager manager;
    private FragmentActivity mContext;
    private FeedbackListener listener;
    private ProgressButtonRounded btnAction;

    public interface FeedbackListener{
        void makeFeedbackRequest(AppFeedback appFeedback, String feedbackDetail);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    public static AppFeedback newInstance(FeedbackListener listener) {
        AppFeedback appFeedback = new AppFeedback();
        appFeedback.listener=listener;
        AppAnimation.setDialogAnimation(appFeedback);
        return appFeedback;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_app_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = getFragmentManager();
        initUI(view);
    }


    private void initUI(View v) {
        ImageButton ibClose = v.findViewById(R.id.ib_close);
        final EditText etFeedback = v.findViewById(R.id.et_alert_feedback);

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(mContext);
                manager.popBackStack();
            }
        });
        btnAction = ProgressButtonRounded.newInstance(getContext(), v)
                .setText(getString(R.string.send_feedback))
                .setOnEditorActionListener(etFeedback, getString(R.string.send))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!FieldValidation.isEmpty(getContext(), etFeedback)) {
                            return;
                        }
                        hideKeyboard(mContext);
                        sendFeedback(etFeedback.getText().toString());
                    }
                });
    }

    public void revertButtonFailureAnimation() {
        btnAction.revertAnimation();
    }

    public void revertButtonSuccessAnimation(ProgressButtonRounded.ProgressButton listener) {
        btnAction.animateButtonAndRevert(listener);
    }

    private void sendFeedback(String feedback) {
        if(listener!=null){
            listener.makeFeedbackRequest(AppFeedback.this,feedback);
        }
    }

}