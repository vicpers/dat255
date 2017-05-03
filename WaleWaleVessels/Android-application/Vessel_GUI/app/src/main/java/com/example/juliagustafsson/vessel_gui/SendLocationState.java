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
    private HashMap<String, TimeType> timeMap;
    private HashMap<String, ReferenceObject> refObjMap;
    private HashMap<String, LocationType> locationMap;
    private Spinner spinnerTimeType;
    private Spinner spinnerRefObj;
    private Spinner spinnerArrOrDep;
    private Spinner spinnerLocationType;
    private String selectedTimeType = null;
    private String selectedRefObj = null;
    private String selectedArrOrDep = null;
    private String selectedLocType = null;
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

        // Spinner for selecting ReferenceObject
        refObjMap = ReferenceObject.toMap();
        ArrayList<String> referenceObjects = new ArrayList<String>(refObjMap.keySet());
        Collections.sort(referenceObjects);
        spinnerRefObj = (Spinner) findViewById(R.id.spinnerReference);
        ArrayAdapter<String> adapterRefObj = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, referenceObjects);
        adapterRefObj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*spinnerRefObj.setAdapter(adapterRefObj);
        spinnerRefObj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRefObj = spinnerRefObj.getSelectedItem().toString();
                Log.wtf("REFERENCE OBJECT", selectedRefObj);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        spinnerRefObj.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterRefObj,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerRefObj.setPrompt("Select Reference Object");
        spinnerRefObj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedRefObj = spinnerRefObj.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED REFERENCE OBJECT", selectedRefObj);
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
        Collections.sort(locationTypes);
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
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.wtf("SELECTED DEPARTURE LOCATION", selectedLocType);
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
        if(selectedArrOrDep.equals("Arrival")) {
            Location arrLocObj = new Location(null, new Position(0, 0, "Gothenburg Port"), locationMap.get(selectedLocType));
            ArrivalLocation arrLoc = new ArrivalLocation(null, arrLocObj);
            locState = new LocationState(refObjMap.get(selectedRefObj), formattedTime, timeMap.get(selectedTimeType), arrLoc);

        } else {
            Location depLocObj = new Location(null, new Position(0, 0, "Gothenburg Port"), locationMap.get(selectedLocType));
            DepartureLocation depLoc = new DepartureLocation(depLocObj, null);
            locState = new LocationState(refObjMap.get(selectedRefObj), formattedTime, timeMap.get(selectedTimeType), depLoc);
        }

        PortCallMessage pcmObj = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                "VesselAppLocationStateView",
                locState);
        AMSS amss = new AMSS(pcmObj);


        String locStateResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.
        TextView locStateResultView = (TextView) findViewById(R.id.etaConfirmView);
        locStateResultView.setText("Location State-status: " + locStateResult);


    }
}



