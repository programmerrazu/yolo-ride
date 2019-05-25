package com.intense.yolo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.intense.yolo.R;
import com.intense.yolo.activity.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        SplashScreenActivity.this.startActivity(intent);
        SplashScreenActivity.this.overridePendingTransition(0, 0);
        SplashScreenActivity.this.finish();
    }

    @Override
    public void onBackPressed() {

    }
}