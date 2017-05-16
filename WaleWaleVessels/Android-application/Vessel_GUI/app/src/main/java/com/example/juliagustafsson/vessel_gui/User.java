package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.util.Log;
import android.util.NoSuchPropertyException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.NoSuchElementException;

import RESTServices.MessageBrokerQueue;
import RESTServices.PortCDMServices;
import ServiceEntities.ServiceObject;
import ServiceEntities.TimeType;
import ServiceEntities.Vessel;

/**
 * Created by juliagustafsson on 2017-04-26.
 */

public class User implements Serializable{

    private Vessel vessel = null;
    private Context context;
    private boolean userLoggedIn = false;
    private HashMap<String, MessageBrokerQueue> messageBrokerMap = new HashMap<>();
    private String portCallID = null;

    public User(){}
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
        UserLocalStorage.setVessel(this.vessel);

        getMessageBrokerMap();
        createDefaultQueues();
    }

    public User(Context context, Vessel vessel) throws NoSuchElementException{
        try{
            this.vessel = vessel;
        } catch (NoSuchElementException e){
            throw e;
        }
        this.context = context;
        UserLocalStorage.setVessel(this.vessel);

        getMessageBrokerMap();
        createDefaultQueues();
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

    public boolean isUserLoggedIn() {
        UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
        userLoggedIn = userLocalStorage.getUserLoggedIn();
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
        UserLocalStorage userLocalStorage = new UserLocalStorage(this.context);
        userLocalStorage.setUserLoggedIn(userLoggedIn);
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
        if(vessel == null)
            throw new NoSuchPropertyException("No vessel for current user found");

        MessageBrokerQueue tempMbq = new MessageBrokerQueue();

        tempMbq.createUnfilteredQueue(vessel);
        messageBrokerMap.put("vessel",tempMbq);

        tempMbq = new MessageBrokerQueue();
        tempMbq.createUnfilteredQueue(vessel, TimeType.ESTIMATED);
        messageBrokerMap.put(TimeType.ESTIMATED.getText(),tempMbq);

        tempMbq = new MessageBrokerQueue();
        tempMbq.createUnfilteredQueue(vessel, TimeType.ACTUAL);
        messageBrokerMap.put(TimeType.ACTUAL.getText(),tempMbq);

        tempMbq = new MessageBrokerQueue();
        tempMbq.createUnfilteredQueue(vessel, ServiceObject.ANCHORING);
        messageBrokerMap.put(ServiceObject.ANCHORING.getText(),tempMbq);

        tempMbq = new MessageBrokerQueue();
        tempMbq.createUnfilteredQueue(vessel, ServiceObject.BERTH_SHIFTING);
        messageBrokerMap.put(ServiceObject.BERTH_SHIFTING.getText(),tempMbq);

        tempMbq = new MessageBrokerQueue();
        tempMbq.createUnfilteredQueue(vessel, ServiceObject.TOWAGE);
        messageBrokerMap.put(ServiceObject.TOWAGE.getText(),tempMbq);

        tempMbq = new MessageBrokerQueue();
        tempMbq.createUnfilteredQueue(vessel, ServiceObject.ICEBREAKING_OPERATION);
        messageBrokerMap.put(ServiceObject.ICEBREAKING_OPERATION.getText(),tempMbq);

        Log.wtf("Queues", messageBrokerMap.toString());
        setMessageBrokerMap(messageBrokerMap);
    }

}
