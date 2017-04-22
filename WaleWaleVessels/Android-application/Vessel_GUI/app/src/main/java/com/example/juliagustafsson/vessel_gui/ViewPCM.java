package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import HTTPRequest.*;
import RESTServices.MessageBrokerQueue;


public class ViewPCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);
        // Get the Intent that started this activity and extract the string

        HttpUrlConnectionPortCDM portCdmCon = new HttpUrlConnectionPortCDM();
        TextView textView = (TextView) findViewById(R.id.textView2);
//        String wrTest = portCdmCon.getLatestPortCalls(4);
//        String wrTest = portCdmCon.pollQueueTest();
        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue("f6430677-1209-47cb-91d5-e8804461b9b3");
        msgBrokerQueue.pollQueue();
        String wrTest = msgBrokerQueue.toString();
        textView.setText(wrTest);

    }

}


