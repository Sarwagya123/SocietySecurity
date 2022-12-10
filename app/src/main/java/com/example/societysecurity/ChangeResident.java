package com.example.societysecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ChangeResident extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_resident);

        Button change = findViewById(R.id.changebtn);
        EditText newBlock = findViewById(R.id.newBlock);
        EditText newFlat = findViewById(R.id.newFlat);
        EditText newEmail = findViewById(R.id.newEmail);
        EditText newPhone = findViewById(R.id.newPhone);

        change.setOnClickListener(view -> {
            String block = newBlock.getText().toString().trim();
            String flat = newFlat.getText().toString().trim();
            String email = newEmail.getText().toString().trim();
            String phone = newPhone.getText().toString().trim();

            if(block.equalsIgnoreCase("") || flat.equalsIgnoreCase("") || email.equalsIgnoreCase("") || !email.contains("@") || phone.equalsIgnoreCase("") || phone.length()!=10){
                if(block.equalsIgnoreCase("")){
                    newBlock.setError("This cannot be empty");
                }
                if(flat.equalsIgnoreCase("")){
                    newFlat.setError("This cannot be empty");
                }
                if(email.equalsIgnoreCase("")){
                    newEmail.setError("This cannot be empty");
                }
                else if(!email.contains("@")){
                    newEmail.setError("Enter a valid email");
                    newEmail.setText("");
                }
                if(phone.equalsIgnoreCase("")){
                    newPhone.setError("This cannot be empty");
                }
                else if(phone.length()!=10){
                    newPhone.setError("Enter a valid phone no.");
                    newPhone.setText("");
                }
            }
            else{
                checkResident(block, email, phone, flat);
            }
        });
    }

    public void checkResident(String block, String email, String phone, String flat){
        FileInputStream fis = null;
        boolean f=false, f2=false;
        StringBuilder oldLine = new StringBuilder();
        String oldString="";
        try {
            fis = openFileInput("flat_info");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String mLine;

                while ((mLine = reader.readLine()) != null){
                    String[] info = mLine.split(",");
                    oldLine.append(mLine).append("\n");
                    if(block.equalsIgnoreCase(info[0])&&flat.equalsIgnoreCase(info[1])){
                        if((!email.equalsIgnoreCase(info[2]))&&(!phone.equalsIgnoreCase(info[3]))){
                            oldString=info[0]+","+info[1]+","+info[2]+","+info[3];
                            f=true;
                        }
                        else{
                            if(email.equalsIgnoreCase(info[2])){
                                EditText em=findViewById(R.id.newEmail);
                                em.setText("");
                                em.setError("Already a resident");
                            }
                            if(phone.equalsIgnoreCase(info[3])){
                                EditText ph = findViewById(R.id.newPhone);
                                ph.setError("Already a resident");
                                ph.setText("");
                            }
                            f2=true;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Exception!", Toast.LENGTH_SHORT).show();
            f=false;
        }
        if(f){
            String newString = block + "," + flat + "," + email +","+ phone;
            String content = oldLine.toString();
            String input = content.replace(oldString,newString);
            String filename = "flat_info";
            try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(input.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Changed Resident Info", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(!f2){
            Toast.makeText(this, "Kindly add the resident info first", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, AddResident.class);
            startActivity(i);
            finish();
        }
    }
}