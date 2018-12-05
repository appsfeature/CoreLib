package com.dennislabs.corelib.util;

import android.content.Context;
import android.widget.Toast;

@SuppressWarnings("all")
public class L {
	public static void m(String message){
//		Log.d("@-Test", message);
	}
	public static void s(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
