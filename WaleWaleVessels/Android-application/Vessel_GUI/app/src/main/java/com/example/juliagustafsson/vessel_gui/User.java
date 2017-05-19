package com.example.juliagustafsson.vessel_gui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.util.NoSuchPropertyException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import RESTServices.MessageBrokerQueue;
import RESTServices.PortCDMServices;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;
import ServiceEntities.TimeType;
import ServiceEntities.Vessel;

import static RESTServices.PortCDMServices.getActualPortData;
import static RESTServices.PortCDMServices.getStateDefinitions;

/**
 * Created by juliagustafsson on 2017-04-26.
 */

public class User implements Runnable{

    private Vessel vessel = null;
    private Context context;
    private HashMap<String, MessageBrokerQueue> messageBrokerMap = new HashMap<>();
    private String portCallID = null;
    private Thread thread;

    /**
     * @param context
     * @param vesselID
     * @throws NoSuchElementException
     */
    public User(Context context, String vesselID) throws NoSuchElementException{
        try{
            this.vessel = PortCDMServices.getVessel(vesselID);
        } catch (NoSuchElementException e){
            throw e;
        }
        this.context = context;
        setVessel(this.vessel);

        getMessageBrokerMap();
        getPortCallID();
        createDefaultQueues();
        getActualPortData();
        getStateDefinitions();

        this.thread = new Thread(this);
        this.thread.start();
    }

    public User(Context context, Vessel vessel) throws NullPointerException{
        if (vessel == null)
            throw new NullPointerException("vessel is null");
        this.vessel = vessel;
        this.context = context;
        setVessel(this.vessel);
        getMessageBrokerMap();
        getPortCallID();
        createDefaultQueues();
        getActualPortData();
        getStateDefinitions();

        this.thread = new Thread(this);
        this.thread.start();
    }

    public Vessel getVessel() {
        if (vessel == null) {
            UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
            vessel = userLocalStorage.getVessel();
        }
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
        userLocalStorage.setVessel(vessel);
        this.vessel = vessel;
    }

    public HashMap<String, MessageBrokerQueue> getMessageBrokerMap() {
        if (messageBrokerMap == null) {
            UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
            messageBrokerMap = userLocalStorage.getMessageBrokerMap();
        }
        return messageBrokerMap;
    }

    public void setMessageBrokerMap(HashMap<String, MessageBrokerQueue> messageBrokerMap) {
        this.messageBrokerMap = messageBrokerMap;
        UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
        userLocalStorage.setMessageBrokerMap(messageBrokerMap);
    }

    public String getPortCallID() {
        if(portCallID == null) {
            UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
            portCallID = userLocalStorage.getPortCallID();
        }
        return portCallID;
    }

    public void setPortCallID(String portCallID) {
        this.portCallID = portCallID;
        UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
        userLocalStorage.setPortCallID(portCallID);
    }

    public void clearUser(){
        UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
        userLocalStorage.clearUserData();
    }

