package com.corelib.basic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


/**
 * @author Created by Abhijit on 13-Dec-16.
 */
public class AppPreferences {
    private static final String APP_USER = "app_user";
    private static final String APP_NAME = "app_name";
    private static final String USER_IMAGE = "user_image";
    private static final String APP_THEME = "app_theme";

    public static void setName(Context context, String name) {
        savePref(context, APP_NAME, name);
    }

    public static String getName(Context context) {
        return loadPref(context, APP_NAME);
    }

    public static void setImage(Context context, String value) {
        savePref(context, USER_IMAGE, value);
    }

    public static String getImage(Context context) {
        return loadPref(context, USER_IMAGE);
    }

    public static void setUserId(Context context, String value) {
        savePref(context, APP_USER, value);
    }

    public static String getUserId(Context context) {
        return loadPref(context, APP_USER);
    }

    public static void setAppTheme(Context context , int value) {
        Utility.savePrefInteger(context, APP_THEME, value);
    }

    public static int getAppTheme(Context context,int defaultTheme) {
        return Utility.loadPrefInteger(context, APP_THEME, defaultTheme);
    }

    private static void savePref(Context context, String key, String value) {
        if (context != null && value != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, encrypt(value));
            editor.apply();
        }
    }

    private static String loadPref(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            return decrypt(sharedPreferences.getString(key, ""));
        }
        return "";
    }


    private static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

}