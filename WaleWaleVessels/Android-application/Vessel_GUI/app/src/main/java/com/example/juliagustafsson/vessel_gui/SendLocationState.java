package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import RESTServices.PortCDMServices;
import ServiceEntities.ArrivalLocation;
import ServiceEntities.DepartureLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ReferenceObject;
import ServiceEntities.TimeType;

import static RESTServices.Constants_API.API_ACTUAL_PORT;

public class SendLocationState extends AppCompatActivity implements View.OnClickListener {
    private HashMap<String, TimeType> timeMap;
    private HashMap<String, ReferenceObject> refObjMap;
    private HashMap<String, LocationType> locationMap;
    private ArrayList<LocationType> allowedSubLocations;
    private HashMap<String, Position> portLocMap;
    private Spinner spinnerTimeType;
    private Spinner spinnerRefObj;
    private Spinner spinnerArrOrDep;
    private Spinner spinnerLocationType;
    private Spinner locationSpinner;
    private String selectedTimeType = null;
    private String selectedArrOrDep = null;
    private String selectedLocType = null;
    private String selectedPortLoc = null;
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private LocationState locState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_location_state);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateEditText = (EditText) findViewById(R.id.editText2);
        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.requestFocus();

        //Set custom toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Send Location State");
        getSupportActionBar().setHomeButtonEnabled(true);

        //Applies fonts
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/BebasKai.otf");
        TextView myTextView11 = (TextView) findViewById(R.id.textView11);
        myTextView11.setTypeface(typeface);
        TextView myTextView9 = (TextView) findViewById(R.id.textView9);
        myTextView9.setTypeface(typeface);
        TextView myTextView8 = (TextView) findViewById(R.id.textView8);
        myTextView8.setTypeface(typeface);

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
        ArrayAdapter<String> adapterTimeType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeTypes);
        adapterTimeType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeType = (Spinner) findViewById(R.id.spinnerTimeType);
        /*spinnerTimeType.setAdapter(adapterTimeType);
        spinnerTimeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeType = spinnerTimeType.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        }); */
        spinnerTimeType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterTimeType,
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

        // Spinner for selecting Arrival or Departure
        spinnerArrOrDep = (Spinner) findViewById(R.id.spinnerArrOrDep);
        ArrayList<String> arrivalOrDeparture = new ArrayList<String>();
        arrivalOrDeparture.add(0, "Arrival");
        arrivalOrDeparture.add(1, "Departure");
        ArrayAdapter<String> adapterArrLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrivalOrDeparture);
        adapterArrLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*spinnerArrLoc.setAdapter(adapterArrLoc);
        spinnerArrLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArrLoc = spinnerArrLoc.getSelectedItem().toString();
                Log.wtf("ARRIVAL LOCATION", selectedArrLoc);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        spinnerArrOrDep.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterArrLoc,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerArrOrDep.setPrompt("Select Arrival or Departure");
        spinnerArrOrDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedArrOrDep = spinnerArrOrDep.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED ARRIVAL LOCATION", selectedArrOrDep);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Spinner for selecting DepartureLocationType
        locationMap = LocationType.toMap();
        ArrayList<String> locationTypes = new ArrayList<String>(locationMap.keySet());
        locationTypes.remove(LocationType.VESSEL.getText()); //Vessel cannot arrive to vessel
        Collections.sort(locationTypes);

        // Removes all LocationTypes that do not have a sublocation.
        allowedSubLocations = new ArrayList<LocationType>(locationMap.values());
        allowedSubLocations.remove(LocationType.VESSEL);
        allowedSubLocations.remove(LocationType.LOC);
        allowedSubLocations.remove(LocationType.TRAFFIC_AREA);

        spinnerLocationType = (Spinner) findViewById(R.id.spinnerLocationType);
        ArrayAdapter<String> adapterLocType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationTypes);
        adapterLocType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*spinnerDepLoc.setAdapter(adapterDepLoc);
        spinnerDepLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepLoc = spinnerDepLoc.getSelectedItem().toString();
                Log.wtf("DEPARTURE LOCATION", selectedDepLoc);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        spinnerLocationType.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterLocType,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerLocationType.setPrompt("Select Location Type");
        spinnerLocationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedLocType = spinnerLocationType.getSelectedItem().toString();
                    setPortLocationSpinnerContent();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED DEPARTURE LOCATION", selectedLocType);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPortLocationSpinnerContent() {
        portLocMap = PortCDMServices.getPortLocations(LocationType.fromString(selectedLocType));
        ArrayList<String> portLocations = new ArrayList<>();
        try {
            portLocations = new ArrayList<String>(portLocMap.keySet());
        } catch (NullPointerException e) {Log.e("PortLocSpinner", e.toString());}
        Collections.sort(portLocations);
        locationSpinner = (Spinner) findViewById(R.id.spinnerSubLocation);
        ArrayAdapter<String> portLocadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, portLocations);
        portLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(portLocadapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPortLoc = locationSpinner.getSelectedItem().toString();
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
        String formattedTime = "";
        try {
            date = etaInput.parse(etaDate + " " + etaTime);
            formattedTime = etaOutput.format(date);
        } catch (ParseException e1) {
            Log.e("DateProblem Parsing", e1.toString());
        } catch (NullPointerException e2){
            Log.e("DateProblem Null", e2.toString());
        }

        Position position = new Position(0, 0);
        try{
            position = portLocMap.get(selectedPortLoc);
        } catch (NullPointerException e){Log.e("PortLocationLocState", e.toString());}


        String locationMRN;
        if (allowedSubLocations.contains(locationMap.get(selectedLocType))) //TODO Add correct ending to locationMRN if subLocation exists.
            locationMRN = "urn:mrn:stm:location:" + API_ACTUAL_PORT + ":" + locationMap.get(selectedLocType);// + ":" + position.getShortName();
        else
            locationMRN = "urn:mrn:stm:location:" + API_ACTUAL_PORT + ":" + locationMap.get(selectedLocType);

        //Creates location-objects based on the selected LocationTypes from the spinners
        if(selectedArrOrDep.equals("Arrival")) {
//            Location arrLocObj = new Location(null, position, locationMap.get(selectedLocType));//Old version 0.0.16 XML
            Location arrLocObj = new Location(null, position, locationMRN);  //New version 0.6
            ArrivalLocation arrLoc = new ArrivalLocation(null, arrLocObj);
            locState = new LocationState(ReferenceObject.VESSEL, formattedTime, timeMap.get(selectedTimeType), arrLoc);

        } else {
//            Location depLocObj = new Location(null, position, locationMap.get(selectedLocType));//Old version 0.0.16 XML
            Location depLocObj = new Location(null, position, locationMRN);  //New version 0.6
            DepartureLocation depLoc = new DepartureLocation(depLocObj, null);
            locState = new LocationState(ReferenceObject.VESSEL, formattedTime, timeMap.get(selectedTimeType), depLoc);
        }

        PortCallMessage pcmObj = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                "VesselAppLocationStateView",
                locState);
        AMSS amss = new AMSS(pcmObj);


        String locStateResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        //TextView locStateResultView = (TextView) findViewById(R.id.etaConfirmView);
        //locStateResultView.setText("Location State-status: " + locStateResult);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SendLocationState.this);
        dialogBuilder.setMessage(locStateResult);
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();

    }
}



