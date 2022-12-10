package com.example.societysecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ImageView logout = findViewById(R.id.logout_admin);
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("value",false);
        if (isNightMode) {
            logout.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }

        Button add = findViewById(R.id.addResident);
        Button change = findViewById(R.id.changeResident);
        Button visit = findViewById(R.id.visitList);
        Button resident = findViewById(R.id.residentList);

        add.setOnClickListener(view -> {
            Intent i =new Intent(this, AddResident.class);
            startActivity(i);
        });
        change.setOnClickListener(view -> {
            Intent i =new Intent(this, ChangeResident.class);
            startActivity(i);
        });
        visit.setOnClickListener(view -> {
            Intent i=new Intent(this, VisitorsList.class);
            i.putExtra("flag","true");
            startActivity(i);
        });
        resident.setOnClickListener(view -> {
            Intent i = new Intent(this, ResidentList.class);
            startActivity(i);
        });


        logout.setOnClickListener(view -> {
            Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(this, LogIn.class);
            startActivity(i);
            finish();
        });
    }
}