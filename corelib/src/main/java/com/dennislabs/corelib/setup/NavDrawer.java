package com.dennislabs.corelib.setup;

import android.view.View;

import com.dennislabs.corelib.R;
import com.dennislabs.corelib.setup.BaseActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

/**
 * Created by Abhijit on 14-Jul-16.
 */

//private void removeItem(int position) {
//        mList.remove(position);
//        adapter.notifyItemRemoved(position);
//        adapter.notifyItemRangeChanged(position, mList.size());
//        }

public abstract class NavDrawer extends BaseActivity {


//    final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//    ImageView ivProfile = (ImageView) findViewById(R.id.iv_profile);
//    TextView tvName = (TextView) findViewById(R.id.tv_profile_name);
//    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    toolbar.setTitle(title);
//    setSupportActionBar(toolbar);
    protected void setDrawer(final Toolbar toolbar, final DrawerLayout drawer, NavigationView navigationView, Boolean isSetNavigation, NavigationView.OnNavigationItemSelectedListener listener) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(listener);

        if(isSetNavigation && toolbar != null) {
            final ActionBarDrawerToggle finalToggle = toggle;
            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        if(getSupportActionBar()!=null)
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    } else {
                        //show hamburger
                        if(getSupportActionBar()!=null)
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        finalToggle.syncState();
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawer.openDrawer(GravityCompat.START);
                            }
                        });
                    }
                }
            });
        }
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_item_1) {
//
//        } else if (id == R.id.nav_item_2) {
//
//        } else if (id == R.id.nav_item_3) {
//
//        } else if (id == R.id.nav_item_4) {
//
//        } else if (id == R.id.nav_item_5) {
//            addFragment(AppFeedback.newInstance("userId"), "appFeedback");
//        } else if (id == R.id.nav_item_6) {
//            addFragment(AppAboutUs.newInstance(MainActivity.this,""), "appAboutUs");
//        } else if (id == R.id.nav_item_7) {
//            Sharewere.shareApp(MainActivity.this);
//        }
//
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }



}