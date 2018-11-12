package com.ndimension.smartlibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.CreativePermission;
import com.ndimension.smartlibrary.utility.NetworkConnectionCheck;
import com.ndimension.smartlibrary.utility.Pref;

public class SplashActivity extends AppCompatActivity {
    private NetworkConnectionCheck connectionCheck;
    private static final int PERMISSION_ALL = 100;
    private CreativePermission myPermission;
    Pref pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();

        CheckPermission();
    }



    private void initialize(){
        pref = new Pref(this);
        connectionCheck = new NetworkConnectionCheck(this);
        myPermission = new CreativePermission(this,PERMISSION_ALL);
    }

    private void CheckPermission() {
        if (!myPermission.hasPermissions()) {
            myPermission.reqPermisions();
        } else {
            setup();
        }
    }

    private void setup(){
            showSplash();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_ALL) {
            setup();

        }
    }


    private void showSplash(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (!pref.getUserId().equals("")) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }


            }
        }, 3000);
    }


}
