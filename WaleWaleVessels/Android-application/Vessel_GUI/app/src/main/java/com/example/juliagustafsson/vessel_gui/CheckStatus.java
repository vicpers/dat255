package com.example.juliagustafsson.vessel_gui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Activity for checking status on various operations. It displays Fragments based on the choice from
 * the user.
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
        getSupportActionBar().setTitle("  Check activity status");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_ship24);


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

        Intent intent = getIntent();
        if (intent.hasExtra("clickedNotification")) {
            String notification = intent.getStringExtra("clickedNotification");
            if (notification.toLowerCase().contains("anchoring")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new AnchoringFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("berth")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new BerthFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("towage")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new TowageFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("pilot")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new PilotageFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("mooring")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new MooringFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("traffic area")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new TrafficAreaFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("vts-area")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new VtsFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }
            else if (notification.toLowerCase().contains("operation")) {
                Bundle bundle = new Bundle();
                bundle.putString("notification", notification);
                frag = new OtherFragmentCS();
                frag.setArguments(bundle);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.viewOptions, frag);
                fragTransaction.commit();
            }




        }
    }

}


