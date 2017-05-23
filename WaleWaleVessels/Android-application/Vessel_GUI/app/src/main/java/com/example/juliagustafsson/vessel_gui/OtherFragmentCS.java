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
import ServiceEntities.Location;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;


/**
 * Created by juliagustafsson on 2017-05-15.
 * Fragment for viewing Mooring related updates
 */

public class OtherFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View serviceStateView;
    private ServiceObject currentServiceObject;
    AlertDialog.Builder dialogBuilder;
    private Drawable iconImage;

    public OtherFragmentCS() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View rootView = inflater.inflate(R.layout.fragment_other, null);

        Button iceBreaking = (Button) rootView.findViewById(R.id.IceBreaking);
        Button cargoOp = (Button) rootView.findViewById(R.id.CargoOp);
        Button bunkeringOp = (Button) rootView.findViewById(R.id.Bunkering);

        iceBreaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                currentServiceObject = ServiceObject.ICEBREAKING_OPERATION;
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Ice Breaking");
                ArrayList<String> times = serviceObjectQueueTimesToString(currentServiceObject);
                ArrayList<String> dates = serviceObjectQueueDatesToString(currentServiceObject);
                ArrayList<String> timeTypes = serviceObjectQueueTimeTypesToString(currentServiceObject);
                ArrayList<String> timeSeq = serviceObjectQueueTimeSequenceToString(currentServiceObject);
                ArrayList<String> locFrom = serviceObjectQueueLocFromToString(currentServiceObject);
                ArrayList<String> locTo = serviceObjectQueueLocToToString(currentServiceObject);
                iconImage = getResources().getDrawable(R.drawable.ic_more);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterBSSU(getActivity(), R.layout.custom_listview_row_bssu, locFrom, locTo, timeTypes, times, dates, timeSeq, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(serviceStateView);
                }
            }
        });

        cargoOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                currentServiceObject = ServiceObject.CARGO_OPERATION;
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Cargo Operation");
                ArrayList<String> positions = serviceObjectQueuePositionsToString(currentServiceObject);
                ArrayList<String> times = serviceObjectQueueTimesToString(currentServiceObject);
                ArrayList<String> dates = serviceObjectQueueDatesToString(currentServiceObject);
                ArrayList<String> timeTypes = serviceObjectQueueTimeTypesToString(currentServiceObject);
                ArrayList<String> timeSeq = serviceObjectQueueTimeSequenceToString(currentServiceObject);
                iconImage = getResources().getDrawable(R.drawable.ic_more);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterSSU(getActivity(), R.layout.custom_listview_row_ssu, positions, timeTypes, times, dates, timeSeq, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(serviceStateView);
                }
            }
        });

        bunkeringOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                currentServiceObject = ServiceObject.BUNKERING_OPERATION;
                TextView title = (TextView) serviceStateView.findViewById(R.id.titleView);
                title.setText("Bunkering Operation");
                ArrayList<String> positions = serviceObjectQueuePositionsToString(currentServiceObject);
                ArrayList<String> times = serviceObjectQueueTimesToString(currentServiceObject);
                ArrayList<String> dates = serviceObjectQueueDatesToString(currentServiceObject);
                ArrayList<String> timeTypes = serviceObjectQueueTimeTypesToString(currentServiceObject);
                ArrayList<String> timeSeq = serviceObjectQueueTimeSequenceToString(currentServiceObject);
                iconImage = getResources().getDrawable(R.drawable.ic_more);
                if (times.isEmpty()) {
                    Toast toast = Toast.makeText(getContext(), "No status available", Toast.LENGTH_SHORT);
                    toast.show(); }
                else {
                    ArrayAdapter<String> itemsAdapter =
                            new CustomAdapterSSU(getActivity(), R.layout.custom_listview_row_ssu, positions, timeTypes, times, dates, timeSeq, iconImage);
                    dialogListView.setAdapter(itemsAdapter);
                    createAlertDialog(serviceStateView);
                }
            }
        });

        Bundle bundle = getArguments();
        if(bundle != null) {
            if(bundle.containsKey("notification")) {
                String notification = bundle.getString("notification");
                if (notification.equals("Bunkering operation")) {
                    bunkeringOp.performClick();
                }
                else if (notification.equals("Cargo operation")) {
                    cargoOp.performClick();
                }
                else if (notification.equals("Icebreaking operation")) {
                    iceBreaking.performClick();
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

    private ArrayList<String> serviceObjectQueueLocFromToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> positions = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList) {
                String locMRN = pcm.getLocationMRN();
                if (locMRN.contains("/")) {
                    String[] parts = locMRN.split("/");
                    try {
                        String loc1 = parts[0];
                        String loc2 = parts[1];
                        Location tempLoc = PortCDMServices.getLocation(loc1);
                        positions.add(tempLoc.getName());
                    }
                    catch (NullPointerException e){
                        Log.e("CheckStatus-servType", e.toString());
                    }
                } else {
                    Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                    positions.add(tempLoc.getName());
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(positions);
    }

    private ArrayList<String> serviceObjectQueueLocToToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> positions = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList) {
                String locMRN = pcm.getLocationMRN();
                if (locMRN.contains("/")) {
                    String[] parts = locMRN.split("/");
                    try {
                        String loc1 = parts[0];
                        String loc2 = parts[1];
                        Location tempLoc = PortCDMServices.getLocation(loc2);
                        positions.add(tempLoc.getName());
                    }
                    catch (NullPointerException e){
                        Log.e("CheckStatus-servType", e.toString());
                    }
                } else {
                    Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                    positions.add(tempLoc.getName());
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }
        return reverse(positions);
    }
    
    private ArrayList<String> serviceObjectQueuePositionsToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> positions = new ArrayList<>();
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
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(positions);
    }
    private ArrayList<String> serviceObjectQueueTimeTypesToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> timeTypes = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList){
                timeTypes.add(pcm.getTimeType());      }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(timeTypes);
    }
    private ArrayList<String> serviceObjectQueueTimesToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> times = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList){
                times.add(PortCDMServices.stringToTime(pcm.getTime()));  }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(times);
    }
    private ArrayList<String> serviceObjectQueueDatesToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> dates = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList){
                dates.add(PortCDMServices.stringToDate(pcm.getTime()));    }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(dates);
    }
    private ArrayList<String> serviceObjectQueueTimeSequenceToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> timeSequences = new ArrayList<>();
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList){
                timeSequences.add(pcm.getTimeSequence());      }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(timeSequences);
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
