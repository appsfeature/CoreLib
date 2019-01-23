package com.corelib.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.corelib.R;

import androidx.core.app.NotificationCompat;


// share image through NavDrawer.class method shareClick();
public class ShareWere {
    private static final int NOTIFICATION_SEARCH = 1001;
    private static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";

    //android:textIsSelectable="true"   use in xml
    public static void copyText(Context context, String text) {
        ClipboardManager cManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cData = ClipData.newPlainText("text", text);
        cManager.setPrimaryClip(cData);
    }

    public static void openExternalBrowser(Activity activity, String url) {
        Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent1);
    }

    public static void rateUs(Activity activity) {
        Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URL+activity.getPackageName()));
        activity.startActivity(browserIntent1);
    }

    public static void shareApp(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this app! "+PLAY_STORE_URL+activity.getPackageName());
        activity.startActivity(Intent.createChooser(intent, "Share With Fiends"));
    }

    // uncomment while using this method
    public static void actionCall(Activity activity, String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Uri number = Uri.parse("tel:" + phone);
//            Intent callIntent = new Intent(Intent.ACTION_CALL, number);
//            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            activity.startActivity(callIntent);
        }else{
            Toast.makeText(activity, "Number Not exists", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openExternalNavigation(Activity activity, String latitude, String longitude, String address) {
        Location location = new Location("local");
        location.setLatitude(Double.parseDouble(latitude));
        location.setLongitude(Double.parseDouble(longitude));

        // Display a label at the location of Google's Sydney office
        String gmmIntentUri = "geo:0,0?q=" + latitude + "," + longitude + "(" + address + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri));
//        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        intent.setPackage("com.google.android.apps.maps");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {

            Toast.makeText(activity, "Please install a maps application", Toast.LENGTH_LONG).show();

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(Context context, String finalBody) {
        Intent intent = new Intent();
        intent.putExtra("msg", finalBody);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel-01";
        String channelName = "Channel Name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

//                .setSmallIcon(R.drawable.ic_stat_name)
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle("Trigger fired")
                .setContentText(finalBody)
                .setAutoCancel(true)
                .setTicker("Trigger fired")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(finalBody));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_SEARCH, mBuilder.build());
    }

//    private void startVibration(Context context) {
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//// Vibrate for 500 milliseconds
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
//        } else {
//            //deprecated in API 26
//            v.vibrate(1000);
//        }
//    }

}
