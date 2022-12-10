package com.example.societysecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ImageView passToggle = findViewById(R.id.passToggle);
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("value",false);
        if (isNightMode) {
            passToggle.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }

        EditText user=(findViewById(R.id.username));
        EditText pass=(findViewById(R.id.password));

        Button loginbtn = findViewById(R.id.loginbtn);
        Button newuserbtn = findViewById(R.id.newuserbtn);

        newuserbtn.setOnClickListener(view ->{
            Intent i=new Intent(this, NewUser.class);
            startActivity(i);
            finish();
        });

        loginbtn.setOnClickListener(view-> {
            String password = pass.getText().toString().trim();
            String username = user.getText().toString().trim();

            if(username.equalsIgnoreCase("")|| password.equalsIgnoreCase("")){
                if(username.equalsIgnoreCase("")){
                    user.setError("This cannnot be empty");
                }
                if(password.equalsIgnoreCase("")){
                    pass.setError("This cannot be empty");
                }
            }
            else {
                pass.setText("");
                user.setText("");

                FileInputStream fis = null;
                try {
                    fis = openFileInput("login");
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis, StandardCharsets.UTF_8);
                    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        boolean f=false, f_admin=false;
                        String mLine;
                        while ((mLine = reader.readLine()) != null){
                            String[] info = mLine.split(",");
                            if (username.equals(info[0]) && password.equals(info[1])) {
                                if(info[2].equalsIgnoreCase("admin")){
                                    f_admin=true;
                                }
                                f=true;
                                break;
                            }
                        }
                        if(!f){
                            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(f_admin){
                                Toast.makeText(this, "Welcome, "+username, Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(this, Admin.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(this, "Welcome, " + username, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(this, Security.class);
                                startActivity(i);
                                finish();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "New User?\nCreate an Account First", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, NewUser.class);
                    startActivity(i);
//            e.printStackTrace();
                }
            }

        });

        passToggle.setOnClickListener(view -> {
            if(pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                (passToggle).setImageResource(R.drawable.hide_password);

                //Show Password
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                (passToggle).setImageResource(R.drawable.show_password);

                //Hide Password
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });

    }
}