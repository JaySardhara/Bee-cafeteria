package com.cmpe272.beecafeteria.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cmpe272.beecafeteria.R;
import com.cmpe272.beecafeteria.fragments.DealsFragment;
import com.cmpe272.beecafeteria.fragments.NavigationDrawerFragment;
import com.cmpe272.beecafeteria.fragments.OrderDetailFragment;
import com.cmpe272.beecafeteria.fragments.OrdersFragment;
import com.cmpe272.beecafeteria.fragments.OutletsFragment;
import com.cmpe272.beecafeteria.modelResponse.Order;
import com.cmpe272.beecafeteria.others.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OrdersFragment.OrderFragmentCallbacks {

    private static final String TAG = "UserActivity";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                setFragment(OutletsFragment.newInstance("Outlets"), false);
                break;
            case 1:
                setFragment(DealsFragment.newInstance("Daily Deals"), false);
                break;
            case 2:
                setFragment(OrdersFragment.newInstance("My Orders"), false);
                break;
        }
        onSectionAttached(position);
    }

    @Override
    public void onOrderSelected(Order order) {
        setFragment(OrderDetailFragment.newInstance(order), true);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.nav_item_outlets);
                break;
            case 1:
                mTitle = getString(R.string.nav_item_dd);
                break;
            case 2:
                mTitle = getString(R.string.nav_item_myOrders);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                SessionManager.logoutUser(UserActivity.this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragment(Fragment fragment, boolean isAdd) {

        //TODO: Unhandled for generic fragment only pop one fragment. have to handle it generic.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isAdd) {
            fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);;
        }
        fragmentTransaction.replace(R.id.container, fragment)
                .commit();
    }



}
