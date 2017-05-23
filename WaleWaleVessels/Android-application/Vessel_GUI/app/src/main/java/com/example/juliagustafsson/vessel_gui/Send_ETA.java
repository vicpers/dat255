package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

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
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ReferenceObject;
import ServiceEntities.TimeType;


/**
 * Activity for sending new ETA. After the user has chosen the location in the first spinner, the
 * second spinner fills with corresponding content. The user then picks time and date and a
 * PortCall Message is then sent when pushing "Send".
 */
public class Send_ETA extends AppCompatActivity implements View.OnClickListener{
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Spinner spinner;
    private Spinner locationSpinner;
    private String selectedRecipant;
    private String selectedPortLoc;
    private HashMap<String, LocationType> locMap;
    private HashMap<String, Location> portLocMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_eta);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateEditText = (EditText) findViewById(R.id.editText2);
        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.requestFocus();

        //Set custom toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Send new ETA");
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
                timeEditText.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);

        locMap = LocationType.toMap();
        ArrayList<String> locations = new ArrayList<String>(locMap.keySet());
        locations.remove(LocationType.VESSEL.getText()); //Vessel cannot arrive to vessel
        // Next and previous ports are currently not in use on server side
        locations.remove(LocationType.NEXT_PORT.getText());
        locations.remove(LocationType.PREVIOUS_PORT.getText());
        Collections.sort(locations);

        spinner = (Spinner) findViewById(R.id.spinnerTimeType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRecipant = spinner.getSelectedItem().toString();
                setPortLocationSpinnerContent();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * Sets the content in the second Spinner based on the choice of the first spinner
     */
    private void setPortLocationSpinnerContent() {
        portLocMap = PortCDMServices.getPortLocations(LocationType.fromString(selectedRecipant));
        ArrayList<String> portLocations = new ArrayList<>();
        try {
            portLocations = new ArrayList<String>(portLocMap.keySet());
        } catch (NullPointerException e) {Log.e("PortLocSpinner", e.toString());}
        Collections.sort(portLocations);
        locationSpinner = (Spinner) findViewById(R.id.spinnerLocationType);
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

    /** Shows the DatePickerDialog and TimePickerDialog when the EditTexts are clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v==dateEditText){
            datePicker.show();
        }
        else if(v==timeEditText){
            timePicker.show();
        }
    }

    /** Sends the actual PortCall Message. Is activated when Send-button is pushed.
     * @param v
     */
    public void sendNewETA(View v) {

        // Gets strings that represent the date and time from different Edit-fields.
        String etaDate = dateEditText.getText().toString();
        String etaTime = timeEditText.getText().toString();
        String message = "";
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
            message = "Error: Date or Time not selected! \n Could not send the message.";
        } catch (NullPointerException e2){
            Log.e("DateProblem Null", e2.toString());
        }


//        Här använder jag det värdet som väljs i spinnern och passar in det i locObj som skickas vid nytt ETA.
        Location location = new Location(selectedPortLoc, new Position(0, 0), locMap.get(selectedRecipant));
        try{
            location = portLocMap.get(selectedPortLoc);
        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}

        ArrivalLocation arrLoc = new ArrivalLocation(null, location);
        LocationState locState = new LocationState(ReferenceObject.VESSEL, formattedTime, TimeType.ESTIMATED, arrLoc);


        String vesselID = UserLocalStorage.getVessel().getId(); //Retrieve VesselIMO from UserLocalStorage
        String portCallID = UserLocalStorage.getPortCallID(); //Retrieve PortCallID from UserLocalStorage
        PortCallMessage pcmObj = new PortCallMessage(portCallID,
                                                     vesselID,
                                                     "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                     null,
                                                     locState);
        AMSS amss = new AMSS(pcmObj);
        String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        if(etaResult.equals("")){
            message = "ETA sent successfully";
            finish();
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();

    }

}