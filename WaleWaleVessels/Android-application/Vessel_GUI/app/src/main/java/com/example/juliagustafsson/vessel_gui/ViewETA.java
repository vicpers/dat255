package com.example.juliagustafsson.vessel_gui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import RESTServices.PortCDMServices;
import ServiceEntities.Location;
import ServiceEntities.PortCallMessage;
import ServiceEntities.TimeType;

/**
 * Activity for displaying ETAs send by the active Vessel. It loads PortCallMessages from the static
 * method in UserLocalStorage and filters them on the TimeType being Estimated.
 */
public class ViewETA extends AppCompatActivity {
    UserLocalStorage userLocalStore;

    //private String[] lv_arr = {};
    private ArrayList<String> serviceObjects = new ArrayList<>();
    private ArrayList<String> positionsAt = new ArrayList<>();
    private ArrayList<String> locationsFrom = new ArrayList<>();
    private ArrayList<String> locationsTo = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> timeTypes = new ArrayList<>();
    private ArrayList<String> timeSequences = new ArrayList<>();
    private Drawable iconImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_eta);

        // Set customized toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("  View ETA history");
        getSupportActionBar().setIcon(R.drawable.ic_ship24);

        ListView dialogListView = (ListView) findViewById(R.id.listView);

        try {
            ArrayList<PortCallMessage> portCallList = UserLocalStorage.getMessageBrokerMap().get(TimeType.ESTIMATED.getText()).getQueue();
            for (PortCallMessage pcm : portCallList) {
                if (pcm.isServiceState() == true) {
                    if (pcm.getServiceState().isBetween()) {
                        serviceObjects.add(pcm.getServiceState().getServiceObject().getText());
                        String locMRN = pcm.getLocationMRN();
                        if (locMRN.contains("/")) {
                            String[] parts = locMRN.split("/");
                            try {
                                String loc1 = parts[0];
                                String loc2 = parts[1];
                                Location tempLoc1 = PortCDMServices.getLocation(loc1);
                                Location tempLoc2 = PortCDMServices.getLocation(loc2);
                                locationsFrom.add(tempLoc1.getName());
                                locationsTo.add(tempLoc2.getName());
                            }
                            catch (NullPointerException e){
                                Log.e("CheckStatus-servType", e.toString());
                            }
                        } else {
                            Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                            locationsFrom.add(tempLoc.getName());
                            locationsTo.add("");
                        }
                        positionsAt.add("");
                        timeSequences.add(pcm.getTimeSequence());

                    }   else {
                        serviceObjects.add(pcm.getServiceState().getServiceObject().getText());
                        locationsFrom.add("");
                        locationsTo.add("");
                        Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                        positionsAt.add(tempLoc.getName());
                        timeSequences.add(pcm.getTimeSequence());
                    }

                } else {
                    serviceObjects.add("");
                    locationsFrom.add("");
                    locationsTo.add("");
                    Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                    positionsAt.add(tempLoc.getName());
                    timeSequences.add("");
                }
                dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                times.add(PortCDMServices.stringToTime(pcm.getTime()));
                iconImage = getResources().getDrawable(R.drawable.ic_alarm_black_24dp);
            }
        } catch (NullPointerException e) {
            Log.e("NoPortCallID", e.toString());
        }
        reverse(serviceObjects);
        reverse(positionsAt);
        reverse(locationsFrom);
        reverse(locationsTo);
        reverse(times);
        reverse(dates);
        reverse(timeSequences);

        ArrayAdapter<String> itemsAdapter =
                new CustomAdapterViewETA(this, R.layout.custom_listview_row_bssu, serviceObjects, positionsAt, locationsFrom, locationsTo, times, dates, timeSequences, iconImage);
        dialogListView.setAdapter(itemsAdapter);
    }

    public ArrayList<String> reverse(ArrayList<String> list) {
        if(list.size() > 1) {
            String value = list.remove(0);
            reverse(list);
            list.add(value);
        }
        return list;
    }

}


