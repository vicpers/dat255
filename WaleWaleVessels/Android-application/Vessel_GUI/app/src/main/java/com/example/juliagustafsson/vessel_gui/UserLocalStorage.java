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

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }


    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("Vessel ID", user.vesselID);
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String vID = userLocalDatabase.getString("Vessel ID","");
        User storedUser = new User(vID);
            return storedUser;
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
    }
    public void setVessel(Vessel vessel){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        gson = new Gson();
        String vesselString = gson.toJson(vessel);
        spEditor.putString("vessel", vesselString);
        spEditor.commit();
    }

    public Vessel getVessel(){
        gson = new Gson();
        String storedVesselString = userLocalDatabase.getString("vessel", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<Vessel>(){}.getType();
        Vessel vessel = gson.fromJson(storedVesselString, type);
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
        gson = new Gson();
        String storedHashMapString = userLocalDatabase.getString("messageMap", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, MessageBrokerQueue>>(){}.getType();
        HashMap<String, MessageBrokerQueue> messageBrokerMap = gson.fromJson(storedHashMapString, type);
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
    }

    public void setPortCallID(String ID){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("PortCallID", ID);
        spEditor.commit();
    }
    public String getPortCallID(){
        String portCallID = userLocalDatabase.getString("PortCallID","");
        return portCallID;
    }
}
