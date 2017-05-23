package com.example.juliagustafsson.vessel_gui;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import ServiceEntities.Location;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ServiceObject;
import ServiceEntities.ServiceState;
import ServiceEntities.ServiceTimeSequence;
import ServiceEntities.TimeType;

/**
 * Fragment for reporting Mooring related activities
 */
public class MooringFragment extends android.app.Fragment implements View.OnClickListener{
    private View serviceStateView;
    private ServiceObject currentServiceObject;
    private Spinner spinnerTimeSequence;
    private Spinner spinnerAtOrFromLocation;
    private Spinner spinnerToLocation;
    private Spinner spinnerTimeType;
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    AlertDialog.Builder dialogBuilder;
    private String selectedTimeSequence;
    private LocationType selectedAtLocation;
    private String selectedAtSubLocation;
    private TimeType selectedTimeType;
    private HashMap<String, TimeType> timeTypeMap;
    HashMap<String, Location> atSubLocationMap;
    HashMap<String, ServiceTimeSequence> timeSequenceMap;
    private String currentActivity;

    public MooringFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mooring, container, false);
        Button arrivalMooring = (Button) rootView.findViewById(R.id.ArrivalMooring);
        Button depMooring = (Button) rootView.findViewById(R.id.DepartureMooring);

        arrivalMooring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                selectedAtLocation = LocationType.BERTH;
                currentActivity = "Arrival Mooring Operation";
                currentServiceObject = ServiceObject.ARRIVAL_MOORING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
            }
        });

        depMooring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_service_state_update, null);
                selectedAtLocation = LocationType.BERTH;
                currentActivity = "Departure Mooring Operation";
                currentServiceObject = ServiceObject.DEPARTURE_MOORING_OPERATION;
                setTimeSequenceSpinner(currentServiceObject);
                createAlertDialog(serviceStateView);
                setTimeAndDate(serviceStateView);
                setAtServiceStateView(selectedAtLocation);
            }
        });

        return rootView;
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

    private void createAlertDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                } catch (NullPointerException e2) {
                    Log.e("DateProblem Null", e2.toString());
                }
                String vesselID = UserLocalStorage.getVessel().getId(); //Get VesselIMO
                String portCallID = UserLocalStorage.getPortCallID(); //Get portCallID
                ServiceState serviceState;

                Location at = new Location(selectedAtSubLocation,
                        new Position(0, 0), selectedAtLocation);
                try {
                    at = atSubLocationMap.get(selectedAtSubLocation);
                } catch (NullPointerException e) {
                    Log.e("PortLocation", e.toString());
                }
                serviceState = new ServiceState(currentServiceObject,
                        ServiceTimeSequence.fromString(selectedTimeSequence),
                        selectedTimeType,
                        formattedTime,
                        at,
                        null); //performingActor ev. vesselId

                PortCallMessage pcmObj = new PortCallMessage(portCallID,
                        vesselID,
                        "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                        null,
                        serviceState);
                AMSS amss = new AMSS(pcmObj);
                String etaResult = amss.submitStateUpdate(); // Submits the PortCallMessage containing the ETA to PortCDM trhough the AMSS.

                Log.e("etaResult: ", etaResult);

                if(etaResult.equals("")){
                    message = "Mooring update regarding: " + etaDate + ", " + etaTime + " sent!\"";
                }

                Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();

            }
        });
        dialogBuilder.setTitle(currentActivity);
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
        datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year,month,dayOfMonth);
                dateEditText.setText(dateFormat.format(newCalendar.getTime()));

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        timePicker = new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeEditText.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                dateEditText.requestFocus();
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }

    private void setTimeSequenceSpinner(ServiceObject serviceObject) {
        spinnerTimeSequence = (Spinner) serviceStateView.findViewById(R.id.spinnerTimeSequence);
        timeSequenceMap = PortCDMServices.getStateDefinitions(serviceObject);
        ArrayList<String> timeSequences = new ArrayList<String>(timeSequenceMap.keySet());
        Collections.sort(timeSequences);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, timeSequences);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, timeTypes);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subLocations);
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


}