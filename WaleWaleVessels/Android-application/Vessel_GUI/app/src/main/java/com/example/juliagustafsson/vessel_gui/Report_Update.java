package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
<<<<<<< Updated upstream
=======
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Service;
>>>>>>> Stashed changes
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

<<<<<<< Updated upstream
=======
import org.w3c.dom.Text;

import java.sql.Time;
>>>>>>> Stashed changes
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
    private Spinner spinnerFromLocation;
    private Spinner spinnerFromSubLocation;
    private Spinner spinnerToSubLocation;
    private Spinner spinnerToLocation;
    private Spinner spinnerTimeType;
    private Spinner spinnerSubLocation;
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    AlertDialog.Builder dialogBuilder;
    private String selectedTimeSequence;
    private LocationType selectedFromLocation;
    private LocationType selectedtoLocation;
    private LocationType selectedAtLocation;
    private String selectedAtSubLocation;
    private String selectedSubLocation;
    private String selectedFromSubLocation;
    private String selectedToSubLocation;
    private TimeType selectedTimeType;
    private LocationType selectedLocationType;
    private Boolean isServiceState;
    private Boolean isArrival;
    private HashMap<String, TimeType> timeTypeMap;
    private ArrivalLocation arrLoc;
    private DepartureLocation depLoc;
    private LocationState locState;
<<<<<<< Updated upstream
    HashMap<String, Location> subLocationsMap;
    HashMap<String, Location> toSubLocationMap;
    HashMap<String, Location> fromSubLocationMap;
    HashMap<String, Location> atSubLocationMap;
    HashMap<String, ServiceTimeSequence> timeSequenceMap;

    //TODO NullPointerException för spinners ifall den inte lyckas hämta hem saker från servern
=======
    private Fragment frag;
    private FragmentTransaction fragTransaction;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_update);
