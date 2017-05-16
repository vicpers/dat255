package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import ServiceEntities.Between;
import ServiceEntities.DepartureLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ReferenceObject;
import ServiceEntities.ServiceObject;
import ServiceEntities.ServiceState;
import ServiceEntities.ServiceTimeSequence;
import ServiceEntities.ServiceType;
import ServiceEntities.TimeType;

import static RESTServices.PortCDMServices.getServiceType;

/**
 * Created by MattiasLundell on 2017-05-03.
 */

public class Report_Update extends AppCompatActivity implements View.OnClickListener {
    private View serviceStateView;
    private View locationstateView;
    private ServiceObject currentServiceObject;
    private Spinner spinnerTimeSequence;
    private Spinner spinnerAtOrFromLocation;
    private Spinner spinnerToLocation;
    private Spinner spinnerTimeType;
    private Spinner spinnerSubLocation;

    private EditText dateEditText;
    private EditText timeEditText;
    private View dialogView;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    AlertDialog.Builder dialogBuilder;
    private String selectedTimeSequence;
    private String selectedFromLocation;
    private String selectedtoLocation;
    private String selectedAtLocation;
    private String selectedSubLocation;
    private TimeType selectedTimeType;
    private String selectedPortLoc;
    private LocationType selectedLocationType;
    private Boolean isServiceState;
    private Boolean isArrival;
    private HashMap<String, TimeType> timeTypeMap;
    private ArrivalLocation arrLoc;
    private DepartureLocation depLoc;
    private LocationState locState;
    HashMap<String, Location> subLocationsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_update);
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

    public void sendNewServiceState(View v) {
        isServiceState = true;
        serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
        switch( v.getId() ) {
            case R.id.Anchoring: {
                currentServiceObject = ServiceObject.ANCHORING;
                selectedLocationType = LocationType.ANCHORING_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedLocationType);
                break;
            }

            case R.id.ArrivalAnchoringOperation: {
                currentServiceObject = ServiceObject.ARRIVAL_ANCHORING_OPERATION;
                selectedLocationType = LocationType.ANCHORING_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedLocationType);
                break;
            }

            case R.id.VTSAreaArrival: {
                currentServiceObject = ServiceObject.ARRIVAL_VTSAREA;
                selectedLocationType = LocationType.TRAFFIC_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedLocationType);
                break;
            }

            case R.id.BerthShifting: {
                currentServiceObject = ServiceObject.BERTH_SHIFTING;
                selectedLocationType = LocationType.BERTH;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setBetweenServiceStateView(selectedLocationType, selectedLocationType);
                break;
            }

            //TODO At which location?
            case R.id.BunkeringOperation: {
                currentServiceObject = ServiceObject.BUNKERING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            //TODO At which location?
            case R.id.CargoOperation: {
                currentServiceObject = ServiceObject.CARGO_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            case R.id.VTSAreaDeparture: {
                currentServiceObject = ServiceObject.DEPARTURE_VTSAREA;
                selectedLocationType = LocationType.TRAFFIC_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedLocationType);
                break;
            }

            //TODO Between which locations?
            case R.id.EscortTowage: {
                currentServiceObject = ServiceObject.ESCORT_TOWAGE;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            //TODO Between which locations?
            case R.id.IceBreakingOperation: {
                currentServiceObject = ServiceObject.ICEBREAKING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            //TODO At which location?
            case R.id.ArrivalMooringOperation: {
                currentServiceObject = ServiceObject.ARRIVAL_MOORING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            //TODO At which location?
            case R.id.DepartureMooringOperation: {
                currentServiceObject = ServiceObject.DEPARTURE_MOORING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            //TODO Between which locations?
            case R.id.Pilotage: {
                currentServiceObject = ServiceObject.PILOTAGE;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }

            //TODO Between which locations?
            case R.id.Towage: {
                currentServiceObject = ServiceObject.TOWAGE;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setDialogView(currentServiceObject);
                break;
            }
        }
    }

    public void sendNewLocationState(View v) {
        isServiceState = false;
        locationstateView = getLayoutInflater().inflate(R.layout.dialog_location_state_update, null);
        switch( v.getId() ) {
            case R.id.ArrivalAnchoringArea: {
                isArrival = true;
                selectedLocationType = LocationType.ANCHORING_AREA;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }

            case R.id.DepartureAnchoringArea: {
                isArrival = false;
                selectedLocationType = LocationType.ANCHORING_AREA;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }

            case R.id.ArrivalBerth: {
                isArrival = true;
                selectedLocationType = LocationType.BERTH;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }

            case R.id.DepartureBerth: {
                isArrival = false;
                selectedLocationType = LocationType.BERTH;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }

            case R.id.ArrivalPilotBoardingArea: {
                isArrival = true;
                selectedLocationType = LocationType.PILOT_BOARDING_AREA;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }

            case R.id.ArrivalTrafficArea: {
                isArrival = true;
                selectedLocationType = LocationType.TRAFFIC_AREA;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }

            case R.id.DepartureTrafficArea: {
                isArrival = false;
                selectedLocationType = LocationType.TRAFFIC_AREA;
                createAlertDialog(locationstateView);
                setTimeAndDate(locationstateView);
                setLocationstateView(selectedLocationType);
                break;
            }
        }

    }

    private void createAlertDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(Report_Update.this);
        dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send a service state port call message
                if(isServiceState) {
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

                    Intent intent = getIntent();
                    String vesselID = intent.getExtras().getString("vesselID"); //Hämta VesselIMO skickat från mainactivity

                    ServiceState serviceState;
                    //TODO Se till så att at och between används utifrån val.
                    //TODO Implementera att en TimeType ska väljas.
                    if(getServiceType(currentServiceObject) == ServiceType.STATIONARY){
                        Location at = new Location(selectedAtLocation,
                                                    new Position(0, 0), selectedLocationType);
                        try{
                            at = subLocationsMap.get(selectedAtLocation);
                        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}
                        serviceState = new ServiceState(currentServiceObject,
                                ServiceTimeSequence.fromString(selectedTimeSequence),
                                selectedTimeType,
                                formattedTime,
                                at,
                                null); //performingActor ev. vesselId
                    } else {
                        Location from = new Location(selectedFromLocation, new Position(0, 0), selectedLocationType);
                        try{
                            from = subLocationsMap.get(selectedFromLocation);
                        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}
                        Location to = new Location(selectedtoLocation, new Position(0, 0), selectedLocationType);
                        try{
                            to = subLocationsMap.get(selectedtoLocation);
                        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}
                        Between between = new Between(from, to);
                        serviceState = new ServiceState(currentServiceObject,
                                ServiceTimeSequence.fromString(selectedTimeSequence),
                                selectedTimeType,
                                formattedTime,
                                between,
                                null); //performingActor ev. vesselId
                    }

                    PortCallMessage pcmObj = new PortCallMessage(vesselID,
                            "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                            null,
                            serviceState);
                    AMSS amss = new AMSS(pcmObj);
                    String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.

                } else {

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

                    Location location = new Location(selectedSubLocation, new Position(0, 0), selectedLocationType);
                    try{
                        location = subLocationsMap.get(selectedSubLocation);
                    } catch (NullPointerException e){Log.e("PortLocation", e.toString());}

                    if (isArrival) {
                        arrLoc = new ArrivalLocation(null, location);
                        locState = new LocationState(ReferenceObject.VESSEL, formattedTime, selectedTimeType, arrLoc);
                    } else {
                        depLoc = new DepartureLocation(location, null);
                        locState = new LocationState(ReferenceObject.VESSEL, formattedTime, selectedTimeType, depLoc);
                    }

                    Intent intent = getIntent();
                    String vesselID = intent.getExtras().getString("vesselID"); //Hämta VesselIMO skickat från mainactivity
                    PortCallMessage pcmObj = new PortCallMessage(vesselID,
                            "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                            null,
                            locState);
                    AMSS amss = new AMSS(pcmObj);
                    String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.

                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.setView(v);
        dialogBuilder.show();
    }

    private void setTimeAndDate(View v){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateEditText = (EditText) v.findViewById(R.id.editTextDate);
        dateEditText.requestFocus();
        dateEditText.setInputType(InputType.TYPE_NULL);
        timeEditText = (EditText) v.findViewById(R.id.editTextTime);
        timeEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.setOnClickListener(this);
        timeEditText.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        timeEditText.requestFocus();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year,month,dayOfMonth);
                dateEditText.setText(dateFormat.format(newCalendar.getTime()));

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        timePicker = new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeEditText.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                dateEditText.requestFocus();
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }

    private void setTimeSequenceSpinner(ServiceObject serviceObject) {
        spinnerTimeSequence = (Spinner) serviceStateView.findViewById(R.id.spinnerTimeSequence);
        HashMap<String, ServiceTimeSequence> timeSequenceMap = PortCDMServices.getStateDefinitions(serviceObject);
        ArrayList<String> timeSequences = new ArrayList<String>(timeSequenceMap.keySet());
        Collections.sort(timeSequences);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSequences);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeSequence.setAdapter(arrayAdapter);
        spinnerTimeSequence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedTimeSequence = spinnerTimeSequence.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setTimeTypeSpinner(View v) {
        spinnerTimeType = (Spinner) v.findViewById(R.id.spinnerTimeType);
        timeTypeMap = TimeType.toMap();
        ArrayList<String> timeTypes = new ArrayList<String>(timeTypeMap.keySet());
        Collections.sort(timeTypes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeType.setAdapter(arrayAdapter);
        spinnerTimeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedTimeType = timeTypeMap.get(spinnerTimeType.getSelectedItem().toString());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setAtLocationSpinner() {
        HashMap<String, LocationType> locationTypeMap = LocationType.toMap();
        ArrayList<String> locationTypes = new ArrayList<String>(locationTypeMap.keySet());
        Collections.sort(locationTypes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAtOrFromLocation.setAdapter(arrayAdapter);
        spinnerAtOrFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedAtLocation = spinnerAtOrFromLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setFromLocationSpinner() {
        HashMap<String, LocationType> locationTypeMap = LocationType.toMap();
        ArrayList<String> locationTypes = new ArrayList<String>(locationTypeMap.keySet());
        Collections.sort(locationTypes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAtOrFromLocation.setAdapter(arrayAdapter);
        spinnerAtOrFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedFromLocation = spinnerAtOrFromLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setToLocationSpinner() {
        HashMap<String, LocationType> locationTypeMap = LocationType.toMap();
        ArrayList<String> locationTypes = new ArrayList<String>(locationTypeMap.keySet());
        Collections.sort(locationTypes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToLocation.setAdapter(arrayAdapter);
        spinnerToLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedtoLocation = spinnerToLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setDialogView(ServiceObject serviceObject) {
        ServiceType currentServiceType = getServiceType(serviceObject);
        TextView atFromLocationText = (TextView) serviceStateView.findViewById(R.id.AtFromLocationText);
        TextView toLocation = (TextView) serviceStateView.findViewById(R.id.ToLocation);
        spinnerAtOrFromLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerAtOrFromLocation);
        spinnerToLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerToLocation);

        if (currentServiceType == ServiceType.NAUTICAL ) {
            atFromLocationText.setText("From Location");
            setFromLocationSpinner();
            setToLocationSpinner();

        } else {
            atFromLocationText.setText("At Location");
            toLocation.setVisibility(View.INVISIBLE);
            spinnerToLocation.setVisibility(View.INVISIBLE);
            setAtLocationSpinner();
        }
    }

    private void setBetweenServiceStateView(LocationType from, LocationType to) {
        setTimeTypeSpinner(serviceStateView);
        TextView atFromLocationText = (TextView) serviceStateView.findViewById(R.id.AtFromLocationText);
        spinnerAtOrFromLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerAtOrFromLocation);
        spinnerToLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerToLocation);
        atFromLocationText.setText("From Location");

        HashMap<String, Location> subLocationsMap = PortCDMServices.getPortLocations(from);
        ArrayList<String> subLocations = new ArrayList<String>(subLocationsMap.keySet());
        Collections.sort(subLocations);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subLocations);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAtOrFromLocation.setAdapter(arrayAdapter);
        spinnerAtOrFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedFromLocation = spinnerAtOrFromLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerToLocation.setAdapter(arrayAdapter);
        spinnerToLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedtoLocation = spinnerToLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setAtServiceStateView (LocationType at) {
        setTimeTypeSpinner(serviceStateView);
        TextView atFromLocationText = (TextView) serviceStateView.findViewById(R.id.AtFromLocationText);
        TextView toLocation = (TextView) serviceStateView.findViewById(R.id.ToLocation);
        spinnerAtOrFromLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerAtOrFromLocation);
        spinnerToLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerToLocation);
        atFromLocationText.setText("At Location");
        toLocation.setVisibility(View.INVISIBLE);
        spinnerToLocation.setVisibility(View.INVISIBLE);

        HashMap<String, Location> subLocationsMap = PortCDMServices.getPortLocations(at);
        ArrayList<String> subLocations = new ArrayList<String>(subLocationsMap.keySet());
        Collections.sort(subLocations);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subLocations);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAtOrFromLocation.setAdapter(arrayAdapter);
        spinnerAtOrFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedAtLocation = spinnerAtOrFromLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setLocationstateView (LocationType locationType) {
        setTimeTypeSpinner(locationstateView);
        spinnerSubLocation = (Spinner) locationstateView.findViewById(R.id.spinnerSubLocation);
        subLocationsMap = PortCDMServices.getPortLocations(locationType);
        ArrayList<String> subLocations = new ArrayList<String>(subLocationsMap.keySet());
        Collections.sort(subLocations);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subLocations);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubLocation.setAdapter(arrayAdapter);
        spinnerSubLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedSubLocation = spinnerSubLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }






}


