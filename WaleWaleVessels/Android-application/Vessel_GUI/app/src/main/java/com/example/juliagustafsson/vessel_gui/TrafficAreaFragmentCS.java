package com.example.juliagustafsson.vessel_gui;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import RESTServices.PortCDMServices;
import ServiceEntities.ArrivalLocation;
import ServiceEntities.DepartureLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;


/**
 * Created by juliagustafsson on 2017-05-15.
 * Fragment for viewing TrafficArea related updates
 */
public class TrafficAreaFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View locationstateView;
    AlertDialog.Builder dialogBuilder;
    private LocationType selectedLocationType;
    Drawable iconImage;

    private ArrayList<String> positions;
    private ArrayList<String> times;
    private ArrayList<String> dates;
    private ArrayList<String> timeTypes;


    public TrafficAreaFragmentCS() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.fragment_traffic_area, container, false);
        Button arrivalTrafficA = (Button) rootView.findViewById(R.id.ArrivalTrafficA);
        Button depTrafficA = (Button) rootView.findViewById(R.id.DepartureTrafficA);

        arrivalTrafficA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Arrival Traffic Area");
                selectedLocationType = LocationType.TRAFFIC_AREA;

                // Only runs one loop.
                locationTypeQueueToString(selectedLocationType, true);
                iconImage = getResources().getDrawable(R.drawable.ic_buoys);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterLSU(getActivity(),  R.layout.custom_listview_row_lsu, positions, timeTypes, times, dates, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(locationstateView);
                }
            }
        });

        depTrafficA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Departure Traffic Area");
                selectedLocationType = LocationType.TRAFFIC_AREA;
                // Only runs one loop.
                locationTypeQueueToString(selectedLocationType, false);
                iconImage = getResources().getDrawable(R.drawable.ic_buoys);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterLSU(getActivity(),  R.layout.custom_listview_row_lsu, positions, timeTypes, times, dates, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(locationstateView);
                }
            }
        });
        Bundle bundle = getArguments();
        if(bundle != null) {
            if(bundle.containsKey("notification")) {
                String notification = bundle.getString("notification");
                if (notification.equals("Traffic Area Arrival")) {
                    arrivalTrafficA.performClick();
                }
                else if (notification.equals("Traffic Area Departure")) {
                    depTrafficA.performClick();
                }
            }
        }

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

    private void createAlertDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.setView(v);
        dialogBuilder.show();
    }

    private void locationTypeQueueToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        positions = new ArrayList<>();
        times     = new ArrayList<>();
        dates     = new ArrayList<>();
        timeTypes = new ArrayList<>();

        try{
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList) {

                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                            positions.add(tempLoc.getName());
                            timeTypes.add(pcm.getTimeType());
                            times.add(PortCDMServices.stringToTime(pcm.getTime()));
                            dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                            positions.add(tempLoc.getName());
                            timeTypes.add(pcm.getTimeType());
                            times.add(PortCDMServices.stringToTime(pcm.getTime()));
                            dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
        }

        reverse(positions);
        reverse(times);
        reverse(dates);
        reverse(timeTypes);
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
