package com.example.juliagustafsson.vessel_gui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;

/**
 * Created by MattiasLundell on 2017-05-03.
 */

public class CheckStatus extends AppCompatActivity implements View.OnClickListener {
    private View serviceStateView;
    private View locationstateView;
    private ServiceObject currentServiceObject;

    AlertDialog.Builder dialogBuilder;
    private String selectedTimeSequence;
    private LocationType selectedFromLocation;
    private LocationType selectedtoLocation;
    private LocationType selectedAtLocation;
    private LocationType selectedLocationType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);
    }

    @Override
    public void onClick(View v) {

    }

    public void sendNewServiceState(View v) {
        serviceStateView  = getLayoutInflater().inflate(R.layout.dialog_check_status, null);
        ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.statusView);
        switch( v.getId() ) {
            case R.id.Anchoring: {
                currentServiceObject = ServiceObject.ANCHORING;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.ArrivalAnchoringOperation: {
                currentServiceObject = ServiceObject.ARRIVAL_ANCHORING_OPERATION;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.VTSAreaArrival: {
                currentServiceObject = ServiceObject.ARRIVAL_VTSAREA;
                selectedAtLocation = LocationType.TRAFFIC_AREA;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.BerthShifting: {
                currentServiceObject = ServiceObject.BERTH_SHIFTING;
                selectedFromLocation = LocationType.BERTH;
                selectedtoLocation = LocationType.BERTH;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.BunkeringOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.BUNKERING_OPERATION;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.CargoOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.CARGO_OPERATION;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.VTSAreaDeparture: {
                currentServiceObject = ServiceObject.DEPARTURE_VTSAREA;
                selectedAtLocation = LocationType.TRAFFIC_AREA;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.EscortTowage: {
                currentServiceObject = ServiceObject.ESCORT_TOWAGE;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.IceBreakingOperation: {
                currentServiceObject = ServiceObject.ICEBREAKING_OPERATION;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.ArrivalMooringOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.ARRIVAL_MOORING_OPERATION;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.DepartureMooringOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.DEPARTURE_MOORING_OPERATION;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.Pilotage: {
                currentServiceObject = ServiceObject.PILOTAGE;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.Towage: {
                currentServiceObject = ServiceObject.TOWAGE;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
                break;
            }
        }
    }

    public void sendNewLocationState(View v) {
        locationstateView = getLayoutInflater().inflate(R.layout.dialog_check_status, null);
        ListView dialogListView = (ListView) locationstateView.findViewById(R.id.statusView);
        switch( v.getId() ) {
            case R.id.ArrivalAnchoringArea: {
                selectedLocationType = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = locationTypeQueueToString(selectedLocationType);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.DepartureAnchoringArea: {
                selectedLocationType = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = locationTypeQueueToString(selectedLocationType);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.ArrivalBerth: {
                selectedLocationType = LocationType.BERTH;
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.DepartureBerth: {
                selectedLocationType = LocationType.BERTH;
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.ArrivalPilotBoardingArea: {
                selectedLocationType = LocationType.PILOT_BOARDING_AREA;
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.ArrivalTrafficArea: {
                selectedLocationType = LocationType.TRAFFIC_AREA;
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.DepartureTrafficArea: {
                selectedLocationType = LocationType.TRAFFIC_AREA;
                createAlertDialog(locationstateView);
                break;
            }
        }

    }

    private void createAlertDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(CheckStatus.this);
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

    private ArrayList<String> locationTypeQueueToString(LocationType locationType){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> stringList = new ArrayList<>();
        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for (PortCallMessage pcm : pcmList) {
                stringList.add(pcm.toString());
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-locType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }


        return stringList;
    }

}


