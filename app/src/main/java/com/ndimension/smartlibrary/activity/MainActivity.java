package com.ndimension.smartlibrary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.adapter.HomePagerAdapter;
import com.ndimension.smartlibrary.fragment.MonthlyFragment;
import com.ndimension.smartlibrary.utility.Pref;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout dlMain;
    private TextView tvHome,tvFeatured,tvScanner,tvShare,tvSettings,tvLogout;
    private ImageView imgHome,imgFeatured,imgScanner,imgShare,imgSettings,imgLogout;
    private LinearLayout llHome,llFeatured,llScanner,llShare,llSettings,llLogout;
    private LinearLayout llMonthly,llQuarterly,llYearly,llOthers;
    private TextView tvMonthly,tvQuarterly,tvYearly,tvOthers;
    private View vMonthly,vQuarterly,vYearly,vOthers;
    FragmentManager fragmentmanager;

    private TextView tvName,tvEmail;
    private Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        peformAction();
        loadState();
    }

    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("Book Shelves");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);

        pref = new Pref(this);

        dlMain = findViewById(R.id.dlMain);

        llHome = (LinearLayout)findViewById(R.id.llHome);
        llFeatured = (LinearLayout)findViewById(R.id.llFeatured);
        llScanner = (LinearLayout)findViewById(R.id.llScanner);
        llShare = (LinearLayout)findViewById(R.id.llShare);
        llSettings = (LinearLayout)findViewById(R.id.llSettings);
        llLogout = (LinearLayout)findViewById(R.id.llLogout);

        tvHome = (TextView)findViewById(R.id.tvHome);
        tvFeatured = (TextView)findViewById(R.id.tvFeatured);
        tvScanner = (TextView)findViewById(R.id.tvScanner);
        tvShare = (TextView)findViewById(R.id.tvShare);
        tvSettings = (TextView)findViewById(R.id.tvSettings);
        tvLogout = (TextView)findViewById(R.id.tvLogout);

        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgFeatured = (ImageView)findViewById(R.id.imgFeatured);
        imgScanner = (ImageView)findViewById(R.id.imgScanner);
        imgShare = (ImageView)findViewById(R.id.imgShare);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        imgLogout = (ImageView)findViewById(R.id.imgLogout);

        llMonthly = findViewById(R.id.llMonthly);
        llQuarterly = findViewById(R.id.llQuarterly);
        llYearly = findViewById(R.id.llYearly);
        llOthers = findViewById(R.id.llOthers);

        tvMonthly = findViewById(R.id.tvMonthly);
        tvQuarterly = findViewById(R.id.tvQuarterly);
        tvYearly = findViewById(R.id.tvYearly);
        tvOthers = findViewById(R.id.tvOthers);

        vMonthly = findViewById(R.id.vMonthly);
        vQuarterly = findViewById(R.id.vQuarterly);
        vYearly = findViewById(R.id.vYearly);
        vOthers = findViewById(R.id.vOthers);

        fragmentmanager= getSupportFragmentManager();

        tvName = (TextView)findViewById(R.id.tvName);
        tvEmail = (TextView)findViewById(R.id.tvEmail);

        tvName.setText(pref.getName());
        tvEmail.setText(pref.getEmail());


    }

    private void peformAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dlMain.isDrawerOpen(Gravity.START)) {
                    dlMain.openDrawer(Gravity.START);
                } else {
                    dlMain.closeDrawer(Gravity.START);
                }
            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.colorAccent);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);
            }
        });

        llFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.colorAccent);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);

               // startActivity(new Intent(MainActivity.this,BookActivity.class));
            }
        });

        llScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.colorAccent);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.colorAccent);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);
            }
        });

        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.colorAccent);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.colorAccent);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.colorAccent));

                dlMain.closeDrawer(Gravity.START);

                pref.saveUserId("");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#0076fe"));
                tvQuarterly.setTextColor(Color.parseColor("#807f80"));
                tvYearly.setTextColor(Color.parseColor("#807f80"));
                tvOthers.setTextColor(Color.parseColor("#807f80"));

                vMonthly.setVisibility(View.VISIBLE);
                vQuarterly.setVisibility(View.INVISIBLE);
                vYearly.setVisibility(View.INVISIBLE);
                vOthers.setVisibility(View.INVISIBLE);

                loadState();


            }
        });

        llQuarterly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#807f80"));
                tvQuarterly.setTextColor(Color.parseColor("#0076fe"));
                tvYearly.setTextColor(Color.parseColor("#807f80"));
                tvOthers.setTextColor(Color.parseColor("#807f80"));

                vMonthly.setVisibility(View.INVISIBLE);
                vQuarterly.setVisibility(View.VISIBLE);
                vYearly.setVisibility(View.INVISIBLE);
                vOthers.setVisibility(View.INVISIBLE);

                loadState();


            }
        });

        llYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#807f80"));
                tvQuarterly.setTextColor(Color.parseColor("#807f80"));
                tvYearly.setTextColor(Color.parseColor("#0076fe"));
                tvOthers.setTextColor(Color.parseColor("#807f80"));

                vMonthly.setVisibility(View.INVISIBLE);
                vQuarterly.setVisibility(View.INVISIBLE);
                vYearly.setVisibility(View.VISIBLE);
                vOthers.setVisibility(View.INVISIBLE);

                loadState();


            }
        });

        llOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#807f80"));
                tvQuarterly.setTextColor(Color.parseColor("#807f80"));
                tvYearly.setTextColor(Color.parseColor("#807f80"));
                tvOthers.setTextColor(Color.parseColor("#0076fe"));

                vMonthly.setVisibility(View.INVISIBLE);
                vQuarterly.setVisibility(View.INVISIBLE);
                vYearly.setVisibility(View.INVISIBLE);
                vOthers.setVisibility(View.VISIBLE);

                loadState();


            }
        });


    }

    private void loadState(){
        FragmentTransaction fragmenttransaction= fragmentmanager.beginTransaction();
        MonthlyFragment cf=new MonthlyFragment();
        fragmenttransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_right);
        fragmenttransaction.replace(R.id.flMain,cf,"Monthly Fragment");
        fragmenttransaction.commit();
    }




}
