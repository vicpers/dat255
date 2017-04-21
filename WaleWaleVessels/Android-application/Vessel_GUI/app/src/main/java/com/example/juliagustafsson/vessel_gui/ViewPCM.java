package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import HTTPRequest.*;


public class ViewPCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);
        // Get the Intent that started this activity and extract the string

        HttpUrlConnectionPortCDM portCdmCon = new HttpUrlConnectionPortCDM();
        TextView textView = (TextView) findViewById(R.id.textView2);
        String wrTest = portCdmCon.getPortCallMessages(4);
        textView.setText(wrTest);

    }

}


