package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import ServiceEntities.TimeType;

/**
 * Created by MattiasLundell on 2017-05-03.
 */

public class Report_Update extends AppCompatActivity implements View.OnClickListener {
    String selectedTimeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_update);

        /*timeMap = TimeType.toMap();
        ArrayList<String> timeTypes = new ArrayList<String>(timeMap.keySet());
        Collections.sort(timeTypes);
        ArrayAdapter<String> adapterTimeType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeTypes);
        adapterTimeType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeType = (Spinner) findViewById(R.id.spinnerTimeType);
*/
        }


    public void sendNewAnchoring(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Report_Update.this);
        dialogBuilder.setMessage("HEJ");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    @Override
    public void onClick(View v) {

    }

}