    public void createDefaultQueues() throws NoSuchPropertyException{
        if(this.vessel == null) {
            getVessel();
            if(this.vessel == null)
                throw new NoSuchPropertyException("No vessel for current user found");
        }

        MessageBrokerQueue tempMbq = new MessageBrokerQueue();

        tempMbq.createUnfilteredQueue(vessel);
        messageBrokerMap.put("vessel", tempMbq);

        if(this.portCallID != null) {
            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID);
            messageBrokerMap.put("portcall", tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, TimeType.ESTIMATED);
            messageBrokerMap.put(TimeType.ESTIMATED.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, TimeType.ACTUAL);
            messageBrokerMap.put(TimeType.ACTUAL.getText(), tempMbq);

//  Creates all ServiceObject queues.
            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ANCHORING);
            messageBrokerMap.put(ServiceObject.ANCHORING.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_ANCHORING_OPERATION);
            messageBrokerMap.put(ServiceObject.ARRIVAL_ANCHORING_OPERATION.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_BERTH);
            messageBrokerMap.put(ServiceObject.ARRIVAL_BERTH.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_BERTH);
            messageBrokerMap.put(ServiceObject.DEPARTURE_BERTH.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.BERTH_SHIFTING);
            messageBrokerMap.put(ServiceObject.BERTH_SHIFTING.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.BUNKERING_OPERATION);
            messageBrokerMap.put(ServiceObject.BUNKERING_OPERATION.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.CARGO_OPERATION);
            messageBrokerMap.put(ServiceObject.CARGO_OPERATION.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ICEBREAKING_OPERATION);
            messageBrokerMap.put(ServiceObject.ICEBREAKING_OPERATION.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ESCORT_TOWAGE);
            messageBrokerMap.put(ServiceObject.ESCORT_TOWAGE.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.TOWAGE);
            messageBrokerMap.put(ServiceObject.TOWAGE.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.PILOTAGE);
            messageBrokerMap.put(ServiceObject.PILOTAGE.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_VTSAREA);
            messageBrokerMap.put(ServiceObject.ARRIVAL_VTSAREA.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_VTSAREA);
            messageBrokerMap.put(ServiceObject.DEPARTURE_VTSAREA.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_MOORING_OPERATION);
            messageBrokerMap.put(ServiceObject.ARRIVAL_MOORING_OPERATION.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_MOORING_OPERATION);
            messageBrokerMap.put(ServiceObject.DEPARTURE_MOORING_OPERATION.getText(), tempMbq);

            // Creates queues for LocationTypes
            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, LocationType.ANCHORING_AREA);
            messageBrokerMap.put(LocationType.ANCHORING_AREA.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, LocationType.BERTH);
            messageBrokerMap.put(LocationType.BERTH.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, LocationType.TRAFFIC_AREA);
            messageBrokerMap.put(LocationType.TRAFFIC_AREA.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, LocationType.PILOT_BOARDING_AREA);
            messageBrokerMap.put(LocationType.PILOT_BOARDING_AREA.getText(), tempMbq);

        }
        setMessageBrokerMap(messageBrokerMap);
    }

    public void run(){





        // notificationID allows you to update the notification later on.

        try {
            while(true) {

                for (Map.Entry<String, MessageBrokerQueue> mapEntry : messageBrokerMap.entrySet()) {
//                  Log.e(mapEntry.getKey(), mapEntry.getValue().getQueueId() + mapEntry.getValue().getQueue().toString());
                    ArrayList<PortCallMessage> pcmArray = mapEntry.getValue().pollQueue();

                    if (mapEntry.getKey().equals("vessel")){
                        for (PortCallMessage pcm : pcmArray) {
                            if (portCallID == null) {
                                Log.e("Got PortCallID", pcm.getPortCallId());
                                setPortCallID(pcm.getPortCallId());
                                createDefaultQueues();
                            }
                            sendNotification(pcm);
                            Log.e("NyttVesselPCM", pcm.toString());
                            long time = new Date().getTime();
                            String tmpStr = String.valueOf(time);
                            String last4Str = tmpStr.substring(tmpStr.length() - 5);
                            int notificationId = Integer.parseInt(last4Str);
                        }
                    }

                    if (mapEntry.getKey().equals("portcall")){
                        for (PortCallMessage pcm : pcmArray) {
                            Log.e("NyttPortCallIdPCM", pcm.toString());
                        }
                    }

                }
                setMessageBrokerMap(messageBrokerMap);
                this.thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void interrupt(){
        if(this.thread.isAlive())
            this.thread.interrupt();
    }

    public void sendNotification(PortCallMessage pcm){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_big_anchor);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);


        mBuilder.setContentTitle("Du har fått en förfrågan om " + pcm.getOperationType());
        mBuilder.setContentText("Öppna det");
        Intent resultIntent = new Intent(context, Send_ETA.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Send_ETA.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(notificationId, mBuilder.build());

    }

}
