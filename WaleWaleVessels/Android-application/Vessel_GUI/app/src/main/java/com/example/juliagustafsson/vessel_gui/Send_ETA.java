package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import RESTServices.AMSS;
import ServiceEntities.ArrivalLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;


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

        timePicker = new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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

    public void sendNewETA(View v) {

        // Gets strings that represent the date and time from different Edit-fields.
        String etaDate = dateEditText.getText().toString();
        String etaTime = timeEditText.getText().toString();

        // Converts the date and time from input into date on the form "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        // which PortCDM requires.
        SimpleDateFormat etaOutput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat etaInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = etaInput.parse(etaDate + " " + etaTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = etaOutput.format(date);

        Location locObj = new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.TRAFFIC_AREA);
        ArrivalLocation arrLoc = new ArrivalLocation(null, locObj);
        LocationState locState = new LocationState("VESSEL", formattedTime, "ESTIMATED", arrLoc, null);
        PortCallMessage pcmObj = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                                                     "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                     "VesselApplicationETAView",
                                                     locState);
        AMSS amss = new AMSS(pcmObj);
        String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        TextView etaResultView = (TextView) findViewById(R.id.etaConfirmView);
        etaResultView.setText("ETA-status: " + etaResult);
    }

}