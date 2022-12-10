package com.example.societysecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileInputStream fis = null;
        try {
            fis = openFileInput("login");
        }catch (Exception e){
            Intent i =new Intent(this, NewUser.class);
            Toast.makeText(this, "First create a New Admin Account\nAdd Residents Information", Toast.LENGTH_LONG).show();
            startActivity(i);
        }
        ImageView settings=findViewById(R.id.settings);
        ImageView login=findViewById(R.id.login);

        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("value",false);
        if (isNightMode) {
            settings.setColorFilter(ContextCompat.getColor(this, R.color.white));
            login.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }

        Button email_btn = findViewById(R.id.email_button);
        Button call_btn = findViewById(R.id.call_button);

        EditText flat_no=findViewById(R.id.flatno);
        EditText block_no=findViewById(R.id.blockno);

        email_btn.setOnClickListener(view -> {
            String block = block_no.getText().toString();
            String flat = flat_no.getText().toString();
            String owneremail = "";

            if(flat.equals("") || block.equals("")){
                if(block.equals("")){
                    block_no.setError("This cannot be empty");
                }
                if(flat.equals("")) {
                    flat_no.setError("This cannot be empty");
                }
            }

            else {

                FileInputStream fis1 = null;
                try {
                    fis1 = openFileInput("flat_info");
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis1, StandardCharsets.UTF_8);
                    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        boolean f = false;
                        String mLine;
                        while ((mLine = reader.readLine()) != null) {
                            String[] info = mLine.split(",");
                            if (flat.equalsIgnoreCase(info[1]) && block.equalsIgnoreCase(info[0])) {
                                f = true;
                                owneremail=info[2];
                                break;
                            }
                        }
                        if (!f) {
                            flat_no.setError("Enter the correct value");
                            flat_no.setText("");
                            block_no.setError("Enter the correct value");
                            block_no.setText("");
                        } else {
                            Intent i = new Intent(this, email.class);
                            Bundle extras = new Bundle();
                            extras.putString("block", block);
                            extras.putString("flat", flat);
                            extras.putString("email", owneremail);
                            i.putExtras(extras);
                            startActivity(i);
                            flat_no.setText("");
                            block_no.setText("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch(Exception e){
                    boolean flag=false;
                    FileInputStream fis2 = null;
                    try {
                        fis2 = openFileInput("login");
                    }catch (Exception e1){
                        flag=true;
                    }
                    if(flag) {
                        Intent i = new Intent(this, NewUser.class);
                        Toast.makeText(this, "First create a New Admin Account\nAdd Residents Information", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                    else{
                        Intent i =new Intent(this, LogIn.class);
                        Toast.makeText(this, "Log in and Add Residents Information", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                }
            }
        });

        call_btn.setOnClickListener(view -> {
            String block = block_no.getText().toString();
            String flat = flat_no.getText().toString();
            String ownerphone="";

            if(flat.equals("") || block.equals("")){
                if(block.equals("")){
                    block_no.setError("This cannot be empty");
                }
                if(flat.equals("")) {
                    flat_no.setError("This cannot be empty");
                }
            }

            else {

                FileInputStream fis1 = null;
                try {
                    fis1 = openFileInput("flat_info");
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis1, StandardCharsets.UTF_8);
                    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        boolean f = false;
                        String mLine;
                        while ((mLine = reader.readLine()) != null) {
                            String[] info = mLine.split(",");
                            if (flat.equalsIgnoreCase(info[1]) && block.equalsIgnoreCase(info[0])) {
                                f = true;
                                ownerphone=info[3];
                                break;
                            }
                        }
                        if (!f) {
                            flat_no.setError("Enter the correct value");
                            flat_no.setText("");
                            block_no.setError("Enter the correct value");
                            block_no.setText("");
                        } else {
                            Intent i = new Intent(this, Call.class);
                            Bundle extras = new Bundle();
                            extras.putString("block", block);
                            extras.putString("flat", flat);
                            extras.putString("phone", ownerphone);
                            i.putExtras(extras);
                            startActivity(i);
                            flat_no.setText("");
                            block_no.setText("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch(Exception e){
                    boolean flag=false;
                    FileInputStream fis2 = null;
                    try {
                        fis2 = openFileInput("login");
                    }catch (Exception e1){
                        flag=true;
                    }
                    if(flag) {
                        Intent i = new Intent(this, NewUser.class);
                        Toast.makeText(this, "First create a New Admin Account\nAdd Residents Information", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                    else{
                        Intent i =new Intent(this, LogIn.class);
                        Toast.makeText(this, "Log in and Add Residents Information", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                }
            }
        });

        settings.setOnClickListener(view -> {
            Intent i=new Intent(this,Settings.class);
            startActivity(i);
        });

        login.setOnClickListener(view -> {
            Intent i=new Intent(this,LogIn.class);
            startActivity(i);
        });
    }
}
