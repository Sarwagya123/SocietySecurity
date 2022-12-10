package com.example.societysecurity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchCompat darkSwitch=findViewById(R.id.dark);
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        darkSwitch.setChecked(sharedPreferences.getBoolean("value",false));

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
            editor.putBoolean("value", true);
            editor.apply();
            darkSwitch.setChecked(true);
            darkSwitch.setOnClickListener(View -> {
                editor.putBoolean("value", false);
                editor.apply();
                darkSwitch.setChecked(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            });
        }
        else
        {
                // When switch unchecked
            SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
            editor.putBoolean("value", false);
            editor.apply();
            darkSwitch.setChecked(false);
            darkSwitch.setOnClickListener(View -> {
                editor.putBoolean("value", true);
                editor.apply();
                darkSwitch.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            });
        }
    }
}