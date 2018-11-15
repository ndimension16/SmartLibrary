package com.ndimension.smartlibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.Pref;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SwitchCompat switchVibrate,switchSound,switchPush;
    private Pref pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
        peformAction();
    }

    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("Settings");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        pref = new Pref(this);

        switchVibrate = (SwitchCompat)findViewById(R.id.switchVibrate);
        switchSound = (SwitchCompat)findViewById(R.id.switchSound);
        switchPush = (SwitchCompat)findViewById(R.id.switchPush);

        if (!TextUtils.isEmpty(pref.getVibrate())){
            if (pref.getVibrate().equals("true")){
                switchVibrate.setChecked(true);
            }else {
                switchVibrate.setChecked(false);
            }
        }else {
            switchVibrate.setChecked(true);
        }

        if (!TextUtils.isEmpty(pref.getSound())){
            if (pref.getSound().equals("true")){
                switchSound.setChecked(true);
            }else {
                switchSound.setChecked(false);
            }
        }else {
            switchSound.setChecked(true);
        }

        if (!TextUtils.isEmpty(pref.getBlockNotification())){
            if (pref.getBlockNotification().equals("true")){
                switchPush.setChecked(true);
            }else {
                switchPush.setChecked(false);
            }
        }else {
            switchPush.setChecked(false);
        }
    }

    private void peformAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        switchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    pref.saveVibrate("true");
                }else {
                    pref.saveVibrate("false");
                }
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    pref.saveSound("true");
                }else {
                    pref.saveSound("false");
                }
            }
        });

        switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    pref.saveBlockNotification("true");
                }else {
                    pref.saveBlockNotification("false");
                }
            }
        });
    }
}