<<<<<<< Updated upstream
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
        switch( v.getId() ) {
            case R.id.Anchoring: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                currentServiceObject = ServiceObject.ANCHORING;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            case R.id.ArrivalAnchoringOperation: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                currentServiceObject = ServiceObject.ARRIVAL_ANCHORING_OPERATION;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            case R.id.VTSAreaArrival: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                currentServiceObject = ServiceObject.ARRIVAL_VTSAREA;
                selectedAtLocation = LocationType.TRAFFIC_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            case R.id.BerthShifting: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                currentServiceObject = ServiceObject.BERTH_SHIFTING;
                selectedFromLocation = LocationType.BERTH;
                selectedtoLocation = LocationType.BERTH;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setBetweenServiceStateView(selectedFromLocation, selectedtoLocation);
                break;
            }

            //TODO At which location?
            case R.id.BunkeringOperation: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.BUNKERING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            //TODO At which location?
            case R.id.CargoOperation: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.CARGO_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            case R.id.VTSAreaDeparture: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                currentServiceObject = ServiceObject.DEPARTURE_VTSAREA;
                selectedAtLocation = LocationType.TRAFFIC_AREA;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            //TODO Between which locations?
            case R.id.EscortTowage: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_between_service_state, null);
                currentServiceObject = ServiceObject.ESCORT_TOWAGE;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setBetweenServiceStateView();
                break;
            }

            //TODO Between which locations?
            case R.id.IceBreakingOperation: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_between_service_state, null);
                currentServiceObject = ServiceObject.ICEBREAKING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setBetweenServiceStateView();
                break;
            }

            //TODO At which location?
            case R.id.ArrivalMooringOperation: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.ARRIVAL_MOORING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            //TODO At which location?
            case R.id.DepartureMooringOperation: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.DEPARTURE_MOORING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
                break;
            }

            //TODO Between which locations?
            case R.id.Pilotage: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_between_service_state, null);
                currentServiceObject = ServiceObject.PILOTAGE;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setBetweenServiceStateView();
                break;
            }

            //TODO Between which locations?
            case R.id.Towage: {
                serviceStateView = getLayoutInflater().inflate(R.layout.dialog_between_service_state, null);
                currentServiceObject = ServiceObject.TOWAGE;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setBetweenServiceStateView();
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

                // TODO Max ska skriva om så att en ny user initieras när den klassen är klar.
                Intent intent = getIntent();
                String vesselID = intent.getExtras().getString("vesselID"); //Hämta VesselIMO skickat från mainactivity
                String portCallID = intent.getExtras().getString("portCallID"); //Hämta portCallID skickat från mainactivity

                //send a service state port call message
                if(isServiceState) {
                    ServiceState serviceState;
                    //TODO Se till så att at och between används utifrån val.
                    //TODO Implementera att en TimeType ska väljas.
                    if(getServiceType(currentServiceObject) == ServiceType.STATIONARY){
                        Location at = new Location(selectedAtSubLocation,
                                                    new Position(0, 0), selectedAtLocation);
                        try{
                            at = atSubLocationMap.get(selectedAtSubLocation);
                        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}
                        serviceState = new ServiceState(currentServiceObject,
                                ServiceTimeSequence.fromString(selectedTimeSequence),
                                selectedTimeType,
                                formattedTime,
                                at,
                                null); //performingActor ev. vesselId
                    } else {
                        Location from = new Location(selectedFromSubLocation, new Position(0, 0), selectedFromLocation);
                        try{
                            from = fromSubLocationMap.get(selectedFromSubLocation);
                        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}
                        Location to = new Location(selectedToSubLocation, new Position(0, 0), selectedtoLocation);
                        try{
                            to = toSubLocationMap.get(selectedToSubLocation);
                        } catch (NullPointerException e){Log.e("PortLocation", e.toString());}
                        Between between = new Between(from, to);
                        serviceState = new ServiceState(currentServiceObject,
                                ServiceTimeSequence.fromString(selectedTimeSequence),
                                selectedTimeType,
                                formattedTime,
                                between,
                                null); //performingActor ev. vesselId
                    }

                    PortCallMessage pcmObj = new PortCallMessage(portCallID,
                                                                 vesselID,
                                                                 "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                                 null,
                                                                 serviceState);
                    AMSS amss = new AMSS(pcmObj);

                    String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.

                    //send a location state port call message
                } else {

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

                    PortCallMessage pcmObj = new PortCallMessage(portCallID,
                                                                 vesselID,
                                                                 "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                                 null,
                                                                 locState);
                    AMSS amss = new AMSS(pcmObj);
                    String wrResponse = amss.submitStateUpdate(); // Submits the PortCallMessage to PortCDM through the AMSS.
                }
=======

        frag = new DefaultFragment();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.viewOptions, frag);
        fragTransaction.commit();

        // Set customized toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Report Update");
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView anchoring = (TextView) findViewById(R.id.Anchoring);
        TextView berth = (TextView) findViewById(R.id.Berth);
        TextView towage = (TextView) findViewById(R.id.Towage);
        TextView pilotage = (TextView) findViewById(R.id.Pilotage);
        TextView mooring = (TextView) findViewById(R.id.Mooring);
        TextView trafficArea = (TextView) findViewById(R.id.trafficArea);
        TextView vtsArea = (TextView) findViewById(R.id.vtsArea);
        TextView other = (TextView) findViewById(R.id.Other);

        anchoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new AnchoringFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
>>>>>>> Stashed changes
            }
        });

        berth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new BerthFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
        });

        towage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new TowageFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
        });

<<<<<<< Updated upstream
    private void setTimeSequenceSpinner(ServiceObject serviceObject) {
        spinnerTimeSequence = (Spinner) serviceStateView.findViewById(R.id.spinnerTimeSequence);
        timeSequenceMap = PortCDMServices.getStateDefinitions(serviceObject);
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
=======
        pilotage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new PilotageFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
>>>>>>> Stashed changes
            }
        });

<<<<<<< Updated upstream
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
=======
        mooring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new MooringFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
>>>>>>> Stashed changes
            }
        });

