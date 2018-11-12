package com.ndimension.smartlibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.Pref;

public class SplashActivity extends AppCompatActivity {
    Pref pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();

        showSplash();
    }

    private void initialize(){
        pref = new Pref(this);
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
