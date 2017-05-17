package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.PortCallMessage;
import ServiceEntities.TimeType;

/**
 * Created by mattiaslundell on 2017-05-16.
 */

public class StatementsOfFacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statements_of_facts);

        ListView dialogListView = (ListView) findViewById(R.id.statusView);
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        MessageBrokerQueue actualQueue = queueMap.get(TimeType.ACTUAL.getText());
        actualQueue.pollQueue();
        ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

        ArrayList<String> statusStringList = new ArrayList<>();

        for(PortCallMessage pcm : pcmList){
            statusStringList.add(pcm.toString());
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
        dialogListView.setAdapter(itemsAdapter);
    }

}
