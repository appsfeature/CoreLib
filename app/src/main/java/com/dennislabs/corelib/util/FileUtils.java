package com.dennislabs.corelib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


import com.dennislabs.corelib.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.FileProvider;

/**
 * Created by Admin on 4/16/2018.
 */

@SuppressWarnings("all")
public class FileUtils {
    public static final String FOLDER_SHARE = "Share";

    //step 1
    public static File getFileFromCacheDirectory(Context context, String filename) {
        return new File(context.getExternalFilesDir(FOLDER_SHARE), filename);
    }

    public static File getFileFromUri(Uri uri) {
        return new File(uri.getPath());
    }

    //step 2
    public static Uri getUriFromFile(Context context, File filePathImageCamera) {
        return FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                filePathImageCamera);
    }

    public static Uri getUriToDrawable(Context context, int image) {
        Bitmap bm = BitmapFactory.decodeResource( context.getResources(), image);
        //String filename = mItem.getTitle() +".png";
        String filename = getTimeStamp() +".png";
        // step 2
        File filePath = new File(context.getExternalFilesDir(FOLDER_SHARE), filename);

        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(filePath);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri imguri= Uri.fromFile(filePath);
        return imguri;
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static String getFileExtension(String file) {
        try {
            int startIndex = file.lastIndexOf('.') + 1;
            int endIndex = file.length();
            return file.substring(startIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return "File extension exception!";
        }
    }

    public static String getFileName(String file) {
        try {
            int startIndex = file.lastIndexOf('/') + 1;
            int endIndex = file.length();
            return file.substring(startIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return "Filename exception!";
        }
    }



}
