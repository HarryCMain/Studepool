package com.main.stdpool;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EasySplashScreen easySplashScreen = new EasySplashScreen(SplashScreen.this)
        .withFullScreen()
        .withTargetActivity(MainActivity.class)
        .withSplashTimeOut(4000)
        .withBackgroundColor(Color.parseColor("#1a1b29"))
        .withHeaderText("")
        .withFooterText("Copyright 2020")
        .withBeforeLogoText("")
        .withLogo(R.drawable.mainlogo)
                .withAfterLogoText("By Main Interactive");

        easySplashScreen.getHeaderTextView().setTextColor(Color.WHITE);
        easySplashScreen.getFooterTextView().setTextColor(Color.WHITE);
        easySplashScreen.getBeforeLogoTextView().setTextColor(Color.WHITE);
        easySplashScreen.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreenView = easySplashScreen.create();


    setContentView(easySplashScreenView);

}

}
