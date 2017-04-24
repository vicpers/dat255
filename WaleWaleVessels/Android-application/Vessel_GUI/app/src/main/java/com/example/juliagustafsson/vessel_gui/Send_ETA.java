package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import HTTPRequest.PCM;

import static android.R.attr.textCheckMark;

public class Send_ETA extends AppCompatActivity implements View.OnClickListener{
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_eta);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateEditText = (EditText) findViewById(R.id.editText2);
        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.requestFocus();

        timeEditText = (EditText) findViewById(R.id.editText);
        timeEditText.setInputType(InputType.TYPE_NULL);

        dateEditText.setOnClickListener(this);

        timeEditText.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year,month,dayOfMonth);
                dateEditText.setText(dateFormat.format(newCalendar.getTime()));
                timeEditText.requestFocus();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        timePicker = new TimePickerDialog(this,/*android.R.style.Theme_Holo_Light_Dialog,*/ new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeEditText.setText( selectedHour + ":" + selectedMinute);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onClick(View v) {
        if (v==dateEditText){
            datePicker.show();
        }
        else if(v==timeEditText){
            timePicker.show();
        }

    }
    /*public void sendNewETA(View v) {
        PCM pcm = new PCM();
        pcm.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dateEditText.getText().toString()));
        pcm.setEndTime("");
        pcm.setId("");
        pcm.setLastUpdate("");
        pcm.setPortUnLocode("");
        pcm.setStartTime("");
        //pcm.setVessel(new Vessel());
        pcm.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));

    }*/

}