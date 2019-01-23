package com.corelib.global;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelib.R;
import com.corelib.util.CoreLibConst;
import com.corelib.util.ShareWere;
import com.corelib.util.Utility;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * @author Created by Abhijit Rao on 04/12/18.
 */
public class PopupDialog extends DialogFragment {
    private TextView tvDescription;
    private Dialog dialog;
    private String mTitle;
    private String mMessage;
    private DialogListener mListener;
    private int task;
    private Context mContext;
    private String errorMessage;

    public interface DialogListener {
        void onPositiveClick(DialogFragment dialog);

        void onNegativeClick(DialogFragment dialog);
    }

    public void onCreate(Bundle state) {
        super.onCreate(state);
        mContext = getContext();
        setRetainInstance(true);
    }

//    public void show(FragmentManager fragmentManager) {
//        if (fragmentManager != null)
//            mInstance.show(fragmentManager, "popupDialog");
//    }



    public static PopupDialog newInstance(String errorMessage, DialogListener listener) {
        PopupDialog mInstance = new PopupDialog();
        mInstance.errorMessage = errorMessage;
        mInstance.mListener = listener;
        return mInstance;
    }
    public static PopupDialog newInstance(String title, String message, DialogListener listener) {
        PopupDialog mInstance = new PopupDialog();
        mInstance.mTitle = title;
        mInstance.mMessage = message;
        mInstance.mListener = listener;
        return mInstance;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialogBuilder.setView(viewHolder(inflater.inflate(R.layout.alert_dialog, null)));
        dialog = dialogBuilder.create();
        dialog.show();

        return dialog;
    }

    private View viewHolder(View view) {
        ImageView ivAbout =  view.findViewById(R.id.iv_alert_dialog_about);
        Button button1 =  view.findViewById(R.id.btn_alert_dialog_button1);
        Button button2 =  view.findViewById(R.id.btn_alert_dialog_button2);
        TextView tvTitle =  view.findViewById(R.id.tv_alert_dialog_title);
        tvDescription =  view.findViewById(R.id.tv_alert_dialog_detail);

        if (mMessage == null) {
            mTitle = CoreLibConst.SERVER_TIME_OUT_TAG;
            mMessage = CoreLibConst.SERVER_TIME_OUT_MSG;
            button1.setText(getString(R.string.retry));
            button2.setText(getString(R.string.cancel));
        }else{
            button1.setText(getString(R.string.ok));
            button2.setText(getString(R.string.cancel));
        }
        tvTitle.setText(mTitle);
        tvDescription.setText(mMessage);
        tvDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShareWere.copyText(mContext, tvDescription.getText().toString());
                Utility.toast(getContext(), "Text Copied.");
                return false;
            }
        });
        ivAbout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (errorMessage != null) {
                    tvDescription.setText(errorMessage);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                if (mListener != null) {
                    mListener.onPositiveClick(PopupDialog.this);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                if (mListener != null) {
                    mListener.onNegativeClick(PopupDialog.this);
                }
            }
        });
        return view;
    }
}
