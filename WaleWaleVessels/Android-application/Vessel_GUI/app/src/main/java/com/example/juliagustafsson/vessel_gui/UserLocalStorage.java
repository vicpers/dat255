package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.Vessel;



/**
 * Created by juliagustafsson on 2017-04-26.
 */

public class UserLocalStorage implements Serializable{

    public static final String SP_NAME = "userDetails";
    static SharedPreferences userLocalDatabase;

    public UserLocalStorage(){}

    public UserLocalStorage(Context context) {
        if (userLocalDatabase == null)
            userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public static void clearUserData () {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public static void setUserLoggedIn (boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("Logged In", loggedIn);
        spEditor.commit();
    }

    public static void setVessel(Vessel vessel){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        Gson gson = new Gson();
        String vesselString = gson.toJson(vessel);
        spEditor.putString("vessel", vesselString);
        spEditor.commit();
    }

    public static Vessel getVessel(){
        Gson gson = new Gson();
        String storedVesselString = userLocalDatabase.getString("vessel", null);
        java.lang.reflect.Type type = new TypeToken<Vessel>(){}.getType();
        return gson.fromJson(storedVesselString, type);
    }

    public static boolean getUserLoggedIn () {
        return userLocalDatabase.getBoolean("Logged In", false);
    }

    public static HashMap<String, MessageBrokerQueue> getMessageBrokerMap() {
        Gson gson = new Gson();
        String storedHashMapString = userLocalDatabase.getString("messageMap", null);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, MessageBrokerQueue>>(){}.getType();
        return gson.fromJson(storedHashMapString, type);
    }

    public static void setMessageBrokerMap(HashMap<String, MessageBrokerQueue> hashMap){
        Gson gson = new Gson();
        String hashMapString = gson.toJson(hashMap);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("messageMap", hashMapString);
        spEditor.commit();
    }

    public static void setPortCallID(String portCallID){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("PortCallID", portCallID);
        spEditor.commit();
    }

    public static String getPortCallID(){
        return userLocalDatabase.getString("PortCallID", null);
    }



}
