package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import RESTServices.PortCDMServices;
import ServiceEntities.PortCallMessage;
import ServiceEntities.TimeType;

/**
 * Activity for displaying ETAs send by the active Vessel. It loads PortCallMessages from the static
 * method in UserLocalStorage and filters them on the TimeType being Estimated.
 */
public class ViewPCM extends AppCompatActivity {
    //TODO döp om
    UserLocalStorage userLocalStore;
    //private String[] lv_arr = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);

       /* private ArrayList<String> serviceObjects = new ArrayList<>();
        private ArrayList<String> locations = new ArrayList<>();
        private ArrayList<String> times = new ArrayList<>();
        private ArrayList<String> dates = new ArrayList<>();
        private ArrayList<String> timeTypes = new ArrayList<>();
        private ArrayList<String> timeSeq = new ArrayList<>();*/
        ArrayList<String> stringList = new ArrayList<>();
        try {
            ArrayList<PortCallMessage> portCallList = UserLocalStorage.getMessageBrokerMap().get(TimeType.ESTIMATED.getText()).getQueue();
            for(PortCallMessage pcm : portCallList){
               /* timeTypes.add(pcm.getTimeType());
                times.add(PortCDMServices.stringToTime(pcm.getTime()));
                dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                timeSeq.add(pcm.getTimeSequence());*/
                stringList.add(pcm.toString());
            }
        } catch (NullPointerException e){
            Log.e("NoPortCallID", e.toString());
        }
/*

        // Get a handle to the list view
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> itemsAdapter =
                new CustomAdapterViewETA(this, R.layout.custom_listview_row_view_eta, stringList);
        lv.setAdapter(itemsAdapter);
*/


    }

}


