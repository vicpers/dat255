package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ReferenceObject;
import ServiceEntities.TimeType;


public class Send_ETA extends AppCompatActivity implements View.OnClickListener{
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Spinner spinner;
    private String selectedRecipant;
    private HashMap<String, LocationType> locMap;
    public static String newETA ="";
    public static String newDate ="";

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

        /* // Settings for Recipant spinner
        //TODO Fix methods that support sending messages to all the recipants in the list
        Spinner spinner = (Spinner) findViewById(R.id.spinnerRecipant);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ETArecipant, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
       // spinner.setAdapter(adapter);

    */
        /*
        Har försökt koppla innehållet i spinnern till en hashmap som är baserad på LocationType.
        Spinnern visar korrekt innehåll och sparar ner den valda mottagaren i selectedRecipant.
        selectedRecipant används sen för att skapa ett Location-objekt som används för att skicka ETA.
        Jag TROR att det funkar...
          */
        locMap = LocationType.toMap();
        ArrayList<String> locations = new ArrayList<String>(locMap.keySet());
        Collections.sort(locations);
        spinner = (Spinner) findViewById(R.id.spinnerTimeType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRecipant = spinner.getSelectedItem().toString();
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

    public void sendNewETA(View v) {
        // Gets strings that represent the date and time from different Edit-fields.
        String etaDate = dateEditText.getText().toString();
        String etaTime = timeEditText.getText().toString();
        // Lagt till publika variabler för att uppdatera ETA på hemskärm
        newETA = etaTime;
        newDate = etaDate;
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

        /*
        Här använder jag det värdet som väljs i spinnern och passar in det i locObj som skickas vid nytt ETA.
         */
        //Location locObj = new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.TRAFFIC_AREA);
        Location locObj = new Location(null, new Position(0,0, "Gothenburg Port"), LocationType.fromString(selectedRecipant));
        ArrivalLocation arrLoc = new ArrivalLocation(null, locObj);
        LocationState locState = new LocationState(ReferenceObject.VESSEL, formattedTime, TimeType.ESTIMATED, arrLoc);
        PortCallMessage pcmObj = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                                                     "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                     "VesselApplicationETAView",
                                                     locState);
        AMSS amss = new AMSS(pcmObj);
        String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        TextView etaResultView = (TextView) findViewById(R.id.etaConfirmView);
        etaResultView.setText(etaResult);

    }

}