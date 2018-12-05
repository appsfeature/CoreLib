package com.dennislabs.corelib.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dennislabs.corelib.R;


/**
 * Created by Abhijit Rao on 04/12/18.
 * Usage
 * responseView = NoResponseView.newInstance(getContext(), v)
 *                 .setImage(R.drawable.ic_internet_error)
 *                 .setRetryButton(new NoResponseView.ResponseViewListener() {
 *                     @Override
 *                     public void onRetryClick() {
 *                         sendFeedback();
 *                     }
 *                 });
 */
@SuppressWarnings("all")
public class NoResponseView {

    private final Context mContext;
    private final ImageView ivImage;
    private final LinearLayout layoutMain;
    private final ProgressButtonError btnAction;
    private final TextView tvTitle, tvDetail;

    public interface ResponseViewListener {
        void onRetryClick();
    }

    public void hide() {
        if (layoutMain != null)
            layoutMain.setVisibility(View.GONE);
    }

    public void show() {
        show(CoreLibConst.SERVER_TIME_OUT_TAG, CoreLibConst.SERVER_TIME_OUT_MSG);
    }

    public void show(String title, String description) {
        if (layoutMain != null) {
            tvTitle.setText(title);
            tvDetail.setText(description);
            layoutMain.setVisibility(View.VISIBLE);
        }
    }

    public static NoResponseView newInstance(Context context, View v) {
        return new NoResponseView(v, context);
    }

    public NoResponseView setImage(int imageResource) {
        ivImage.setImageResource(imageResource);
        return this;
    }

    public NoResponseView setRetryButton(ResponseViewListener listener) {
        return setRetryButton(mContext.getString(R.string.retry), listener);
    }

    private NoResponseView setRetryButton(String buttonText, final ResponseViewListener listener) {
        btnAction.setVisibility(View.VISIBLE)
                .setText(buttonText)
                .setTransparentBackground(R.color.colorGreen)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onRetryClick();
                    }
                });
        return this;
    }

    private NoResponseView(View v, Context context) {
        this.mContext = context;
        layoutMain = v.findViewById(R.id.ll_empty_detail);
        tvTitle = v.findViewById(R.id.tv_empty_title);
        tvDetail = v.findViewById(R.id.tv_empty_detail);
        ivImage = v.findViewById(R.id.iv_empty_image);
        btnAction = ProgressButtonError.newInstance(mContext, v);
        btnAction.setVisibility(View.GONE);
    }

}
