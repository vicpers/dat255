package com.example.juliagustafsson.vessel_gui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import RESTServices.MessageBrokerQueue;
import ServiceEntities.ArrivalLocation;
import ServiceEntities.DepartureLocation;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;


/**
 * Created by juliagustafsson on 2017-05-15.
 */

public class TrafficAreaFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View locationstateView;
    AlertDialog.Builder dialogBuilder;
    private LocationType selectedLocationType;

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
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.statusView);
                selectedLocationType = LocationType.TRAFFIC_AREA;
                ArrayList<String> statusStringList = locationTypeQueueToString(selectedLocationType, true);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
            }
        });

        depTrafficA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.statusView);
                selectedLocationType = LocationType.TRAFFIC_AREA;
                ArrayList<String> statusStringList = locationTypeQueueToString(selectedLocationType, false);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
            }
        });

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

    private ArrayList<String> locationTypeQueueToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> stringList = new ArrayList<>();
        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for (PortCallMessage pcm : pcmList) {
                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            stringList.add(pcm.toString());
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            stringList.add(pcm.toString());
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-locType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }
        return stringList;
    }

}
