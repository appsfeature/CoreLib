package com.dennislabs.corelib.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dennislabs.corelib.R;
import com.dennislabs.corelib.model.ErrorAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Abhijit on 14-Jul-16.
 */
@SuppressWarnings("all")
public abstract class BaseActivity extends AppCompatActivity {
    private BaseActivity mContext;

    protected void setAnalytics(Activity activity, String method, String detail) {
        String className = activity.getClass().getSimpleName();
    }

    protected void setErrorAdapter(RecyclerView lvMain, String errorMessage) {
        if(lvMain!=null&&!TextUtils.isEmpty(errorMessage)) {
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add(errorMessage);
            lvMain.setAdapter(new ErrorAdapter(BaseActivity.this, errorList));
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected View getActivityRootView() {
        return getWindow().getDecorView().getRootView();
    }

    protected void initToolBarForTheme(final Activity act, String title) {
        ImageButton ibBack = findViewById(R.id.ib_action_back);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(act);
                act.onBackPressed();
            }
        });
    }
    protected Toolbar initToolBar(String title) {
        Toolbar toolbar = null;
        if (!TextUtils.isEmpty(title)) {
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);

            toolbar.setNavigationIcon(R.mipmap.ic_back);
            toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideKeyboard(mContext);
                            onBackPressed();
                        }
                    }
            );
        }
        return toolbar;
    }



    protected boolean APICheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    protected Toolbar setToolBar(String title) {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        return toolbar;
    }

    protected void closeFragmentDialog(String dialogName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    protected void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    protected void enableFields(EditText editText){
        editText.setClickable(false);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    protected void disableFields(EditText editText){
        editText.setClickable(true);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }



    protected void showKeyboard(View view, Context activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm!=null) {
//                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }
    protected static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }






    protected String LoadPref(String key) {
        return LoadPref(mContext, key);
    }

    protected String LoadPref(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(key, "");
        } else {
            return "";
        }
    }

    protected void SavePref(String key, String value) {
        SavePref(mContext, key, value);
    }

    protected void SavePref(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    protected String encodeUrl(String res) {
        String UrlEncoding = null;
        try {
            UrlEncoding = URLEncoder.encode(res, "UTF-8");

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return UrlEncoding;
    }



    protected String getSt(int id) {
        return getResources().getString(id);
    }

    private void EnableMobileIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.provider.Settings.ACTION_SETTINGS);
        startActivity(intent);

    }

    protected void showToast(String txt) {
        // Inflate the Layout
        Toast toast = Toast.makeText(mContext, txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 30);
        toast.show();
    }


    protected void replaceFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    protected void setFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    protected void addFragment(Fragment fragment) {
        addFragment(fragment, android.R.id.content);
    }

    protected void addFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    protected void addFragmentWithoutBackstack(Fragment fragment) {
        addFragmentWithoutBackstack(fragment,android.R.id.content);
    }
    protected void addFragmentWithoutBackstack(Fragment fragment, int container) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    // for 3 tabs
    protected void setTabFragment(int containerId, Fragment addFragment
            , Fragment rFrag1, Fragment rFrag2, Fragment rFrag3) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addFragment != null) {
            if (addFragment.isAdded()) { // if the fragment is already in container
                ft.show(addFragment);
            } else { // fragment needs to be added to frame container
                ft.add(containerId, addFragment, addFragment.getClass().getSimpleName());
            }
        }

        // Hide 1st fragments
        if (rFrag1 != null && rFrag1.isAdded()) {
            ft.hide(rFrag1);
        }
        // Hide 2nd fragments
        if (rFrag2 != null && rFrag2.isAdded()) {
            ft.hide(rFrag2);
        }
        // Hide 2nd fragments
        if (rFrag3 != null && rFrag3.isAdded()) {
            ft.hide(rFrag3);
        }

        ft.commit();
    }
}