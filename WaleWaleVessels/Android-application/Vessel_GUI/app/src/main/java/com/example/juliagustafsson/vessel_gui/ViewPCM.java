package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ServiceEntities.PortCallMessage;
import ServiceEntities.TimeType;

public class ViewPCM extends AppCompatActivity {
    UserLocalStorage userLocalStore;
    //private String[] lv_arr = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);
        Intent intent = getIntent();

        //ArrayList<String> stringList = intent.getStringArrayListExtra("portcalls");

        ArrayList<PortCallMessage> portCallList = UserLocalStorage.getMessageBrokerMap().get(TimeType.ESTIMATED.getText()).getQueue();

        ArrayList<String> stringList = new ArrayList<>();

        for(PortCallMessage pcm : portCallList){
            stringList.add(pcm.toString());
        }



        // Get a handle to the list view
        ListView lv = (ListView) findViewById(R.id.listView);


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        lv.setAdapter(itemsAdapter);


    }

}


