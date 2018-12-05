package com.dennislabs.corelib.global;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dennislabs.corelib.R;
import com.dennislabs.corelib.model.ErrorAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Abhijit Rao on 04/12/18.
 */
@SuppressWarnings("all")
public class CompactFragment extends Fragment {


    protected void setErrorAdapter(RecyclerView lvMain, String errorMessage) {
        if(lvMain!=null&&!TextUtils.isEmpty(errorMessage)) {
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add(errorMessage);
            lvMain.setAdapter(new ErrorAdapter(getContext(), errorList));
        }
    }

    protected boolean APICheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
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

    protected void initToolBarTheme(final Activity act, View v, String title) {
        ImageView ivBack = v.findViewById(R.id.ib_action_back);
        TextView tvTitle = v.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(act);
                act.onBackPressed();
            }
        });
    }
    protected void initToolBarForThemeActivity(final Activity act, View v, String title) {
        ImageView ivBack = v.findViewById(R.id.ib_action_back);
        TextView tvTitle = v.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(act);
                act.finish();
            }
        });
    }

//    protected void initCloseButton(final Activity act, View v) {
//        ImageButton ibClose = v.findViewById(R.id.ib_close);
//        ibClose.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideKeybord(act);
//                getActivity().onBackPressed();
//            }
//        });
//    }

    protected Toolbar initToolBar(final Activity act, View v, String title) {
        Toolbar toolbar =   v.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        AppCompatActivity activity = (AppCompatActivity) act;
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(act);
                act.onBackPressed();
            }
        });
        return toolbar;
    }
    protected void initToolBarForActivity(final Activity act, View v, String title) {
        Toolbar toolbar =   v.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        AppCompatActivity activity = (AppCompatActivity) act;
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard(act);
                        act.finish();
                    }
                }

        );
    }

//    protected void showSnakBar(CoordinatorLayout view, String message) {
//        Snackbar snackbar = Snackbar
//                .make(view, message, Snackbar.LENGTH_LONG);
//
//        // Changing message text color
//        snackbar.setActionTextColor(Color.WHITE);
//
////        // Changing action button text color
////        View sbView = snackbar.getView();
////        TextView textView =sbView.findViewById(android.support.design.R.id.snackbar_text);
////        textView.setTextColor(Color.YELLOW);
//
//        snackbar.show();
//    }
//    protected void showSnakBarAction(CoordinatorLayout view, String message) {
//        Snackbar snackbar = Snackbar
//                .make(view, message, Snackbar.LENGTH_LONG)
//                .setAction(getString(R.string.retry), new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                    }
//                });
//
//        // Changing message text color
//        snackbar.setActionTextColor(Color.RED);
//
//        // Changing action button text color
//        View sbView = snackbar.getView();
////        TextView textView =sbView.findViewById(android.support.design.R.id.snackbar_text);
////        textView.setTextColor(Color.YELLOW);
//
//        snackbar.show();
//    }

    protected void closeFragmentDialog(String dialogName) {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager!=null) {
            DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
            if (fragment != null) {
                fragment.dismiss();
            }
        }
    }

    protected void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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



    protected String loadPref(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getString(key, "");
    }

    protected void savePref(String key, String value) {
        if(value!=null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    protected int getColor(Context context, int resourceId) {
        return ContextCompat.getColor(context, resourceId);
    }

    protected void replaceFragment(Fragment fragment, int container, String tag) {
        if(getFragmentManager()!=null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(container, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    protected void setFragment(Fragment fragment, int container, String tag) {
        if(getFragmentManager()!=null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(container, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    protected void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

    protected void addFragment(Fragment fragment, int container, String tag) {
        if(getFragmentManager()!=null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(container, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }
    protected void addFragmentWithoutBackStack(Fragment fragment, String tag) {
        addFragmentWithoutBackStack(fragment, android.R.id.content, tag);
    }
    protected void addFragmentWithoutBackStack(Fragment fragment, int container, String tag) {
        if(getFragmentManager()!=null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(container, fragment, tag);
            transaction.commitAllowingStateLoss();
        }
    }

    protected void setUnSetFragment(FragmentManager manager, int containerId, Fragment addFragment, String fragTag
            , Fragment rFrag1) {
        FragmentTransaction ft = manager.beginTransaction();
        if (addFragment != null) {
            if (addFragment.isAdded()) { // if the fragment is already in container
                ft.show(addFragment);
            } else { // fragment needs to be added to frame container
                ft.add(containerId, addFragment, fragTag);
            }
        }

        // Hide other fragments
        if (rFrag1 != null && rFrag1.isAdded()) {
            ft.hide(rFrag1);
        }

        ft.commit();
    }
}
