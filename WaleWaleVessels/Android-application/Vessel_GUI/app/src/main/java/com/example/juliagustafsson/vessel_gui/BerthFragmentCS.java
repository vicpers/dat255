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
 */

public class BerthFragmentCS extends android.app.Fragment implements View.OnClickListener {

    private View serviceStateView;
    private View locationstateView;
    private ServiceObject currentServiceObject;
    AlertDialog.Builder dialogBuilder;
    private LocationType selectedFromLocation;
    private LocationType selectedtoLocation;
    private LocationType selectedLocationType;
    private Drawable iconImage;

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
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Arrival Berth");
                selectedLocationType = LocationType.BERTH;
                ArrayList<String> positions = locationTypeQueuePositionsToString(selectedLocationType, true);
                ArrayList<String> times = locationTypeQueueTimesToString(selectedLocationType, true);
                ArrayList<String> dates = locationTypeQueueDatesToString(selectedLocationType, true);
                ArrayList<String> timeTypes = locationTypeQueueTimeTypesToString(selectedLocationType, true);
                iconImage = getResources().getDrawable(R.drawable.ic_mooring_point);
                ArrayAdapter<String> itemsAdapter =
                        new CustomAdapterLSU(getActivity(),  R.layout.custom_listview_row_lsu, positions, timeTypes, times, dates, iconImage);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
            }
        });

        departureBerth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationstateView = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) locationstateView.findViewById(R.id.checkStatus);
                TextView title = (TextView) locationstateView.findViewById(R.id.titleView);
                title.setText("Departure Berth");
                selectedLocationType = LocationType.BERTH;
                ArrayList<String> positions = locationTypeQueuePositionsToString(selectedLocationType, false);
                ArrayList<String> times = locationTypeQueueTimesToString(selectedLocationType, false);
                ArrayList<String> dates = locationTypeQueueDatesToString(selectedLocationType, false);
                ArrayList<String> timeTypes = locationTypeQueueTimeTypesToString(selectedLocationType, false);
                iconImage = getResources().getDrawable(R.drawable.ic_mooring_point);
                ArrayAdapter<String> itemsAdapter =
                        new CustomAdapterLSU(getActivity(), R.layout.custom_listview_row_lsu, positions, timeTypes, times, dates, iconImage);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(locationstateView);
            }});

        berthShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceStateView  = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_status, null);
                ListView dialogListView = (ListView) serviceStateView.findViewById(R.id.checkStatus);
                currentServiceObject = ServiceObject.BERTH_SHIFTING;
                selectedFromLocation = LocationType.BERTH;
                selectedtoLocation = LocationType.BERTH;
                ArrayList<String> statusStringList = serviceObjectQueueToString(currentServiceObject);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, statusStringList);
                dialogListView.setAdapter(itemsAdapter);
                createAlertDialog(serviceStateView);}

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

    private ArrayList<String> serviceObjectQueuePositionsToString(ServiceObject serviceObject){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> positions = new ArrayList<>();
        Log.e("QP", "calls method serviceObject");
        try{
            MessageBrokerQueue actualQueue = queueMap.get(serviceObject.getText());
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList) {
                String locMRN = pcm.getLocationMRN();
                Log.e("MRNS", locMRN);
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
            actualQueue.pollQueue();
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
            actualQueue.pollQueue();
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
            actualQueue.pollQueue();
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
            actualQueue.pollQueue();
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            for(PortCallMessage pcm : pcmList){
                timeSequences.add(pcm.getTimeSequence());      }
        } catch (NullPointerException e){
            Log.e("CheckStatus-servType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(timeSequences);
    }

    private ArrayList<String> locationTypeQueuePositionsToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> positions = new ArrayList<>();

        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();

            //Retrieves current queue and stores as an ArrayList of PortCallMessages
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            //Reads through all PCMs and stores them as Strings
            for (PortCallMessage pcm : pcmList) {
                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                            positions.add(tempLoc.getName());
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            Location tempLoc = PortCDMServices.getLocation(pcm.getLocationMRN());
                            positions.add(tempLoc.getName());
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-locType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }

        return reverse(positions);
    }
    private ArrayList<String> locationTypeQueueTimesToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> times = new ArrayList<>();

        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();

            //Retrieves current queue and stores as an ArrayList of PortCallMessages
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            //Reads through all PCMs and stores them as Strings
            for (PortCallMessage pcm : pcmList) {
                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            times.add(PortCDMServices.stringToTime(pcm.getTime()));
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            times.add(PortCDMServices.stringToTime(pcm.getTime()));
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-locType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }
        return reverse(times);
    }
    private ArrayList<String> locationTypeQueueDatesToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> dates = new ArrayList<>();

        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();

            //Retrieves current queue and stores as an ArrayList of PortCallMessages
            ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            //Reads through all PCMs and stores them as Strings
            for (PortCallMessage pcm : pcmList) {
                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            dates.add(PortCDMServices.stringToDate(pcm.getTime()));
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            dates.add(PortCDMServices.stringToDate(pcm.getTime()));                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-locType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }
        return reverse(dates);
    }
    private ArrayList<String> locationTypeQueueTimeTypesToString(LocationType locationType, boolean isArrival){
        HashMap<String, MessageBrokerQueue> queueMap = UserLocalStorage.getMessageBrokerMap();
        ArrayList<String> timeTypes = new ArrayList<>();

        try {
            MessageBrokerQueue actualQueue = queueMap.get(locationType.getText());
            actualQueue.pollQueue();

            //Retrieves current queue and stores as an ArrayList of PortCallMessages
        ArrayList<PortCallMessage> pcmList = actualQueue.getQueue();

            //Reads through all PCMs and stores them as Strings
            for (PortCallMessage pcm : pcmList) {
                LocationState locationState = pcm.getLocationState();
                if(locationState != null){
                    if(isArrival) {
                        ArrivalLocation arrivalLocation = locationState.getArrivalLocation();
                        if(arrivalLocation != null) {
                            timeTypes.add(pcm.getTimeType());
                        }
                    } else {
                        DepartureLocation departureLocation = locationState.getDepartureLocation();
                        if(departureLocation != null) {
                            timeTypes.add(pcm.getTimeType());
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Log.e("CheckStatus-locType", e.toString());
            //TODO Visa felmeddelande för användaren.
        }
        return reverse(timeTypes);
    }

    public ArrayList<String> reverse(ArrayList<String> list) {
        if(list.size() > 1) {
            String value = list.remove(0);
            reverse(list);
            list.add(value);
        }
        return list;
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
