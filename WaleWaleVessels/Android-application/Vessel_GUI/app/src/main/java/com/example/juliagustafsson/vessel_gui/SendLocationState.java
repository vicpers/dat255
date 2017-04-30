package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import ServiceEntities.ArrivalLocation;
import ServiceEntities.DepartureLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ReferenceObject;
import ServiceEntities.TimeType;

public class SendLocationState extends AppCompatActivity implements View.OnClickListener {
    private HashMap<TimeType, String> timeMap;
    private HashMap<ReferenceObject, String> refObjMap;
    private HashMap<LocationType, String> locationMap;
    private Spinner spinnerTimeType;
    private Spinner spinnerRefObj;
    private Spinner spinnerArrLoc;
    private Spinner spinnerDepLoc;
    private String selectedTimeType;
    private String selectedRefObj;
    private String selectedArrLoc;
    private String selectedDepLoc;
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_location_state);

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

        // Spinner for selecting TimeType
        timeMap = TimeType.toMap();
        ArrayList<String> timeTypes = new ArrayList<String>(timeMap.values());
        Collections.sort(timeTypes);
        ArrayAdapter<String> adapterTimeType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeTypes);
        adapterTimeType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeType = (Spinner) findViewById(R.id.spinnerTimeType);
        spinnerTimeType.setAdapter(adapterTimeType);
        spinnerTimeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeType = spinnerTimeType.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Spinner for selecting ReferenceObject
        refObjMap = ReferenceObject.toMap();
        ArrayList<String> referenceObjects = new ArrayList<String>(refObjMap.values());
        Collections.sort(referenceObjects);
        spinnerRefObj = (Spinner) findViewById(R.id.spinnerReference);
        ArrayAdapter<String> adapterRefObj = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, referenceObjects);
        adapterRefObj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRefObj.setAdapter(adapterRefObj);
        spinnerRefObj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRefObj = spinnerRefObj.getSelectedItem().toString();
                Log.wtf("REFERENCE OBJECT", selectedRefObj);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Spinner for selecting ArrivalLocationType
        locationMap = LocationType.toMap();
        ArrayList<String> locationTypes = new ArrayList<String>(locationMap.values());
        Collections.sort(locationTypes);
        spinnerArrLoc = (Spinner) findViewById(R.id.spinnerArrival);
        ArrayAdapter<String> adapterArrLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationTypes);
        adapterArrLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrLoc.setAdapter(adapterArrLoc);
        spinnerArrLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArrLoc = spinnerArrLoc.getSelectedItem().toString();
                Log.wtf("ARRIVAL LOCATION", selectedArrLoc);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Spinner for selecting DepartureLocationType
        spinnerDepLoc = (Spinner) findViewById(R.id.spinnerDeparture);
        ArrayAdapter<String> adapterDepLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationTypes);
        adapterDepLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepLoc.setAdapter(adapterDepLoc);
        spinnerDepLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepLoc = spinnerDepLoc.getSelectedItem().toString();
                Log.wtf("DEPARTURE LOCATION", selectedDepLoc);
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

    //TODO Kopierade in från Send_ETA, kraschar hela skiten just "java.lang.IllegalStateException: Could not find method sendNewETA(View)"
    public void sendNewLocationState(View v) {
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

        //TODO Verkar som att det skickas LocationStates med antingen en arrival (då är departure = null), eller en departure (då är arrival = null). Hur Lösa?

        //Creates location-objects based on the selected LocationTypes from the spinners
        Location arrLocObj = new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.fromString(selectedArrLoc));
        Location depLocObj = new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.fromString(selectedDepLoc));
        ArrivalLocation arrLoc = new ArrivalLocation(null, arrLocObj);
        DepartureLocation depLoc = new DepartureLocation(depLocObj, null);

        //Creates an LocationState based on the selected input.
        LocationState locState = new LocationState(selectedRefObj.toUpperCase(), formattedTime, selectedTimeType.toUpperCase(), arrLoc, depLoc);
        PortCallMessage pcmObj = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                "VesselApplicationETAView",
                locState);
        AMSS amss = new AMSS(pcmObj);
        String locStateResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        TextView locStateResultView = (TextView) findViewById(R.id.etaConfirmView);
        locStateResultView.setText("Location State-status: " + locStateResult);

    }
}



