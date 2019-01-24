package com.corelib.basic.global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corelib.R;
import com.corelib.basic.model.ErrorAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by Abhijit Rao on 04/12/18.
 */
public class BaseFragmentUtil{


    private final Fragment fragment;
    private final Context context;

    private BaseFragmentUtil(Fragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
    }

    public static BaseFragmentUtil getInstance(Fragment fragment, Context context) {
        return new BaseFragmentUtil(fragment,context);
    }

    public void setErrorAdapter(RecyclerView lvMain, String errorMessage) {
        if(lvMain!=null&&!TextUtils.isEmpty(errorMessage)) {
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add(errorMessage);
            lvMain.setAdapter(new ErrorAdapter(context, errorList));
        }
    }

    public boolean APICheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private int getColorRes(Context context, int resource) {
        return ContextCompat.getColor(context,resource);
    }

    public void setCardBackgroundColor(Context context, CardView view, int colorResource) {
        view.setCardBackgroundColor(ContextCompat.getColor(context, colorResource));
    }


    public void setBackgroundColor(Context context, View view, int colorLite) {
        view.setBackgroundColor(getColorRes(context, colorLite));
    }


    public void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public void enableFields(EditText editText){
        editText.setClickable(false);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    public void disableFields(EditText editText){
        editText.setClickable(true);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }

    public void initToolBarTheme(final Activity act, View v, String title) {
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
    public void initToolBarForThemeActivity(final Activity act, View v, String title) {
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

//    public void initCloseButton(final Activity act, View v) {
//        ImageButton ibClose = v.findViewById(R.id.ib_close);
//        ibClose.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideKeyboard(act);
//                getActivity().onBackPressed();
//            }
//        });
//    }

    public Toolbar initToolBar(final Activity act, View v, String title) {
        Toolbar toolbar =   v.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        AppCompatActivity context = (AppCompatActivity) act;
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
    public void initToolBarForActivity(final Activity act, View v, String title) {
        Toolbar toolbar =   v.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        AppCompatActivity context = (AppCompatActivity) act;
        context.setSupportActionBar(toolbar);
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

    public void closeFragmentDialog(String dialogName) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if(fragmentManager!=null) {
            DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
            if (fragment != null) {
                fragment.dismiss();
            }
        }
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showKeyboard(View view, Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm!=null) {
//                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }


    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = context.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



    public String loadPref(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void savePref(String key, String value) {
        if(value!=null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public int getColor(Context context, int resourceId) {
        return ContextCompat.getColor(context, resourceId);
    }

    public void replaceFragment(Fragment fragment, int container, String tag) {
        if(fragment.getFragmentManager()!=null) {
            FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
            transaction.replace(container, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    public void setFragment(Fragment fragment, int container, String tag) {
        if(fragment.getFragmentManager()!=null) {
            FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
            transaction.replace(container, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    public void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

    private void addFragment(Fragment fragment, int container, String tag) {
        if(fragment.getFragmentManager()!=null) {
            FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
            transaction.add(container, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }
    public void addFragmentWithoutBackStack(Fragment fragment, String tag) {
        addFragmentWithoutBackStack(fragment, android.R.id.content, tag);
    }
    private void addFragmentWithoutBackStack(Fragment fragment, int container, String tag) {
        if(fragment.getFragmentManager()!=null) {
            FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
            transaction.add(container, fragment, tag);
            transaction.commitAllowingStateLoss();
        }
    }

    public void setUnSetFragment(FragmentManager manager, int containerId, Fragment addFragment, String fragTag
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

    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if(fragmentManager!=null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void startTransitionActivity(Activity activity, Intent intent, View textView) {
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
