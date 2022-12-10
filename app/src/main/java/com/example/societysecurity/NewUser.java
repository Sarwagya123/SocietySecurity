package com.example.societysecurity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class NewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);


        ImageView passToggle = findViewById(R.id.passToggle);
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("value",false);
        if (isNightMode) {
            passToggle.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }

        Spinner blockSpinner=findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.acctypes, R.layout.simple_spinner_item2);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item2);

        blockSpinner.setAdapter(adapter);

        EditText user = findViewById(R.id.user);
        EditText pass = findViewById(R.id.pass);

        Button register = findViewById(R.id.registerbtn);

        register.setOnClickListener(view -> {
            String acctype = blockSpinner.getSelectedItem().toString().trim();

            String username = user.getText().toString().trim();
            String password = pass.getText().toString().trim();

            boolean numberFlag=false, capitalFlag=false, lowerCaseFlag=false;
            for(int i=0;i < password.length();i++) {
                char ch = password.charAt(i);
                if( Character.isDigit(ch)) {
                    numberFlag = true;
                }
                else if (Character.isUpperCase(ch)) {
                    capitalFlag = true;
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true;
                }
                if(numberFlag && capitalFlag && lowerCaseFlag) {
                    break;
                }
            }
            boolean actype = checkAccType("Admin");
            if(!actype && acctype.equalsIgnoreCase("Others")){
                Toast.makeText(this, "First create an Admin Account", Toast.LENGTH_SHORT).show();
            }
            else if(username.equalsIgnoreCase("")|| password.equalsIgnoreCase("") || password.length()<8 || !numberFlag || !capitalFlag || !lowerCaseFlag){
                if(username.equalsIgnoreCase("")){
                    user.setError("This cannnot be empty");
                }
                if(password.equalsIgnoreCase("")){
                    pass.setError("This cannot be empty");
                }
                else if(password.length()<8){
                    pass.setError("Should be of minimum 8 characters");
                    pass.setText("");
                }
                else{
                    if(!capitalFlag){
                        pass.setError("Should contain an Uppercase Letter");
                    }
                    if(!numberFlag){
                        pass.setError("Should contain a Number");
                    }
                    if(!lowerCaseFlag){
                        pass.setError("Should contain a Lowercase Letter");
                    }
                }
            }
            else{
                user.setText("");
                pass.setText("");

                boolean f =checkUser(username);

                if(!f) {

                    Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
                    String input = username + "," + password + "," + acctype + "\n";
                    String filename = "login";
                    try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_APPEND)) {
                        fos.write(input.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(this, LogIn.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(this, "Already a user", Toast.LENGTH_SHORT).show();
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

    public boolean checkUser(String username){
        FileInputStream fis = null;
        boolean f=false;

        try {
            fis = openFileInput("login");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String mLine;
                while ((mLine = reader.readLine()) != null){
                    String[] info = mLine.split(",");
                    if (username.equalsIgnoreCase(info[0])) {
                        f=true;
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            f=false;
        }
        return f;
    }

    public boolean checkAccType(String accType){
        FileInputStream fis = null;
        boolean f=false;

        try {
            fis = openFileInput("login");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String mLine;
                while ((mLine = reader.readLine()) != null){
                    String[] info = mLine.split(",");
                    if (accType.equalsIgnoreCase(info[2])) {
                        f=true;
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            f=false;
        }
        return f;
    }
}