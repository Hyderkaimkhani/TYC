package com.example.hyder.tuc;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import TabView.TabsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    Global global;
    // Tab titles
    private String[] tabs = { "Add Member", "View Members", "Add Task" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //getSupportActionBar().setDisplayShowTitleEnabled(false);


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Add Member"));
        tabLayout.addTab(tabLayout.newTab().setText("View Member"));
        tabLayout.addTab(tabLayout.newTab().setText("Add task"));
        tabLayout.addTab(tabLayout.newTab().setText("View task"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initilization
      //  getApplication().setTheme(R.style.HoloTheme);
        viewPager = (ViewPager) findViewById(R.id.pager);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        global = (Global) getApplicationContext();
        global.setActivity(this);
        global.setContext(this);
        global.GetUserInfo();
       // glGetAllEmployees()

/*

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
*/

    }




}
