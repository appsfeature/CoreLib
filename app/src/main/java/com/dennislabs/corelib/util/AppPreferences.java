package com.dennislabs.corelib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.dennislabs.corelib.setup.CoreLib;


/**
 * Created by Abhijit on 13-Dec-16.
 */
@SuppressWarnings("all")
public class AppPreferences {
    private static final String APP_USER = CoreLib.APP_KEYWORD + "app_user";
    private static final String APP_NAME = CoreLib.APP_KEYWORD + "app_name";
    private static final String USER_IMAGE = CoreLib.APP_KEYWORD + "user_image";
    private final Context context;
//    private static AppPreferences _instance;

    public AppPreferences(Context context) {
        this.context = context;
    }

//    public static AppPreferences getInstance(Context context){
//        if(_instance==null) {
//            _instance = new AppPreferences(context);
//        }
//        return _instance;
//    }
     
    public void setName(String name) {
        savePref(context, APP_NAME, name);
    }
    public String getName() {
        return loadPref(context,APP_NAME);
    }

    public void setImage(String value) {
        savePref(context, USER_IMAGE, value);
    }
    public String getImage() {
        return loadPref(context,USER_IMAGE);
    }

    public void setUserId(String value) {
        savePref(context, APP_USER, value);
    }
    public String getUserId() {
        return loadPref(context,APP_USER);
    }

    private void savePref(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (context != null && value != null) {
            editor.putString(key, encrypt(value));
            editor.apply();
        }
    }

    private String loadPref(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return decrypt(sharedPreferences.getString(key, ""));
        }
        return "";
    }


    public String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

}