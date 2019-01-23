package com.corelib.global;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.corelib.R;
import com.corelib.util.ShareWere;
import com.corelib.util.Utility;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * @author Created by Abhijit Rao on 04/12/18.
 */
public class PopupAlert extends DialogFragment {
    public static final int ACTIVITY = 1, FRAGMENT = 2, POPUP_DIALOG = 3;
    private Dialog dialog;
    private String mTitle;
    private String mMessage;
    private int finishType, popupTimeOutInSec;
    private TextView tvDescription;
    private Context mContext;


    public PopupAlert setFinishType(int finishType) {
        this.finishType = finishType;
        return this;
    }

    public PopupAlert setTimeOutInSec(int popupTimeOutInSec) {
        this.popupTimeOutInSec = popupTimeOutInSec;
        return this;
    }

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
        mContext = getContext();
        if(getArguments()!=null) {
            mTitle = getArguments().getString("mTitle");
            mMessage = getArguments().getString("mMessage");
            finishType = getArguments().getInt("finishType");
            popupTimeOutInSec = getArguments().getInt("popupTimeOutInSec");
        }
    }

    public static PopupAlert newInstance(String title, String message) {
        PopupAlert mInstance = new PopupAlert();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("mTitle", title);
        args.putString("mMessage", message);
        args.putInt("finishType", POPUP_DIALOG);
        args.putInt("popupTimeOutInSec", 0);
        mInstance.setArguments(args);
        return mInstance;
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialogBuilder.setView(viewHolder(inflater.inflate(R.layout.frag_popup_alert, null)));
        dialog = dialogBuilder.create();
        dialog.show();
        return dialog;
    }

    private View viewHolder(View view) {
        Button btnOk =   view.findViewById(R.id.btn_popup);
        TextView tvTitle =  view.findViewById(R.id.tv_popup_title);
        tvDescription =  view.findViewById(R.id.tv_popup_description);

        tvTitle.setText(mTitle);
        tvDescription.setText(mMessage);
        btnOk.setText(getString(R.string.ok));
        tvDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShareWere.copyText(mContext, tvDescription.getText().toString());
                Utility.toast(getContext(), "Text Copied.");
                return false;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish(finishType);
            }
        });

        if (popupTimeOutInSec != 0) {
            startTimerForMainActivity();
        }

        return view;
    }

    private void finish(int finishType) {
        if (finishType == ACTIVITY && getActivity() != null) {
            getActivity().finish();
        } else if (finishType == FRAGMENT && getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }

    private void startTimerForMainActivity() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (dialog != null) {
                    if (finishType == ACTIVITY && getActivity() != null) {
                        getActivity().finish();
                    } else if (finishType == POPUP_DIALOG) {
                        dialog.dismiss();
                    } else {
                        if (getFragmentManager() != null)
                            getFragmentManager().popBackStack();
                    }
                }
            }
        }, (popupTimeOutInSec * 1000));
    }

}
