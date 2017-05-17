package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.util.Log;
import android.util.NoSuchPropertyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import RESTServices.MessageBrokerQueue;
import RESTServices.PortCDMServices;
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
    private boolean userLoggedIn = false;
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
        tempMbq.pollQueue();
        messageBrokerMap.put("vessel", tempMbq);

        if(this.portCallID != null) {
            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID);
            tempMbq.pollQueue();
            messageBrokerMap.put("portcall", tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, TimeType.ESTIMATED);
            tempMbq.pollQueue();
            messageBrokerMap.put(TimeType.ESTIMATED.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, TimeType.ACTUAL);
            tempMbq.pollQueue();
            messageBrokerMap.put(TimeType.ACTUAL.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ANCHORING);
            tempMbq.pollQueue();
            messageBrokerMap.put(ServiceObject.ANCHORING.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.BERTH_SHIFTING);
            tempMbq.pollQueue();
            messageBrokerMap.put(ServiceObject.BERTH_SHIFTING.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.TOWAGE);
            tempMbq.pollQueue();
            messageBrokerMap.put(ServiceObject.TOWAGE.getText(), tempMbq);

            tempMbq = new MessageBrokerQueue();
            tempMbq.createUnfilteredQueue(portCallID, ServiceObject.ICEBREAKING_OPERATION);
            tempMbq.pollQueue();
            messageBrokerMap.put(ServiceObject.ICEBREAKING_OPERATION.getText(), tempMbq);

        }
        setMessageBrokerMap(messageBrokerMap);
    }

    public void run(){
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
                            Log.e("NyttVesselPCM", pcm.toString());
                        }
                    }

                    if (mapEntry.getKey().equals("portcall")){
                        for (PortCallMessage pcm : pcmArray) {
                            Log.e("NyttPortCallIdPCM", pcm.toString());
                        }
                    }
                    /*MessageBrokerQueue messageBrokerQueue = messageBrokerMap.get("vessel");
                    if (messageBrokerQueue != null) {
                        ArrayList<PortCallMessage> pcmArray = messageBrokerQueue.pollQueue();
                        for (PortCallMessage pcm : pcmArray) {
                            if (portCallID == null) {
                                Log.e("Got PortCallID", pcm.getPortCallId());
                                setPortCallID(pcm.getPortCallId());
                                createDefaultQueues();
                            }
                            Log.e("NyttPCM", pcm.toString());
                        }
                    }
                    messageBrokerQueue = messageBrokerMap.get("portcall");
                    if (messageBrokerQueue != null) {
                        ArrayList<PortCallMessage> pcmArray = messageBrokerQueue.pollQueue();
                        for (PortCallMessage pcm : pcmArray) {
                            if (portCallID == null) {
                                Log.e("Got PortCallID", pcm.getPortCallId());
                                setPortCallID(pcm.getPortCallId());
                            }
                            Log.e("NyttPCM", pcm.toString());
                        }
                    }*/
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

}
