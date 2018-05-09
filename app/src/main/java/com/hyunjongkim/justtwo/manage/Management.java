package com.hyunjongkim.justtwo.manage;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_adapter.ManagementTabAdapter;

public class Management extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_base);

        setToolbar();

// Initializing the TabLayout
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.bg_white));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#FF4500"));

        tabLayout.addTab(tabLayout.newTab().setText("部屋管理"));
        tabLayout.addTab(tabLayout.newTab().setText("個人管理"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

// Initializing ViewPager
        viewPager = findViewById(R.id.pager);

// Creating TabPagerAdapter adapter
        ManagementTabAdapter pagerAdapter = new ManagementTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

// Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    }

// Toolbarの設定
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.manage_toolbar_title);
        }
    }


// Back to main
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_submit:
                break;
        }

        return true;
    }
}
