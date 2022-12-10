package com.example.societysecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResidentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_list);

        FileInputStream fis = null;
        try {
            fis = openFileInput("flat_info");
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
                        String info[] = line.split(",");
                        stringBuilder.append(info[0]).append(" ").append(info[1]).append('\n').append(info[2]).append('\n').append(info[3]).append('\n').append('\n');
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
                ((TextView)findViewById(R.id.display2)).setText(contents);
            }

        } catch (Exception e) {
            String contents = "No data to display";
            ((TextView)findViewById(R.id.display2)).setText(contents);
        }
    }
}