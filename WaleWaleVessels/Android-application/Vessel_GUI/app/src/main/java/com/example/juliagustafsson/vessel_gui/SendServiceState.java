package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import RESTServices.AMSS;
import ServiceEntities.Between;
import ServiceEntities.Location;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ReferenceObject;
import ServiceEntities.ServiceObject;
import ServiceEntities.ServiceState;
import ServiceEntities.ServiceTimeSequence;
import ServiceEntities.TimeType;

public class SendServiceState extends AppCompatActivity implements View.OnClickListener {
    private HashMap<String, TimeType> timeMap;
    private HashMap<String, ServiceObject> serviceObjMap;
    private HashMap<String, ServiceTimeSequence> timeSeqMap;
    private HashMap<String, LocationType> locationMap;
    private Spinner spinnerTimeType;
    private Spinner spinnerServiceObj;
    private Spinner spinnerPerfAct;
    private Spinner spinnerTimeSeq;
    private String selectedTimeType = null;
    private String selectedServiceObj = null;
    private String selectedPerfAct = null;
    private String selectedTimeSeq = null;
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_service_state);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateEditText = (EditText) findViewById(R.id.editText2);
        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.requestFocus();

        //Applies fonts
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/BebasKai.otf");
        TextView serviceObject = (TextView) findViewById(R.id.serviceObject);
        serviceObject.setTypeface(typeface);
        TextView timeSeq = (TextView) findViewById(R.id.timeSeq);
        timeSeq.setTypeface(typeface);
        TextView timeType = (TextView) findViewById(R.id.timeType);
        timeType.setTypeface(typeface);

        //Set custom toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Send Service State");
        getSupportActionBar().setHomeButtonEnabled(true);

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


        // Spinner for selecting TimeType
        timeMap = TimeType.toMap();
        ArrayList<String> timeTypes = new ArrayList<String>(timeMap.keySet());
        Collections.sort(timeTypes);
        spinnerTimeType = (Spinner) findViewById(R.id.spinnerTimeType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerTimeType.setAdapter(adapter);
        spinnerTimeType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerTimeType.setPrompt("Select Time Type");
        spinnerTimeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedTimeType = spinnerTimeType.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED TIMETYPE", selectedTimeType);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner for selecting Service Object
        serviceObjMap = ServiceObject.toMap();
        ArrayList<String> serviceObjects = new ArrayList<String>(serviceObjMap.keySet());
        Collections.sort(serviceObjects);
        spinnerServiceObj = (Spinner) findViewById(R.id.spinnerServiceObj);
        ArrayAdapter<String> adapterRefObj = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceObjects);
        adapterRefObj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerServiceObj.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterRefObj,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerServiceObj.setPrompt("Select Service Object");
        spinnerServiceObj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedServiceObj = spinnerServiceObj.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED SERVICE OBJECT", selectedServiceObj);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner for selecting Time Sequence
        timeSeqMap = ServiceTimeSequence.toMap();
        ArrayList<String> timeSequences = new ArrayList<String>(timeSeqMap.keySet());
        Collections.sort(timeSequences);
        spinnerTimeSeq = (Spinner) findViewById(R.id.spinnerTimeSeq);
        ArrayAdapter<String> adapterTimeSeq = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSequences);
        adapterTimeSeq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTimeSeq.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterTimeSeq,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerTimeSeq.setPrompt("Select Time Sequence");
        spinnerTimeSeq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedTimeSeq = spinnerTimeSeq.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED TIME SEQUENCE", selectedTimeSeq);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

    //TODO Kopierade in från Send_ETA och försökt anpassa för hur ett service state ska se ut"
    public void sendNewServiceState(View v) {
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

        Location from = new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.BERTH);
        Location to =  new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.BERTH);
        Between betweenLocations = new Between(from, to);
        ServiceState servState = new ServiceState("DEPARTURE_BERTH", "COMPLETED", "Gothenburg Port", betweenLocations, "urn:mrn:stm:vessel:IMO:9501368");

        PortCallMessage pcmObj = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                "VesselApplicationETAView",
                servState);
        AMSS amss = new AMSS(pcmObj);
        String servStateResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        TextView servStateResultView = (TextView) findViewById(R.id.etaConfirmView);
        servStateResultView.setText("Service State-status: " + servStateResult);

    }
}
