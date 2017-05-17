package com.example.juliagustafsson.vessel_gui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        switch( v.getId() ) {
            case R.id.Anchoring: {
                currentServiceObject = ServiceObject.ANCHORING;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                TextView dialogStatusView = (TextView) serviceStateView.findViewById(R.id.statusView);
                String statusString = serviceObjectQueueToString(currentServiceObject);
                dialogStatusView.setText(statusString);
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.ArrivalAnchoringOperation: {
                currentServiceObject = ServiceObject.ARRIVAL_ANCHORING_OPERATION;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.VTSAreaArrival: {
                currentServiceObject = ServiceObject.ARRIVAL_VTSAREA;
                selectedAtLocation = LocationType.TRAFFIC_AREA;
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.BerthShifting: {
                currentServiceObject = ServiceObject.BERTH_SHIFTING;
                selectedFromLocation = LocationType.BERTH;
                selectedtoLocation = LocationType.BERTH;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.BunkeringOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.BUNKERING_OPERATION;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.CargoOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.CARGO_OPERATION;
                createAlertDialog(serviceStateView);
                break;
            }

            case R.id.VTSAreaDeparture: {
                currentServiceObject = ServiceObject.DEPARTURE_VTSAREA;
                selectedAtLocation = LocationType.TRAFFIC_AREA;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.EscortTowage: {
                currentServiceObject = ServiceObject.ESCORT_TOWAGE;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.IceBreakingOperation: {
                currentServiceObject = ServiceObject.ICEBREAKING_OPERATION;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.ArrivalMooringOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.ARRIVAL_MOORING_OPERATION;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO At which location?
            case R.id.DepartureMooringOperation: {
                selectedAtLocation = LocationType.BERTH;
                currentServiceObject = ServiceObject.DEPARTURE_MOORING_OPERATION;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.Pilotage: {
                currentServiceObject = ServiceObject.PILOTAGE;
                createAlertDialog(serviceStateView);
                break;
            }

            //TODO Between which locations?
            case R.id.Towage: {
                currentServiceObject = ServiceObject.TOWAGE;
                createAlertDialog(serviceStateView);
                break;
            }
        }
    }

    public void sendNewLocationState(View v) {
        locationstateView = getLayoutInflater().inflate(R.layout.dialog_check_status, null);
        switch( v.getId() ) {
            case R.id.ArrivalAnchoringArea: {
                selectedLocationType = LocationType.ANCHORING_AREA;
                createAlertDialog(locationstateView);
                break;
            }

            case R.id.DepartureAnchoringArea: {
                selectedLocationType = LocationType.ANCHORING_AREA;
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


    private String serviceObjectQueueToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
        actualQueue.pollQueue();
        ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();
        String returnString = "";
        for(PortCallMessage pcm : pcmList){
            returnString += pcm.getServiceState().toString() + "\n";
            Log.e("Inuti 1", pcm.toString());
        }

        ArrayList<PortCallMessage> pcmList2 = actualQueue.getQueue();
        for(PortCallMessage pcm : pcmList2){
            returnString += pcm.getServiceState().toString() + "\n";
            Log.e("Inuti 1", pcm.toString());
        }


        return returnString;
    }

}


