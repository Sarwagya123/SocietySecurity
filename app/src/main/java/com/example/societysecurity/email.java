package com.example.societysecurity;

import android.content.Context;
import android.content.Intent;
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

public class email extends AppCompatActivity {

    private static final String emailsubject="Requesting Access to the Society Premises";
    private static StringBuilder emailbody= new StringBuilder("Respected Sir/Maam, \n\n " +
            "A person is requesting access to enter the society premises so as to visit your home. \n\n" +
            "Kindly reply to this mail with a 'Yes' or 'No' to grant or deny access respectively. The information of the visitor is given below - \n\n");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String block = extras.getString("block").trim();
        String flat = extras.getString("flat").trim();
        String emailsend= extras.getString("email").trim();

        Button btn=findViewById(R.id.email_btn);

        EditText nameView=findViewById(R.id.nameVisitor);
        EditText phoneView=findViewById(R.id.phoneVisitor);
        EditText workView=findViewById(R.id.workVisitor);

        btn.setOnClickListener(view -> {

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
                emailbody.append("Name : ").append(name).append("\nPh. No. : ").append(phone).append("\nWork : ").append(work);

                try {
                    saveData(name, phone, work, block, flat);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_SEND);

                // add three fields to intent using putExtra function
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailbody.toString());

                // set type of intent
                intent.setType("message/rfc822");

                // startActivity with intent with chooser as Email client using createChooser function
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

                nameView.setText("");
                phoneView.setText("");
                workView.setText("");

                emailbody.delete(0,emailbody.capacity());
                emailbody.append("Respected Sir/Maam, \n\n " +
                        "A person is requesting access to enter the society premises so as to visit your home. \n\n" +
                        "Kindly reply to this mail with a 'Yes' or 'No' to grant or deny access respectively. The information of the visitor is given below - \n\n");

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