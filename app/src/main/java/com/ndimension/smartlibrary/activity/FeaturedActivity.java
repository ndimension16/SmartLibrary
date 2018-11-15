package com.ndimension.smartlibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.adapter.HomePagerAdapter;
import com.ndimension.smartlibrary.fragment.MonthlyFeaturedFragment;
import com.ndimension.smartlibrary.fragment.OthersFeaturedFragment;
import com.ndimension.smartlibrary.fragment.QuarterlyFeaturedFragment;
import com.ndimension.smartlibrary.fragment.YearlyFeaturedFragment;

public class FeaturedActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private HomePagerAdapter homePagerAdapter;
    private TabLayout tabLayout;

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        initialize();
        peformAction();
    }

    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("Featured Book");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        mViewPager =  (ViewPager)findViewById(R.id.container);
        tabLayout=  (TabLayout)findViewById(R.id.tabs);

        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        homePagerAdapter.addFragment(new MonthlyFeaturedFragment(), "Monthly");
        homePagerAdapter.addFragment(new QuarterlyFeaturedFragment(), "Quarterly");
        homePagerAdapter.addFragment(new YearlyFeaturedFragment(), "Yearly");
        homePagerAdapter.addFragment(new OthersFeaturedFragment(), "Others");
        mViewPager.setAdapter(homePagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void peformAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
