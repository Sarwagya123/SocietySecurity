package com.example.societysecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class AddResident extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resident);

        Button add = findViewById(R.id.changebtn);

        add.setOnClickListener(view -> {

            EditText bl = findViewById(R.id.newBlock);
            EditText fl = findViewById(R.id.newFlat);
            EditText em = findViewById(R.id.newEmail);
            EditText ph = findViewById(R.id.newPhone);

            String block = bl.getText().toString().trim();
            String flat = fl.getText().toString().trim();
            String email = em.getText().toString().trim();
            String phone = ph.getText().toString().trim();

            if(block.equalsIgnoreCase("") || flat.equalsIgnoreCase("") || email.equalsIgnoreCase("") || !email.contains("@") || phone.equalsIgnoreCase("") || phone.length()!=10){
                if(block.equalsIgnoreCase("")){
                    bl.setError("This cannot be empty");
                }
                if(flat.equalsIgnoreCase("")){
                    fl.setError("This cannot be empty");
                }
                if(email.equalsIgnoreCase("")){
                    em.setError("This cannot be empty");
                }
                else if(!email.contains("@")){
                    em.setError("Enter a valid email");
                    em.setText("");
                }
                if(phone.equalsIgnoreCase("")){
                    ph.setError("This cannot be empty");
                }
                else if(phone.length()!=10){
                    ph.setError("Enter a valid phone no.");
                    ph.setText("");
                }
            }
            else{

                boolean f = checkResident(block, email, phone, flat);

                if(!f) {
                    bl.setText("");
                    fl.setText("");
                    em.setText("");
                    ph.setText("");

                    Toast.makeText(this, "Resident Info Added", Toast.LENGTH_SHORT).show();
                    String input = block + "," + flat + "," + email +","+ phone + "\n";
                    String filename = "flat_info";
                    try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_APPEND)) {
                        fos.write(input.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }

        });
    }

    public boolean checkResident(String block, String email, String ph, String flat){
        FileInputStream fis = null;
        boolean f=false;
        try {
            fis = openFileInput("flat_info");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String mLine;
                while ((mLine = reader.readLine()) != null){
                    String[] info = mLine.split(",");
                    if (block.equalsIgnoreCase(info[0]) || email.equalsIgnoreCase(info[2]) || ph.equalsIgnoreCase(info[3])) {
                        if(block.equalsIgnoreCase(info[0])){
                            if(flat.equalsIgnoreCase(info[1])){
                                EditText flat_no = findViewById(R.id.newFlat);
                                flat_no.setError("Duplicate Value");
                                flat_no.setText("");
                                f=true;
                            }
                        }
                        if(email.equalsIgnoreCase(info[2]) || ph.equalsIgnoreCase(info[3])) {
                            if(email.equalsIgnoreCase(info[2])){
                                EditText em=findViewById(R.id.newEmail);
                                em.setError("Duplicate Value");
                                em.setText("");
                            }
                            if(ph.equalsIgnoreCase(info[3])){
                                EditText ph_no = findViewById(R.id.newPhone);
                                ph_no.setError("Duplicate Value");
                                ph_no.setText("");
                            }
                            f=true;
                            break;
                        }
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