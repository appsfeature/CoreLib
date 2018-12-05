package com.dennislabs.corelib.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.widget.Toast;

import com.dennislabs.corelib.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import androidx.core.content.ContextCompat;

//import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by Abhijit on 23-Dec-16.
 */
@SuppressWarnings("all")
@SuppressLint("UnusedResources")
public class Utility {


    public  static boolean checkInternetConnection(Context context) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isConnected();
    }

    // return imageUrl
    public static String local(String latitudeFinal, String longitudeFinal){
        return "https://maps.googleapis.com/maps/api/staticmap?center="+latitudeFinal+","+longitudeFinal+"&zoom=18&size=280x280&markers=color:red|"+latitudeFinal+","+longitudeFinal;
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }


//    private static int arrayIndexFindingByMatchString(List<String> list, Object userString) {
//        return Arrays.asList(list).indexOf(userString);
//    }

    public static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    public String encodeUrl(String res) {
        String UrlEncoding = null;
        try {
            UrlEncoding = URLEncoder.encode(res, "UTF-8");

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return UrlEncoding;
    }

    public static SpannableString generateSpannableTitle(Context context, String title, String noramalText) {
        return generateSpannableTitle(title, noramalText, ContextCompat.getColor(context, R.color.colorGreen), false);
    }

    public static SpannableString generateSpannableTitle(Context context, String title, String noramalText, Boolean isTitleBold) {
        return generateSpannableTitle(title, noramalText, ContextCompat.getColor(context, R.color.colorGreen), isTitleBold);
    }

    public static SpannableString generateSpannableTitle(String title, String noramalText, int titleColor, Boolean isTitleBold) {
        SpannableString s = new SpannableString(title + " " + noramalText);
        if (isTitleBold) {
            s.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);
        }
        s.setSpan(new ForegroundColorSpan(titleColor), 0, title.length(), 0);
        return s;
    }

    public static SpannableString generateBoldTitle(String title, String noramalText) {
        SpannableString s = new SpannableString(title + " " + noramalText);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);
        return s;
    }

    public static int getColor(Context context, int resourceId) {
        return ContextCompat.getColor(context, resourceId);
    }

    public static Drawable getDrawable(Context context, int resourceId) {
        return ContextCompat.getDrawable(context, resourceId);
    }


    public static String loadPref(Context context, String key) {
        return loadPref(context, key, "");
    }

    public static String loadPref(Context context, String key, String defaultValue) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(key, defaultValue);
        }
        return "";
    }

    public static Boolean loadPrefBoolean(Context context, String key) {
        return loadPrefBoolean(context, key, false);
    }

    public static Boolean loadPrefBoolean(Context context, String key, Boolean defaultValue) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getBoolean(key, defaultValue);

        }
        return false;
    }

    public static void savePref(Context context, String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (context != null) {
            editor.putInt(key, value);
            editor.apply();
        }
    }

    public static void savePrefBoolean(Context context, String key, Boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (context != null) {
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    public static int loadPrefInteger(Context context, String key) {
        return loadPrefInteger(context, key, 0);
    }

    public static int loadPrefInteger(Context context, String key, int defaultValue) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getInt(key, defaultValue);
        }
        return 0;
    }

    public static void savePref(Context context, String key, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (context != null && name != null) {
            editor.putString(key, name);
            editor.apply();
        }
    }

    public static String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }


    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public static float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }


    public static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }


    public static String decimalFormat(float value) {
        return decimalFormat(value, "#");
    }

    public static String decimalFormat(float value, String type) {
        switch (type) {
            case "#":
                return new DecimalFormat("#").format(value);
            case "#.#":
                return new DecimalFormat("#.#").format(value);
            case "#.##":
                return new DecimalFormat("#.##").format(value);
            case "#.###":
                return new DecimalFormat("#.###").format(value);
            default:
                return new DecimalFormat("#").format(value);
        }
    }

}
