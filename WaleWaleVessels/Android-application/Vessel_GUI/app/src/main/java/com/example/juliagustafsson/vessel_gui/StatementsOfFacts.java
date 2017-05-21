package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.PortCallMessage;
import ServiceEntities.TimeType;

/**
 * Activity for displaying statement of facts. It loads PortCallMessages from the static
 * method in UserLocalStorage and filters them on the TimeType being Estimated.
 */

public class StatementsOfFacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statements_of_facts);

        //Set custom toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Statements of facts");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar ().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView dialogListView = (ListView) findViewById(R.id.statusView);
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();

        try {
            MessageBrokerQueue actualQueue = queueMap.get(TimeType.ACTUAL.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();
            ArrayList<String> statusStringList = new ArrayList<>();

            for(PortCallMessage pcm : pcmList){
                statusStringList.add(pcm.toString());
            }

            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
            dialogListView.setAdapter(itemsAdapter);
        } catch (NullPointerException e) {
            Log.e("actualQueue.pollQueue()", e.toString());
        }


    }

}
