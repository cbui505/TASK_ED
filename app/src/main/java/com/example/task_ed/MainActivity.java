package com.example.task_ed;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new TaskFragment()).commit();
        bottomNavigation.setOnTabSelectedListener(this);
    }

    /*Makes the bottom navigation bar*/
    private void makeNav(){
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.nav_tasks), R.drawable.ic_tasks);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.nav_social), R.drawable.ic_social);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.nav_settings), R.drawable.ic_settings_black_24dp);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setCurrentItem(0);
    }

    /* Handles switching between bottom navigation tabs*/
    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        switch(position){
            case 0:
                TaskFragment taskFrag = new TaskFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, taskFrag).commit();
                break;
            case 1:
                SocialFragment socialFrag = new SocialFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, socialFrag).commit();
                break;
            default:
                SettingsFragment settingsFrag = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, settingsFrag).commit();
                break;
        }
        return true;
    }
}
