package com.example.juliagustafsson.vessel_gui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Send_ETA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_eta);
    }

    public void sendNewETA(View view) {
        //TODO Kolla inputs så att de innehåller giltiga värden (Datum och tid)
        //TODO kod för att uppdatera senast skickat ETA

        EditText dateText = (EditText) findViewById(R.id.editText2);
        EditText timeText = (EditText) findViewById(R.id.editText);
        String date = dateText.getText().toString();
        String time = timeText.getText().toString();


        if(!isValidDate(date)){
            dateText.setText("Invalid date");
            dateText.setTextColor(Color.RED);
        }
        if(!isValidTime(time)){
            timeText.setText("Invalid time");
            timeText.setTextColor(Color.RED);
        }
    }




    public static boolean isValidDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    
    public static boolean isValidTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(time.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
