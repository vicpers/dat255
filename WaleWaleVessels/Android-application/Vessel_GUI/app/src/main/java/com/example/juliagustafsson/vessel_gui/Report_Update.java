package com.example.juliagustafsson.vessel_gui;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
        getSupportActionBar().setTitle("  Report activity update");
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
                frag = new VtsFragment();
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


