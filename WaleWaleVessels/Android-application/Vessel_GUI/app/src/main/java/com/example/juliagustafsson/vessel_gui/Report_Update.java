package com.example.juliagustafsson.vessel_gui;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
 * Activity for reporting other updates than ETAs to PortCDM. Through a menu the user gets to choose
 * which operation to send a PortCall Message about. When the operation is chosen a form is displayed
 * for the user to enter required data.
 */

public class Report_Update extends AppCompatActivity implements View.OnClickListener {

    private Fragment frag;
    private FragmentTransaction fragTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_update);

        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Report Update");
        getSupportActionBar().setHomeButtonEnabled(true);

        frag = new DefaultFragment();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.viewOptions, frag);
        fragTransaction.commit();

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
        pilotage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new PilotageFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
        });
        mooring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new MooringFragment();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
        });
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
            }
        });

    }

    @Override
    public void onClick(View v) {
    }


}


