package com.corelib.progressmanager;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corelib.R;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProgressManager {
    public static final int THEME_LITE = 3, THEME_DARK = 4;
    public static final int SCREEN_SMALL = 0, SCREEN_FULL = 1;
    private RelativeLayout container;
    private final Context context;
    private String TAG = "ProgressManager";

    private static ProgressManager instance;
    private static Progress progress;
    private Progress.ViewHolder holder;

    private ProgressManager(Context context) {
        this.context = context;
        progress = new Progress();
    }


    public static Progress newInstance(Context context) {
        instance = new ProgressManager(context);
        return progress;
    }


    public void startProgress() {
        if (checkProgressManager() && progress.isCustomProgressAvailableThanShow()) {
            if (holder == null) {
                holder = progress.getViewReference(container);
                int height = holder.llProgress.getMeasuredHeight();
                progress.setLayoutTheme(holder);
                ProgressAnim.slideDownAlpha(holder.llProgress);
            }
            progress.startProgress(holder);
        }
    }

    public void stopProgress() {
        stopProgress(null);
    }

    /**
     * Call when error occurred
     */
    public void stopProgress(String errorMessage) {
        stopProgress(errorMessage, false);
    }

    public void stopProgress(String errorMessage, boolean showMessageOnly) {
        if (checkProgressManager() && progress.isCustomProgressAvailableThanHide() && holder != null) {
            if (errorMessage == null) {
                ProgressAnim.slideUp(holder.llProgress, new ProgressAnim.AnimListener() {
                    @Override
                    public void onAnimationEnd() {
                        container.removeAllViews();
                        holder = null;
                    }
                });
            } else {
                progress.stopProgress(holder, errorMessage, showMessageOnly);
            }
        }
    }


    public void updateLoadingMessage(String message) {
        progress.loadingMessage = message;
    }


    public ProgressManager setContainer(RelativeLayout container) {
        this.container = container;
        return this;
    }

    public ProgressManager setBackgroundColor(int backgroundColorRes) {
        progress.backgroundColorRes = backgroundColorRes;
        return this;
    }

    public void updateBackgroundColor(int backgroundColorRes) {
        progress.backgroundColorRes = backgroundColorRes;
        if (holder != null) {
            progress.setLayoutTheme(holder);
        }
    }

    public ProgressManager setRetryListener(RetryListener retryListener) {
        progress.retryListener = retryListener;
        return this;
    }


    public class Progress {

        private int backgroundColorRes = 0;
        private int gravity;
        private String loadingMessage;
        private SwipeRefreshLayout swipeRefreshLayout;
        private int theme = 0;

        private RetryListener retryListener;
        private int errorIcon = 0;
        private LayoutInflater inflater;

        public Progress setLayoutType(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Progress setLoadingMessage(String loadingMessage) {
            this.loadingMessage = loadingMessage;
            return this;
        }

        public Progress setErrorIcon() {
            setErrorIcon(R.drawable.ic_internet_error);
            return this;
        }

        public Progress setErrorIcon(int errorIcon) {
            this.errorIcon = errorIcon;
            return this;
        }

        public Progress setCustomProgress(SwipeRefreshLayout swipeRefreshLayout) {
            this.swipeRefreshLayout = swipeRefreshLayout;
            return this;
        }

        public ProgressManager build() {
            this.inflater = LayoutInflater.from(context);
            return instance;
        }

        void startProgress(ViewHolder holder) {
            if (holder != null) {
                if (!TextUtils.isEmpty(loadingMessage)) {
                    holder.tvMessage.setText(loadingMessage);
                } else {
                    ProgressAnim.startAlphaAnimation(holder.tvMessage, View.GONE);
                }
                holder.ivErrorIcon.setImageResource(errorIcon);
                if (retryListener == null && errorIcon != 0) {
                    ProgressAnim.startAlphaAnimation(holder.ivErrorIcon, View.VISIBLE);
                    ProgressAnim.startAlphaAnimation(holder.btnRetry, View.GONE);
                } else {
                    ProgressAnim.startAlphaAnimation(holder.btnRetry, View.INVISIBLE);
                }
                ProgressAnim.startAlphaAnimation(holder.progressBar, View.VISIBLE);
            }
        }

        void stopProgress(ViewHolder holder, String errorMessage, boolean showMessageOnly) {
            if (holder != null) {
                if (errorMessage != null) {
                    if (errorIcon != 0) {
                        holder.ivErrorIcon.setVisibility(View.VISIBLE);
                        holder.ivErrorIcon.setImageResource(errorIcon);
                    } else {
                        if (showMessageOnly) {
                            holder.llProgress.setOrientation(LinearLayout.VERTICAL);
                            holder.tvMessage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
                            holder.tvMessage.setGravity(Gravity.CENTER);
                        }
                    }
                } else {
                    ProgressAnim.startAlphaAnimation(holder.ivErrorIcon, View.INVISIBLE);
                }
                holder.tvMessage.setText(errorMessage);
                ProgressAnim.startAlphaAnimation(holder.tvMessage, View.VISIBLE);

                if (retryListener == null) {
                    if (errorIcon != 0) {
                        ProgressAnim.startAlphaAnimation(holder.ivErrorIcon, View.VISIBLE);
                        holder.ivErrorIcon.setImageResource(errorIcon);
                    }
                } else {
                    ProgressAnim.startAlphaAnimation(holder.btnRetry, View.VISIBLE);
                }
                ProgressAnim.startAlphaAnimation(holder.progressBar, View.GONE);
            }
        }


        private void setLayoutTheme(ViewHolder holder) {
            if (TextUtils.isEmpty(loadingMessage)) {
                holder.llProgress.setOrientation(LinearLayout.VERTICAL);
                holder.tvMessage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
                holder.tvMessage.setGravity(Gravity.CENTER);
            }
            if (theme == THEME_DARK) {
                holder.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                holder.btnRetry.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                holder.ivErrorIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorWhite));
                holder.progressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.progress_custom_white));
                if (backgroundColorRes != 0) {
                    holder.llProgress.setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes));
                } else {
                    holder.llProgress.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                }
            } else {
                holder.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.progressTextColorBlack));
                holder.btnRetry.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                holder.ivErrorIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
                holder.progressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.progress_custom_accent));
                if (backgroundColorRes != 0) {
                    holder.llProgress.setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes));
                } else {
                    holder.llProgress.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                }
            }
        }


        private ViewHolder getViewReference(RelativeLayout rootView) {
            if (inflater == null) {
                setSdkError("Error:003 Not build ProgressManager properly, Please check the documentation of ProgressManager");
            }
            if (gravity == SCREEN_FULL) {
                return new ViewHolder(inflater.inflate(R.layout.screen_progress_large, rootView));
            } else {
                return new ViewHolder(inflater.inflate(R.layout.screen_progress_small, rootView));
            }
        }

        public Progress setProgressTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public Progress setRetryListener(RetryListener retryListener) {
            this.retryListener = retryListener;
            return this;
        }

        boolean isCustomProgressAvailableThanShow() {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
                return false;
            } else {
                return true;
            }
        }

        boolean isCustomProgressAvailableThanHide() {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
                return false;
            } else {
                return true;
            }
        }


        class ViewHolder {
            private final TextView tvMessage;
            private final ProgressBar progressBar;
            private final Button btnRetry;
            private final ImageView ivErrorIcon;
            private final LinearLayout llProgress;

            private ViewHolder(View view) {
                llProgress = view.findViewById(R.id.llProgress);
                tvMessage = view.findViewById(R.id.tvProgressMessage);
                progressBar = view.findViewById(R.id.progressBar);
                btnRetry = view.findViewById(R.id.btnRetry);
                ivErrorIcon = view.findViewById(R.id.ivErrorIcon);
                btnRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (retryListener != null) {
                            retryListener.onRetryClick();
                        }
                    }
                });
            }
        }

    }

    private boolean checkProgressManager() {
        if (progress == null) {
            setSdkError("Error:001 Please check the documentation of ProgressManager");
            return false;
        } else if (container == null) {
            setSdkError("Error:002 Container does'nt initialize, Please check the documentation of ProgressManager");
            return false;
        } else {
            return true;
        }
    }

    private void setSdkError(String error) {
        TextView tvError = new TextView(context);
        tvError.setText(error);
        tvError.setPadding(16, 16, 16, 16);
        tvError.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        if (container != null) {
            container.addView(tvError);
        } else {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
    }

    public static void clearContainer(RelativeLayout container) {
        container.removeAllViews();
    }

    public static TextView setErrorMessage(Context context, RelativeLayout container, String error) {
        return setErrorMessage(context, container, THEME_DARK, error);
    }

    public static TextView setErrorMessage(Context context, RelativeLayout container, int theme, String error) {
        TextView tvError = new TextView(context);
        tvError.setText(error);
        tvError.setGravity(Gravity.CENTER);
        tvError.setPadding(32, 32, 32, 32);
        tvError.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        if (theme == THEME_DARK) {
            tvError.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            tvError.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        } else if (theme == THEME_LITE) {
            tvError.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
            tvError.setTextColor(ContextCompat.getColor(context, R.color.progressTextColorGrey));
        } else {
            tvError.setBackgroundColor(ContextCompat.getColor(context, theme));
            tvError.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        }
        container.addView(tvError);
        return tvError;
    }

}
