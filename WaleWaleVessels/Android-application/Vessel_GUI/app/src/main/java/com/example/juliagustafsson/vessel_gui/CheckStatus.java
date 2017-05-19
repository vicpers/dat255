package com.example.juliagustafsson.vessel_gui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;

/**
 * Created by MattiasLundell on 2017-05-03.
 */

public class CheckStatus extends AppCompatActivity implements View.OnClickListener {

    AlertDialog.Builder dialogBuilder;
    private Fragment frag;
    private FragmentTransaction fragTransaction;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Check Status");
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
                frag = new AnchoringFragmentCS();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
                     }
                });

                berth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new BerthFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });

                towage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new TowageFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });

                pilotage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new PilotageFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });

                mooring.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new MooringFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });

                other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new OtherFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });

                vtsArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new VtsFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });

                trafficArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frag = new TrafficAreaFragmentCS();
                        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                        fragTransaction.commit();
                    }
                });


    }


}


