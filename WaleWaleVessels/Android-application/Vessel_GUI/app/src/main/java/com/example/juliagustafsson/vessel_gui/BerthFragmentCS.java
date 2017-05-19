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
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;

/**
 * Created by juliagustafsson on 2017-05-15.
 */

public class BerthFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View serviceStateView;
    private View locationstateView;
    private ServiceObject currentServiceObject;
    AlertDialog.Builder dialogBuilder;
    private LocationType selectedFromLocation;
    private LocationType selectedtoLocation;
    private LocationType selectedLocationType;

    public BerthFragmentCS() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.fragment_berth, null);

        Button arrivalBerth = (Button) rootView.findViewById(R.id.ArrivalBerth);
        Button departureBerth = (Button) rootView.findViewById(R.id.DepartureBerth);
        Button berthShift = (Button) rootView.findViewById(R.id.BerthShift);

        arrivalBerth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.statusView);
                selectedLocationType = LocationType.BERTH;
                createAlertDialog(locationstateView);
            }
        });

        departureBerth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.statusView);
                selectedLocationType = LocationType.BERTH;
                createAlertDialog(locationstateView);
            }
        });

        berthShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.statusView);
                currentServiceObject = ServiceObject.BERTH_SHIFTING;
                selectedFromLocation = LocationType.BERTH;
                selectedtoLocation = LocationType.BERTH;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
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

    private ArrayList<String> serviceObjectQueueToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> stringList = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            actualQueue.pollQueue();
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList){
                stringList.add(pcm.toString());
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return stringList;
    }


}