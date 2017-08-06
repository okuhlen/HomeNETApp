package com.koeksworld.homenet;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import Fragments.HouseMessagesFragment;
import HomeNETStream.AnnoucementFragment;
import HomeNETStream.FeedFragment;
import Tasks.HomeNetFeedTask;
import Utilities.DeviceUtils;

public class HomeNetFeedActivity extends AppCompatActivity {

    private HomeNetFeedTask feedTask;
    private DeviceUtils deviceUtils;
    private Toolbar appToolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView toolbarTextView;
    private SharedPreferences sharedPreferences;
    private TextView nameSurnameTextView;
    private TextView emailTextView;
    private BottomBar feedBottomBar;
    private ImageView profileImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_net_feed);
        initializeComponents();
        setupHeaderView();
        toolbarTextView.setText("Your Feed");
        //getSupportActionBar().setTitle(null);
        setSupportActionBar(appToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadFeedFragment();
    }

    private void setupHeaderView() {
        View headerView = navigationView.inflateHeaderView(R.layout.homenet_feed_menu_header);
        nameSurnameTextView = (TextView) headerView.findViewById(R.id.UserNameSurnameTextView);
        emailTextView = (TextView) headerView.findViewById(R.id.HeaderEmailAddressTextView);
        nameSurnameTextView.setText(sharedPreferences.getString("name", "") + " "+sharedPreferences.getString("surname", ""));
        emailTextView.setText(sharedPreferences.getString("emailAddress", ""));
        profileImageView = (ImageView) headerView.findViewById(R.id.UserProfileImageView);
        String name =  sharedPreferences.getString("name", "").substring(0,1);
        String surname = sharedPreferences.getString("surname", "").substring(0,1);
        TextDrawable drawable = TextDrawable.builder().buildRound(name.toUpperCase() + surname.toUpperCase(), Color.BLUE);
        profileImageView.setImageDrawable(drawable);

    }

    //Source: https://stackoverflow.com/questions/33194594/navigationview-get-find-header-layout
    private void initializeComponents() {
        deviceUtils = new DeviceUtils(this);
        feedBottomBar = (BottomBar) findViewById(R.id.HomeNetFeedBottomBar);
        feedBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.MessagesTab:
                        HouseMessagesFragment messagesFragment = new HouseMessagesFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.HomeNetFeedContentView, messagesFragment, null);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.AnnouncementsTab:
                        AnnoucementFragment annoucementFragment = new AnnoucementFragment();
                        FragmentTransaction transactionTwo = getFragmentManager().beginTransaction();
                        transactionTwo.replace(R.id.HomeNetFeedContentView, annoucementFragment, null);
                        transactionTwo.addToBackStack(null);
                        transactionTwo.commit();
                        break;
                    case R.id.SettingsTab:
                        Intent settingsIntent = new Intent(getParent(), ApplicationSettingsActivity.class);
                        startActivity(settingsIntent);
                        break;
                    case R.id.YourFeedTab:
                        FeedFragment feedFragment = new FeedFragment();
                        FragmentTransaction transactionThree = getFragmentManager().beginTransaction();
                        transactionThree.replace(R.id.HomeNetFeedContentView, feedFragment, null);
                        transactionThree.addToBackStack(null);
                        transactionThree.commit();


                        break;

                }
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        appToolbar = (Toolbar) findViewById(R.id.HomeNetFeedToolbar);
        toolbarTextView = (TextView) findViewById(R.id.HomeNetFeedToolbarTextView);
        navigationView = (NavigationView) findViewById(R.id.HomeNetFeedNavigationMenu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Check item
                drawerLayout.closeDrawers();
                return true;
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.FeedDrawerLayout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("toolbar", toolbarTextView.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_setttings_menu_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    private void loadFeedFragment() {
        FeedFragment feedFragment = new FeedFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.HomeNetFeedContentView, feedFragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
