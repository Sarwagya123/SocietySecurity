package com.example.societysecurity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class VisitorsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_list);
        ImageView bin = findViewById(R.id.bin);

        Intent i = getIntent();
        String f = i.getStringExtra("flag");
        if(f.equalsIgnoreCase("true")){
            bin.setVisibility(View.VISIBLE);
        }
        else{
            bin.setVisibility(View.INVISIBLE);
        }

        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("value",false);
        if (isNightMode) {
            bin.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }


        FileInputStream fis = null;
        try {
            fis = openFileInput("visitors");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {

                String line = reader.readLine();
                if(line.equalsIgnoreCase("")){
                    stringBuilder.append("No data to display");
                }
                else {
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = reader.readLine();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                stringBuilder.append("No data to display");
            }
            finally {
                String contents = stringBuilder.toString();
                ((TextView)findViewById(R.id.display)).setText(contents);
                if(contents.equalsIgnoreCase("No data to display")) {
                    bin.setOnClickListener(view -> {
                        Toast.makeText(this, "No data to delete", Toast.LENGTH_SHORT).show();
                    });
                }
                else {
                    bin.setOnClickListener(view -> {
                        Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        String message = "No data to display";
                        ((TextView) findViewById(R.id.display)).setText(message);
                        delete();
                        finish();
                    });
                }
            }

        } catch (Exception e) {
            String contents = "No data to display";
            ((TextView)findViewById(R.id.display)).setText(contents);
            bin.setOnClickListener(view -> {
                Toast.makeText(this, "No data to delete", Toast.LENGTH_SHORT).show();
            });
        }

    }

    public void delete(){
        String visitor = "";
        String filename = "visitors";
        try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(visitor.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}