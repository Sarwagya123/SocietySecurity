package com.example.societysecurity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Call extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String block = extras.getString("block").trim();
        String flat = extras.getString("flat").trim();
        String ownerPhone = extras.getString("phone").trim();

                Button call= findViewById(R.id.call_btn);

        EditText nameView=findViewById(R.id.nameVisitor);
        EditText phoneView=findViewById(R.id.phoneVisitor);
        EditText workView=findViewById(R.id.workVisitor);

        call.setOnClickListener(view -> {
            String name = nameView.getText().toString().trim();
            String phone = phoneView.getText().toString().trim();
            String work = workView.getText().toString().trim();

            if(name.equals("") || phone.equals("") || work.equals("") || phone.length()!=10) {

                if (name.equals("")) {
                    nameView.setError("This cannot be empty");
                }
                if (phone.equals("")) {
                    phoneView.setError("This cannot be empty");
                }
                if(phone.length()!=10){
                    phoneView.setError("Enter a valid Phone Number");
                }
                if (work.equals("")) {
                    workView.setError("This cannot be empty");
                }
            }
            else {
                try {
                    saveData(name, phone, work, block, flat);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                String uri = "tel:" + ownerPhone.trim();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(uri));
                startActivity(i);

                nameView.setText("");
                phoneView.setText("");
                workView.setText("");


            }
        });
    }

    public void saveData(String name, String phone, String work, String block, String flat) throws FileNotFoundException {
        Date date = new Date();
        String visitor = date + "\n" + name + "\n" + phone + "\n" + work + "\n" + block + "\n" + flat + "\n" + "\n";
        String filename = "visitors";
        try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_APPEND)) {
            fos.write(visitor.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}