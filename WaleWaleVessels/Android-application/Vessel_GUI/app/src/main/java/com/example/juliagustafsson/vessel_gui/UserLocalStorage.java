package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.Vessel;



/**
 * Created by juliagustafsson on 2017-04-26.
 */

public class UserLocalStorage {
    private Gson gson;
    private User user = null;
    private String portCallID = null;
    private HashMap<String, MessageBrokerQueue> messageBrokerMap = null;
    private Vessel vessel = null;

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }


    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("Vessel ID", user.vesselID);
        spEditor.commit();
        this.user = user;
    }

    public User getLoggedInUser() {
        if (user == null){
            user = new User(userLocalDatabase.getString("Vessel ID",""));
        }
            return user;
    }

    public void setUserLoggedIn (boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("Logged In", loggedIn);
        spEditor.commit();
    }

    public void clearUserData () {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
        user = null;
        portCallID = null;
        messageBrokerMap = null;
        vessel = null;
    }
    public void setVessel(Vessel vessel){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        gson = new Gson();
        String vesselString = gson.toJson(vessel);
        spEditor.putString("vessel", vesselString);
        spEditor.commit();
        this.vessel = vessel;
    }

    public Vessel getVessel(){
        if (vessel == null){
            gson = new Gson();
            String storedVesselString = userLocalDatabase.getString("vessel", "oopsDintWork");
            java.lang.reflect.Type type = new TypeToken<Vessel>(){}.getType();
            vessel = gson.fromJson(storedVesselString, type);
        }
        return vessel;
    }

    public boolean getUserLoggedIn () {
        // Går att använda enbart;
        // return userLocalDatabase.getBoolean("Logged In", false);
        if (userLocalDatabase.getBoolean("Logged In", false) == true) {
            return true;
        } else {
            return false;
        }
    }
    public HashMap<String, MessageBrokerQueue> getMessageBrokerMap() {
        if (messageBrokerMap == null){
            gson = new Gson();
            String storedHashMapString = userLocalDatabase.getString("messageMap", "oopsDintWork");
            java.lang.reflect.Type type = new TypeToken<HashMap<String, MessageBrokerQueue>>(){}.getType();
            messageBrokerMap = gson.fromJson(storedHashMapString, type);
        }
        return messageBrokerMap;
    }

    public void addMessageBrokerQueue(String key, MessageBrokerQueue queue)   {
        HashMap<String, MessageBrokerQueue> map = getMessageBrokerMap();
        map.put(key, queue);
        setMessageBrokerMap(map);
    }

    public void setMessageBrokerMap(HashMap<String, MessageBrokerQueue> hashMap){
        gson = new Gson();
        String hashMapString = gson.toJson(hashMap);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("messageMap", hashMapString);
        spEditor.commit();
        messageBrokerMap = hashMap;
    }

    public void setPortCallID(String ID){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        portCallID = ID;
        spEditor.putString("PortCallID", portCallID);
        spEditor.commit();
    }
    public String getPortCallID(){
        if (portCallID == null){
            portCallID = userLocalDatabase.getString("PortCallID","");
        }
        return portCallID;
    }
}