<<<<<<< Updated upstream
    private void setBetweenServiceStateView(LocationType from, LocationType to) {
        setTimeTypeSpinner(serviceStateView);
        TextView atFromLocationText = (TextView) serviceStateView.findViewById(R.id.AtFromLocationText);
        spinnerAtOrFromLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerAtOrFromLocation);
        spinnerToLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerToLocation);
        atFromLocationText.setText("From Location");

        fromSubLocationMap = PortCDMServices.getPortLocations(from);
        ArrayList<String> fromSubLocations = new ArrayList<String>(fromSubLocationMap.keySet());
        Collections.sort(fromSubLocations);
        ArrayAdapter<String> fromSubLocationsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromSubLocations);
        fromSubLocationsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAtOrFromLocation.setAdapter(fromSubLocationsArrayAdapter);
        spinnerAtOrFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedFromSubLocation = spinnerAtOrFromLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        toSubLocationMap = PortCDMServices.getPortLocations(to);
        ArrayList<String> toSubLocations = new ArrayList<String>(toSubLocationMap.keySet());
        Collections.sort(toSubLocations);
        ArrayAdapter<String> toSubLocationsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toSubLocations);
        toSubLocationsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToLocation.setAdapter(toSubLocationsArrayAdapter);
        spinnerToLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedToSubLocation = spinnerToLocation.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setBetweenServiceStateView() {
        setTimeTypeSpinner(serviceStateView);

        spinnerFromLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerFromLocation);
        spinnerToLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerToLocation);
        spinnerFromSubLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerFromSubLocation);
        spinnerToSubLocation = (Spinner) serviceStateView.findViewById(R.id.spinnerToSubLocation);

        HashMap<String, LocationType> locationTypeMap = LocationType.toMap();
        ArrayList<String> locations = new ArrayList<String>(locationTypeMap.keySet());
        Collections.sort(locations);
        ArrayAdapter<String> locationArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFromLocation.setAdapter(locationArrayAdapter);
        spinnerFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedFromLocation = LocationType.fromString(spinnerFromLocation.getSelectedItem().toString());
                    setFromSubLocationSpinner(selectedFromLocation);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerToLocation.setAdapter(locationArrayAdapter);
        spinnerToLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedtoLocation = LocationType.fromString(spinnerToLocation.getSelectedItem().toString());
                    setToSubLocationSpinner(selectedtoLocation);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
=======
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new OtherFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
        });

        vtsArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new vtsFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
        });

        trafficArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new TrafficAreaFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
>>>>>>> Stashed changes
            }
        });
    }

<<<<<<< Updated upstream
    private void setFromSubLocationSpinner(LocationType location) {
        fromSubLocationMap = PortCDMServices.getPortLocations(location);
        ArrayList<String> portLocations = new ArrayList<>();
        try {
            portLocations = new ArrayList<String>(fromSubLocationMap.keySet());
        } catch (NullPointerException e) {Log.e("PortLocSpinner", e.toString());}
        Collections.sort(portLocations);
        ArrayAdapter<String> portLocadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, portLocations);
        portLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromSubLocation.setAdapter(portLocadapter);
        spinnerFromSubLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFromSubLocation = spinnerFromSubLocation.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setToSubLocationSpinner(LocationType location) {
        toSubLocationMap = PortCDMServices.getPortLocations(location);
        ArrayList<String> portLocations = new ArrayList<>();
        try {
            portLocations = new ArrayList<String>(toSubLocationMap.keySet());
        } catch (NullPointerException e) {Log.e("PortLocSpinner", e.toString());}
        Collections.sort(portLocations);
        ArrayAdapter<String> portLocadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, portLocations);
        portLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToSubLocation.setAdapter(portLocadapter);
        spinnerToSubLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedToSubLocation = spinnerToSubLocation.getSelectedItem().toString();
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

        atSubLocationMap = PortCDMServices.getPortLocations(at);
        ArrayList<String> subLocations = new ArrayList<String>(atSubLocationMap.keySet());
        Collections.sort(subLocations);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subLocations);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAtOrFromLocation.setAdapter(arrayAdapter);
        spinnerAtOrFromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedAtSubLocation = spinnerAtOrFromLocation.getSelectedItem().toString();
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
=======
    @Override
    public void onClick(View v) {
        if (v==dateEditText){
            datePicker.show();

        }
        else if(v==timeEditText){
            timePicker.show();
        }
    }

>>>>>>> Stashed changes
}


