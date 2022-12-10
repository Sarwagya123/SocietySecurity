package com.example.societysecurity;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("value",false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate();
    }
}
