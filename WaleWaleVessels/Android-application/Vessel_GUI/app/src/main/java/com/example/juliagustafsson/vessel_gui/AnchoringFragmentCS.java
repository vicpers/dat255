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
import ServiceEntities.ServiceObject;


/**
 * Created by juliagustafsson on 2017-05-15.
 * Fragment for viewing Anchoring related updates
 */

public class AnchoringFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View serviceStateView;
    private View locationstateView;
    private ServiceObject currentServiceObject;
    AlertDialog.Builder dialogBuilder;
    private LocationType selectedLocationType;
    private Drawable iconImage;
    private ArrayList<String> positions;
    private ArrayList<String> times;
    private ArrayList<String> dates;
    private ArrayList<String> timeTypes;
    private ArrayList<String> timeSeq;

    public AnchoringFragmentCS() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.fragment_anchoring, null);

        Button anchoring = (Button) rootView.findViewById(R.id.Anchoring);
        Button arrivalAnchoring = (Button) rootView.findViewById(R.id.ArrivalAnchoringArea);
        Button arrivalAnchoringOp = (Button) rootView.findViewById(R.id.ArrivalAnchoringOperation);
        Button departureAnchoringOp = (Button) rootView.findViewById(R.id.DepartureAnchoringOperation);
        Button departureAnchoring = (Button) rootView.findViewById(R.id.DepartureAnchoringArea);

        anchoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Anchoring");
                currentServiceObject = ServiceObject.ANCHORING;

                // Only runs one loop
                serviceObjectQueueToString(currentServiceObject);
//                ArrayList<String> positions = serviceObjectQueuePositionsToString(currentServiceObject);
//                ArrayList<String> times = serviceObjectQueueTimesToString(currentServiceObject);
//                ArrayList<String> dates = serviceObjectQueueDatesToString(currentServiceObject);
//                ArrayList<String> timeTypes = serviceObjectQueueTimeTypesToString(currentServiceObject);
//                ArrayList<String> timeSeq = serviceObjectQueueTimeSequenceToString(currentServiceObject);
                iconImage = getResources().getDrawable(R.drawable.ic_big_anchor);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterSSU(getActivity(), R.layout.custom_listview_row_ssu, positions, timeTypes, times, dates, timeSeq, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(serviceStateView);
                }
            }
        });

        arrivalAnchoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Arrival Anchoring");
                selectedLocationType = LocationType.ANCHORING_AREA;

                // Only runs one loop.
                locationTypeQueueToString(selectedLocationType, true);

                iconImage = getResources().getDrawable(R.drawable.ic_big_anchor);
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

        arrivalAnchoringOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Arrival Anchoring Operation");
                currentServiceObject = ServiceObject.ARRIVAL_ANCHORING_OPERATION;

                // Only runs one loop
                serviceObjectQueueToString(currentServiceObject);

                iconImage = getResources().getDrawable(R.drawable.ic_big_anchor);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterSSU(getActivity(), R.layout.custom_listview_row_ssu, positions, timeTypes, times, dates, timeSeq, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(serviceStateView);
                }

            }
        });

        departureAnchoringOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Departure Anchoring Operat.");
                currentServiceObject = ServiceObject.DEPARTURE_ANCHORING_OPERATION;

                // Only runs one loop
                serviceObjectQueueToString(currentServiceObject);

                iconImage = getResources().getDrawable(R.drawable.ic_big_anchor);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterSSU(getActivity(), R.layout.custom_listview_row_ssu, positions, timeTypes, times, dates, timeSeq, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(serviceStateView);
                }
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

                // Only runs one loop.
                locationTypeQueueToString(selectedLocationType, false);
                iconImage = getResources().getDrawable(R.drawable.ic_big_anchor);
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
                if (notification.equals("Anchoring")) {
                    anchoring.performClick();
                }
                else if (notification.equals("Arrival to anchoring operation")) {
                    arrivalAnchoringOp.performClick();
                }
                else if (notification.equals("Departure from anchoring operation")){
                    departureAnchoringOp.performClick();
                }
                else if (notification.equals("Anchoring Area Arrival")){
                    arrivalAnchoring.performClick();
                }
                else if (notification.equals("Anchoring Area Departure")){
                    departureAnchoring.performClick();
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

    private void serviceObjectQueueToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        positions = new ArrayList<>();
        times     = new ArrayList<>();
        dates     = new ArrayList<>();
        timeTypes = new ArrayList<>();
        timeSeq   = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList) {
                String locMRN = pcm.getLocationMRN();
                if (locMRN.contains("/")) {
                    String[] parts = locMRN.split("/");
                    String loc1 = parts[0];
                    String loc2 = parts[1];
                    Location tempLoc = PortCDMServices.getLocation(loc1);
                    positions.add(tempLoc.getName());
                } else {
                    Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                    positions.add(tempLoc.getName());
                }
                timeTypes.add(pcm.getTimeType());
                times.add(PortCDMServices.stringToTime(pcm.getTime()));
                dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                timeSeq.add(pcm.getTimeSequence());




            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
        }

         reverse(positions);
         reverse(times);
         reverse(dates);
         reverse(timeTypes);
         reverse(timeSeq);
    }

    private void locationTypeQueueToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        positions = new ArrayList<>();
        times     = new ArrayList<>();
        dates     = new ArrayList<>();
        timeTypes = new ArrayList<>();
        timeSeq   = new ArrayList<>();
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
                            timeSeq.add(pcm.getTimeSequence());
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                            positions.add(tempLoc.getName());
                            timeTypes.add(pcm.getTimeType());
                            times.add(PortCDMServices.stringToTime(pcm.getTime()));
                            dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                            timeSeq.add(pcm.getTimeSequence());
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
        reverse(timeSeq);
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
