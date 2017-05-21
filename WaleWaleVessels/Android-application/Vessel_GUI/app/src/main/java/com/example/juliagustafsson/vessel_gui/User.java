package com.example.juliagustafsson.vessel_gui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.util.NoSuchPropertyException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;
import ServiceEntities.TimeType;
import ServiceEntities.Vessel;

import static RESTServices.PortCDMServices.getActualPortData;
import static RESTServices.PortCDMServices.getStateDefinitions;

/**
 * A class for calculations regarding User information. By communicating with UserLocalStorage,
 * data is saved even if the Application is closed. It creates all MessageBrokerQueues needed
 * and by launching a Thread, the class continuously searches for new incoming PortCall Messages.
 * When a new PortCall Message is identified, a notification is sent.
 */

public class User implements Runnable, Serializable{

    private Vessel vessel = null;
    private Context context;
    private HashMap<String, MessageBrokerQueue> messageBrokerMap;
    private String portCallID = null;
    private Thread thread;

    /** Creates a User
     * @param context The Context of the launching Activity
     * @param vessel The Vessel of the User
     * @throws NullPointerException If Vessel is Null
     */
    public User(Context context, Vessel vessel) throws NullPointerException{
        if (vessel == null)
            throw new NullPointerException("vessel is null");
        this.vessel = vessel;
        this.context = context;
        new UserLocalStorage(context);
        setVessel(this.vessel);
        messageBrokerMap = getMessageBrokerMap();
        getPortCallID();
        createDefaultQueues();
        getActualPortData();
        getStateDefinitions();

        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * @return the Vessel of the User
     */
    public Vessel getVessel() {
        if (vessel == null) {
            vessel = UserLocalStorage.getVessel();
        }
        return vessel;
    }

    /**
     * @param vessel Set the user Vessel
     */
    public void setVessel(Vessel vessel) {
        UserLocalStorage.setVessel(vessel);
        this.vessel = vessel;
    }

    /**
     * @return A HashMap containing MessagebrokerQueues with Strings as keys. All PortCallMessages is
     * accessible through this data structure.
     */
    public HashMap<String, MessageBrokerQueue> getMessageBrokerMap() {
        HashMap<String, MessageBrokerQueue> mb = UserLocalStorage.getMessageBrokerMap();
        if(mb == null)
            return new HashMap<String, MessageBrokerQueue>();
        return mb;
    }

    /**
     * @param messageBrokerMap A HashMap containing MessageBrokerQueues with Strings as keys.
     */
    public void setMessageBrokerMap(HashMap<String, MessageBrokerQueue> messageBrokerMap) {
        this.messageBrokerMap = messageBrokerMap;
        UserLocalStorage.setMessageBrokerMap(messageBrokerMap);
    }

    /**
     * @return A String with the users PortCallID
     */
    public String getPortCallID() {
        if(portCallID == null) {
            portCallID = UserLocalStorage.getPortCallID();
        }
        return portCallID;
    }

    /**
     * @param portCallID A String with the users PortCallID
     */
    public void setPortCallID(String portCallID) {
        this.portCallID = portCallID;
        UserLocalStorage.setPortCallID(portCallID);
    }

    /**
     * Removes all saved User data
     */
    public void clearUser(){
        UserLocalStorage.clearUserData();
    }

    /**
     * Creates all the MessageBrokerQueues needed for the User to receive all relevant PortCall Messages.
     * @throws NoSuchPropertyException
     */
    public void createDefaultQueues() throws NoSuchPropertyException{
        if(this.vessel == null) {
            getVessel();
            if(this.vessel == null)
                throw new NoSuchPropertyException("No vessel for current user found");
        }

        MessageBrokerQueue tempMbq = new MessageBrokerQueue();

        if(!messageBrokerMap.containsKey("vessel")) {
            tempMbq.createUnfilteredQueue(vessel);
            messageBrokerMap.put("vessel", tempMbq);
        }

        if(this.portCallID != null) {
            if(!messageBrokerMap.containsKey("portcall")){
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID);
                messageBrokerMap.put("portcall", tempMbq);
            }

            if(!messageBrokerMap.containsKey(TimeType.ESTIMATED.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, TimeType.ESTIMATED);
                messageBrokerMap.put(TimeType.ESTIMATED.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(TimeType.ACTUAL.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, TimeType.ACTUAL);
                messageBrokerMap.put(TimeType.ACTUAL.getText(), tempMbq);
            }

//  Creates all ServiceObject queues.
            if(!messageBrokerMap.containsKey(ServiceObject.ANCHORING.getText())){
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ANCHORING);
                messageBrokerMap.put(ServiceObject.ANCHORING.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.ARRIVAL_ANCHORING_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_ANCHORING_OPERATION);
                messageBrokerMap.put(ServiceObject.ARRIVAL_ANCHORING_OPERATION.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.DEPARTURE_ANCHORING_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_ANCHORING_OPERATION);
                messageBrokerMap.put(ServiceObject.DEPARTURE_ANCHORING_OPERATION.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.ARRIVAL_BERTH.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_BERTH);
                messageBrokerMap.put(ServiceObject.ARRIVAL_BERTH.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.DEPARTURE_BERTH.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_BERTH);
                messageBrokerMap.put(ServiceObject.DEPARTURE_BERTH.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.BERTH_SHIFTING.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.BERTH_SHIFTING);
                messageBrokerMap.put(ServiceObject.BERTH_SHIFTING.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.BUNKERING_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.BUNKERING_OPERATION);
                messageBrokerMap.put(ServiceObject.BUNKERING_OPERATION.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.CARGO_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.CARGO_OPERATION);
                messageBrokerMap.put(ServiceObject.CARGO_OPERATION.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.ICEBREAKING_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ICEBREAKING_OPERATION);
                messageBrokerMap.put(ServiceObject.ICEBREAKING_OPERATION.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.ESCORT_TOWAGE.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ESCORT_TOWAGE);
                messageBrokerMap.put(ServiceObject.ESCORT_TOWAGE.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.TOWAGE.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.TOWAGE);
                messageBrokerMap.put(ServiceObject.TOWAGE.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.PILOTAGE.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.PILOTAGE);
                messageBrokerMap.put(ServiceObject.PILOTAGE.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.ARRIVAL_VTSAREA.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_VTSAREA);
                messageBrokerMap.put(ServiceObject.ARRIVAL_VTSAREA.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.DEPARTURE_VTSAREA.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_VTSAREA);
                messageBrokerMap.put(ServiceObject.DEPARTURE_VTSAREA.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.ARRIVAL_MOORING_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ARRIVAL_MOORING_OPERATION);
                messageBrokerMap.put(ServiceObject.ARRIVAL_MOORING_OPERATION.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(ServiceObject.DEPARTURE_MOORING_OPERATION.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, ServiceObject.DEPARTURE_MOORING_OPERATION);
                messageBrokerMap.put(ServiceObject.DEPARTURE_MOORING_OPERATION.getText(), tempMbq);
            }

            // Creates queues for LocationTypes
            if(!messageBrokerMap.containsKey(LocationType.ANCHORING_AREA.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, LocationType.ANCHORING_AREA);
                messageBrokerMap.put(LocationType.ANCHORING_AREA.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(LocationType.BERTH.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, LocationType.BERTH);
                messageBrokerMap.put(LocationType.BERTH.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(LocationType.TRAFFIC_AREA.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, LocationType.TRAFFIC_AREA);
                messageBrokerMap.put(LocationType.TRAFFIC_AREA.getText(), tempMbq);
            }

            if(!messageBrokerMap.containsKey(LocationType.PILOT_BOARDING_AREA.getText())) {
                tempMbq = new MessageBrokerQueue();
                tempMbq.createUnfilteredQueue(portCallID, LocationType.PILOT_BOARDING_AREA);
                messageBrokerMap.put(LocationType.PILOT_BOARDING_AREA.getText(), tempMbq);
            }
        }
        setMessageBrokerMap(messageBrokerMap);
    }

    /**
     * Runs thread and searches for new incoming PortCall Messages
     */
    public void run(){

        // notificationID allows you to update the notification later on.

        try {
            while(true) {
                messageBrokerMap = getMessageBrokerMap();
                for (Map.Entry<String, MessageBrokerQueue> mapEntry : messageBrokerMap.entrySet()) {
                    ArrayList<PortCallMessage> pcmArray;
                    pcmArray = mapEntry.getValue().pollQueue();

                    if (mapEntry.getKey().equals("vessel") && pcmArray != null && pcmArray.size() > 0){
                        Log.e("SizePcm", pcmArray.size() + "");
                        for (PortCallMessage pcm : pcmArray) {
                            if (portCallID == null || portCallID.equals("null") || portCallID.equals("")) {
                                Log.e("Got New PortCallID", pcm.getPortCallId());
                                setPortCallID(pcm.getPortCallId());
                                createDefaultQueues();
                            } else
                                Log.e("PortCallID", portCallID);
                            sendNotification(pcm);
                            Log.e("NyttVesselPCM", pcm.toString());
                            long time = new Date().getTime();
                            String tmpStr = String.valueOf(time);
                            String last4Str = tmpStr.substring(tmpStr.length() - 5);
                            int notificationId = Integer.parseInt(last4Str);
                        }
                    }

                    if (mapEntry.getKey().equals("portcall") && pcmArray != null && pcmArray.size() > 0){
                        Log.e("SizePcm", pcmArray.size() + "");
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

    /**
     * Interrupts thread
     */
    public void interrupt(){
        if(this.thread.isAlive())
            this.thread.interrupt();
    }

    /**
     * Creates a notification from a PortCall Message.
     * @param pcm The PortCall Message which the User needs to know about.
     */
    public void sendNotification(PortCallMessage pcm){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_big_anchor);
        mBuilder.setAutoCancel(true);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.parseInt(last4Str);

        mBuilder.setContentTitle("New PCM regarding "+ pcm.getOperationType());
        

        mBuilder.setContentText("Click to view");
        Intent resultIntent = new Intent(context, CheckStatus.class);
        resultIntent.putExtra("clickedNotification", pcm.getOperationType());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(CheckStatus.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(notificationId, mBuilder.build());

    }

}
