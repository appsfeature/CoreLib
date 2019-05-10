package com.corelib.basic.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.corelib.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyManager {
    private static final String CHANNEL_ID = "DataSync";
    private final Context context;
    private final String title;

    private int notificationId = 01;
    private int progressMax = 100;
    private int progressCurrent = 0;
    private int icon;
    private String tickerText,contentText;
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder mBuilder;

    private NotifyManager(Context context, String title) {
        this.context = context;
        this.title = title;
        this.icon = R.drawable.ic_about;
        this.setNotification();
    }

    public NotifyManager setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public NotifyManager setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public NotifyManager setProgressMax(int progressMax) {
        this.progressMax = progressMax;
        return this;
    }

    public static NotifyManager getInstance(Context context, String title) {
        return new NotifyManager(context, title);
    }

    private void setNotification() {
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(context);
        mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        mBuilder.setContentTitle(title)
                .setSmallIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_LOW);
        if(!TextUtils.isEmpty(tickerText)){
            mBuilder.setTicker(tickerText);
        }

        if(!TextUtils.isEmpty(contentText)){
            mBuilder.setContentText(contentText);
        }

        updateProgress(progressCurrent);
    }

    //message = "Download complete"
    public void showNotification() {
        notificationManager.notify(notificationId, mBuilder.build());
    }

    //message = "Download complete"
    public void finishProgress(String message) {
        // When done, update the notification one more time to remove the progress bar
        mBuilder.setContentText(message)
                .setProgress(0, 0, false);
        notificationManager.notify(notificationId, mBuilder.build());

    }

    public void updateProgress(int currentProgress) {
        // Issue the initial notification with zero progress
        mBuilder.setProgress(progressMax, currentProgress, false);
        notificationManager.notify(notificationId, mBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "DictionaryAndroid";
            String description = "Offline Dictionary";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
