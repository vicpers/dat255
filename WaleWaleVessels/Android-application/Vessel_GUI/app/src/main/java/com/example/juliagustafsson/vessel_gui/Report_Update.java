package com.example.juliagustafsson.vessel_gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import ServiceEntities.ServiceObject;
import ServiceEntities.ServiceTimeSequence;
import ServiceEntities.TimeType;

/**
 * Created by MattiasLundell on 2017-05-03.
 */

public class Report_Update extends AppCompatActivity implements View.OnClickListener {
    private String selectedServiceObject;
    private String selectedTimeSequence;
    private Spinner spinnerServiceObject;
    private Spinner spinnerTimeSequence;
    private View anchoringView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_update);
        }


    public void sendNewAnchoring(View v) {
        //Creates an AlertDialog when the Anchoring-button is pressed.
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Report_Update.this);
        anchoringView = getLayoutInflater().inflate(R.layout.dialog_anchoring, null);

        //Creates spinner for selecting Service Object
        spinnerServiceObject = (Spinner) anchoringView.findViewById(R.id.spinnerServiceObject);
        ArrayList<String> serviceObjects = new ArrayList<String>();
        serviceObjects.add(0, "Anchoring");
        serviceObjects.add(1, "Arrival to anchoring operation");
        ArrayAdapter<String> adapterServiceObjects = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceObjects);
        adapterServiceObjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceObject.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterServiceObjects,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerServiceObject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedServiceObject = spinnerServiceObject.getSelectedItem().toString();
                    //Creates spinner for selecting Service Time Sequence based on the selected service object
                    setTimeSequenceSpinnerContent(selectedServiceObject);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Log.wtf("SERVICE OBJECT", selectedServiceObject);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dialogBuilder.setTitle("Anchoring");
        dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.setView(anchoringView);
        dialogBuilder.show();
    }

    //Creates spinner for selecting Service Time Sequence based on the selected service object
    private void setTimeSequenceSpinnerContent(String selectedServiceObject) {
        spinnerTimeSequence = (Spinner) anchoringView.findViewById(R.id.spinnerTimeSequence);
        ArrayList<String> timeSequences = new ArrayList<String>();
        try {
            if (selectedServiceObject.equalsIgnoreCase("Anchoring")) {
                timeSequences.add(0, "Commenced");
                timeSequences.add(1, "Completed");
            } else {
                timeSequences.add(0, "Requested");
                timeSequences.add(1, "Request recieved");
                timeSequences.add(2, "Denied");
                timeSequences.add(3, "Confirmed");
                timeSequences.add(4, "Completed");
                timeSequences.add(5, "Commenced");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(timeSequences);
        ArrayAdapter<String> adapterTimeSequences = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSequences);
        adapterTimeSequences.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeSequence.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterTimeSequences,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        spinnerTimeSequence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedTimeSequence = spinnerTimeSequence.getSelectedItem().toString();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Log.wtf("TIME SEQUENCE", selectedTimeSequence);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onClick(View v) {

    }



}

