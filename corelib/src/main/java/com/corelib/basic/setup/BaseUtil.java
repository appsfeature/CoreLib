package com.corelib.basic.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.corelib.R;
import com.corelib.basic.model.ErrorAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by Abhijit on 14-Jul-16.
 */
public class BaseUtil{
    private final AppCompatActivity activity;

    private BaseUtil(AppCompatActivity activity) {
        this.activity = activity;
    }


    public void restartActivity() {
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.finish();
    }


    public void setAnalytics(String method, String detail) {
        String className = activity.getClass().getSimpleName();
    }

    public void setErrorAdapter(RecyclerView lvMain, String errorMessage) {
        if (lvMain != null && !TextUtils.isEmpty(errorMessage)) {
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add(errorMessage);
            lvMain.setAdapter(new ErrorAdapter(activity, errorList));
        }
    }

    public static BaseUtil getInstance(AppCompatActivity activity){
        return new BaseUtil(activity);

    }

    public View getActivityRootView() {
        return activity.getWindow().getDecorView().getRootView();
    }

    public TextView initToolBarForTheme(View view, String title) {
        ImageButton ibBack = view.findViewById(R.id.ib_action_back);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(activity);
                activity.onBackPressed();
            }
        });
        return tvTitle;
    }

    public Toolbar initToolBar(final AppCompatActivity mActivity, View view, String title) {
        return initToolBar(mActivity,view,title, 400);
    }
    private Toolbar initToolBar(final AppCompatActivity mActivity, View view, String title, long duration) {
        Toolbar toolbar;
        toolbar = view.findViewById(R.id.toolbar);
        TextView tvTitle = toolbar.findViewById(R.id.title);
        tvTitle.setText(title);
        mActivity.setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Uncomment for shared element transition between TextView and Toolbar
//            mActivity.getWindow().setSharedElementsUseOverlay(false);
            Bundle bundle = mActivity.getIntent().getExtras();
            if(bundle!=null){
                title = bundle.getString("title");
                if(!TextUtils.isEmpty(title)) {
                    tvTitle.setTransitionName(mActivity.getString(R.string.transition_actionbar_title) + title);
                    tvTitle.setText(title);
                }
            }
            TransitionSet set = new TransitionSet();
            set.addTransition(new ChangeBounds());
            set.setDuration(duration);
            mActivity.getWindow().setSharedElementExitTransition(set);
            mActivity.getWindow().setSharedElementEnterTransition(set);
        }
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard(mActivity);
                        mActivity.onBackPressed();
                    }
                }
        );
        return toolbar;
    }

    public Toolbar initToolBarDefault(final AppCompatActivity mActivity, View view, String title) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView tvTitle = toolbar.findViewById(R.id.toolbar_title);
        tvTitle.setText(title);
        mActivity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard(mActivity);
                        mActivity.onBackPressed();
                    }
                }
        );
        return toolbar;
    }


    public boolean APICheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public Toolbar setToolBar(AppCompatActivity mActivity, View view,String title) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        mActivity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        return toolbar;
    }

    public void closeFragmentDialog(String dialogName) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    public void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public void enableFields(EditText editText) {
        editText.setClickable(false);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    public void disableFields(EditText editText) {
        editText.setClickable(true);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }


    public void showKeyboard(View view, Context activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
//                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



    public String LoadPref(String key) {
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getPackageName(),Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } else {
            return "";
        }
    }

    public void SavePref(String key, String value) {
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getPackageName(),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
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


    private void EnableMobileIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.provider.Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }

    public void showToast(String txt) {
        // Inflate the Layout
        Toast toast = Toast.makeText(activity, txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 30);
        toast.show();
    }


    public void replaceFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void setFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void addFragment(Fragment fragment) {
        addFragment(fragment, android.R.id.content);
    }

    private void addFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void addFragmentWithoutBackStack(Fragment fragment) {
        addFragmentWithoutBackStack(fragment, android.R.id.content);
    }

    public void addFragmentWithoutBackStack(Fragment fragment, int container) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    // for 3 tabs
    public void setTabFragment(int containerId, Fragment addFragment
            , Fragment rFrag1, Fragment rFrag2, Fragment rFrag3) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
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

    public void startTransitionActivity(Intent intent, View textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && textView!=null) {
            textView.setTransitionName(activity.getString(R.string.transition_actionbar_title)+((TextView)textView).getText().toString());
//            Pair<View, String> pair1 = Pair.create(textView, textView.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, textView,textView.getTransitionName());
            activity.startActivity(intent, options.toBundle());
        }
        else {
            activity.startActivity(intent);
        }
    }
}