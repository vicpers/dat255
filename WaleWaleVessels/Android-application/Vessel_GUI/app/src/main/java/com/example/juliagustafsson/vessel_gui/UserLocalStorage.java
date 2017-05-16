package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private Gson gson;

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(){}

    public UserLocalStorage(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void clearUserData () {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public void setUserLoggedIn (boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("Logged In", loggedIn);
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
        String storedVesselString = userLocalDatabase.getString("vessel", null);
        java.lang.reflect.Type type = new TypeToken<Vessel>(){}.getType();
        return gson.fromJson(storedVesselString, type);
    }

    public void setUser(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        spEditor.putString("user", userString);
        spEditor.commit();
    }

    public User getUser(){
        Gson gson = new Gson();
        String storedUserString = userLocalDatabase.getString("user", null);
        Log.e("storedUserStr", storedUserString);
        //java.lang.reflect.Type type = new TypeToken<User>(){}.getType();
        return gson.fromJson(storedUserString, User.class);
    }

    public boolean getUserLoggedIn () {
        return userLocalDatabase.getBoolean("Logged In", false);
    }

    public HashMap<String, MessageBrokerQueue> getMessageBrokerMap() {
        gson = new Gson();
        String storedHashMapString = userLocalDatabase.getString("messageMap", null);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, MessageBrokerQueue>>(){}.getType();
        return gson.fromJson(storedHashMapString, type);
    }

    public void setMessageBrokerMap(HashMap<String, MessageBrokerQueue> hashMap){
        gson = new Gson();
        String hashMapString = gson.toJson(hashMap);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("messageMap", hashMapString);
        spEditor.commit();
    }

    public void setPortCallID(String portCallID){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("PortCallID", portCallID);
        spEditor.commit();
    }

    public String getPortCallID(){
        return userLocalDatabase.getString("PortCallID", null);
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public static String getSpName() {
        return SP_NAME;
    }

    public SharedPreferences getUserLocalDatabase() {
        return userLocalDatabase;
    }

    public void setUserLocalDatabase(SharedPreferences userLocalDatabase) {
        this.userLocalDatabase = userLocalDatabase;
    }

}
