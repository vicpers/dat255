package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import RESTServices.MessageBrokerQueue;
import ServiceEntities.Vessel;



/**
 * A class which stores information of the User logged in. By saving to SharedPreferences and using
 * static methods, the data is easily accessed from all activities and classes. Some objects are
 * exported to JSONs using GSON since SharedPreferences only accepts simple data structures.
 */

public class UserLocalStorage{

    private static final String SP_NAME = "userDetails";
    private static SharedPreferences userLocalDatabase;

    /**
     * Creates a UserLocalStorage
     */
    public UserLocalStorage(){}

    /** Creates a UserLocalStorage
     * @param context Context of the activity launching
     */
    public UserLocalStorage(Context context) {
        if (userLocalDatabase == null)
            userLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Removes all saved data about the user
     */
    public static void clearUserData () {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("messageMap", "");
        spEditor.clear();
        spEditor.apply();
    }

    /**
     * Store the Vessel of the logged in User
     * @param vessel The user Vessel
     */
    public static void setVessel(Vessel vessel){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        Gson gson = new Gson();
        String vesselString = gson.toJson(vessel);
        spEditor.putString("vessel", vesselString);
        spEditor.apply();
    }

    /**
     * @return the stored user Vessel
     */
    public static Vessel getVessel(){
        Gson gson = new Gson();
        String storedVesselString = userLocalDatabase.getString("vessel", null);
        java.lang.reflect.Type type = new TypeToken<Vessel>(){}.getType();
        return gson.fromJson(storedVesselString, type);
    }

    /**
     * A method to receive a HashMap with String as key and MessageBrokerQueue as value.
     * @return a HashMap with all PortCallMessages received since user logged in.
     */
    public static HashMap<String, MessageBrokerQueue> getMessageBrokerMap() {
        Gson gson = new Gson();
        String storedHashMapString = userLocalDatabase.getString("messageMap", null);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, MessageBrokerQueue>>(){}.getType();
        return gson.fromJson(storedHashMapString, type);
    }

    /**
     * @param hashMap The HashMap to be saved containing the User's MessageBrokerQueue's.
     */
    public static void setMessageBrokerMap(HashMap<String, MessageBrokerQueue> hashMap){
        Gson gson = new Gson();
        String hashMapString = gson.toJson(hashMap);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("messageMap", hashMapString);
        spEditor.apply();
    }

    /** Saves the users PortCallID
     * @param portCallID a string with the users PortCallID
     */
    public static void setPortCallID(String portCallID){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("PortCallID", portCallID);
        spEditor.apply();
    }

    /**
     * @return a String with the users PortCallID
     */
    public static String getPortCallID(){
        return userLocalDatabase.getString("PortCallID", null);
    }



}
