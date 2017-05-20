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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
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

public class AnchoringFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View serviceStateView;
    private View locationstateView;
    private ServiceObject currentServiceObject;
    AlertDialog.Builder dialogBuilder;
    private LocationType selectedAtLocation;
    private LocationType selectedLocationType;

    public AnchoringFragmentCS() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.fragment_anchoring, null);

        Button anchoring = (Button) rootView.findViewById(R.id.Anchoring);
        Button arrivalAnchoring = (Button) rootView.findViewById(R.id.ArrivalAnchoringArea);
        Button arrivalAnchoringOp = (Button) rootView.findViewById(R.id.ArrivalAnchoringOperation);
        Button departureAnchoring = (Button) rootView.findViewById(R.id.DepartureAnchoringArea);

        anchoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Anchoring");
                currentServiceObject = ServiceObject.ANCHORING;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
            }
        });

        arrivalAnchoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Arrival Anchoring");
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                selectedLocationType = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = locationTypeQueueToString(selectedLocationType, true);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
            }
        });

        arrivalAnchoringOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Arrival Anchoring Operation");
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                currentServiceObject = ServiceObject.ARRIVAL_ANCHORING_OPERATION;
                selectedAtLocation = LocationType.ANCHORING_AREA;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);
            }
        });

        departureAnchoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Departure Anchoring");
                selectedLocationType = LocationType.ANCHORING_AREA;
                ArrayList<ArrayList<String>> statusStringList = locationTypeQueueToString(selectedLocationType, false);
                ArrayAdapter<String> itemsAdapter =
                        new CustomAdapterLSU(getActivity(), statusStringList);
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

    private ArrayList<ArrayList<String>> locationTypeQueueToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<ArrayList<String>> stringList = new ArrayList<>();
        ArrayList<String> locationTimeSequence = new ArrayList<>();
        ArrayList<String> position = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();

        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();

            //hämtar befintlig kö och lagrar som pcmList
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            //läser igenom alla PCMer och lagrar som strings
            for (PortCallMessage pcm : pcmList) {
                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            locationTimeSequence.add(pcm.getTimeSequence());
                            position.add(pcm.getLo)

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
        stringList.add(locationTimeSequence);
        stringList.add(position);
        stringList.add(time);
        stringList.add(date);
        return stringList;
    }

    private ArrayList<String>

}
