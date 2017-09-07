package com.example.task_ed;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.FragmentManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{

    AHBottomNavigation bottomNavigation;

    /*Run at app startup, set up defaults and listen for user input toward navigation bar*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeNav();
        FragmentManager fragManager = getSupportFragmentManager();
        fragManager.beginTransaction().add(R.id.frag_container, new TaskFragment(), "taskFrag").commit();
        bottomNavigation.setOnTabSelectedListener(this);
    }

    /*Makes the bottom navigation bar*/
    private void makeNav(){
        //initialize bottomNavigation Bar
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        //create the tabs, giving them titles and icons
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.nav_tasks), R.drawable.ic_tasks);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.nav_social), R.drawable.ic_social);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.nav_settings), R.drawable.ic_settings_black_24dp);
        //add tabs to navigation bar
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        //customize color and look
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        //load up first tab when opening the app
        bottomNavigation.setCurrentItem(0);
    }

    /* Handles switching between bottom navigation tabs*/
    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        TaskFragment taskFrag = new TaskFragment();
        SocialFragment socialFrag = new SocialFragment();
        SettingsFragment settingsFrag = new SettingsFragment();
        switch(position){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, taskFrag).commit();

                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, socialFrag).commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, settingsFrag).commit();
                break;
        }
        return true;
    }

}
