package com.corelib.basic.global;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelib.R;
import com.corelib.basic.util.CoreLibConst;
import com.corelib.basic.util.ShareWere;
import com.corelib.basic.util.Utility;

import static com.corelib.basic.setup.CoreLib.PLAY_STORE_URL;


/**
 * Reference : https://developer.android.com/guide/topics/ui/dialogs#java
 */
public class VersionDialog {
    private Dialog dialog;
    private Boolean restrictToUpdate;
    private Activity activity;
    private TextView tvDescription;

    public static VersionDialog newInstance(Activity activity, Boolean restrictToUpdate) {
        VersionDialog mInstance = new VersionDialog();
        mInstance.activity = activity;
        mInstance.restrictToUpdate = restrictToUpdate;
        return mInstance;
    }
 
    public void show() {
        if ( activity != null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = LayoutInflater.from(activity);
            dialogBuilder.setView(viewHolder(inflater.inflate(R.layout.alert_dialog, null)));
            dialogBuilder.setCancelable(!restrictToUpdate);
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    private View viewHolder(View view) {
        ImageView ivAbout =  view.findViewById(R.id.iv_alert_dialog_about);
        Button button1 =  view.findViewById(R.id.btn_alert_dialog_button1);
        Button button2 =  view.findViewById(R.id.btn_alert_dialog_button2);
        TextView tvTitle =  view.findViewById(R.id.tv_alert_dialog_title);
        tvDescription =  view.findViewById(R.id.tv_alert_dialog_detail);

        tvTitle.setText("Alert");
        tvDescription.setText("New version is available on play store.");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
        return view;
    }

    private void update(Activity activity) {
        String packageName = activity.getApplicationContext().getPackageName();
        Uri applicationUrl = Uri.parse(PLAY_STORE_URL+ packageName);
        Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, applicationUrl);
        activity.startActivity(browserIntent1);
    }
}
