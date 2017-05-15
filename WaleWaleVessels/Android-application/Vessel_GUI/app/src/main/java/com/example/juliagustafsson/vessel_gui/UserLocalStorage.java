package com.example.juliagustafsson.vessel_gui;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutput;
        try {
            objectOutput = new ObjectOutputStream(arrayOutputStream);
            objectOutput.writeObject(vessel);
            byte[] data = arrayOutputStream.toByteArray();
            objectOutput.close();
            arrayOutputStream.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Base64OutputStream b64 = new Base64OutputStream(out, Base64.DEFAULT);
            b64.write(data);
            b64.close();
            out.close();

            spEditor.putString("vessel", new String(out.toByteArray()));

            spEditor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Vessel getVessel(){
        byte[] bytes = userLocalDatabase.getString("vessel", "{}").getBytes();
        if (bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
        Base64InputStream base64InputStream = new Base64InputStream(byteArray, Base64.DEFAULT);
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(base64InputStream);
            Vessel vessel = (Vessel) in.readObject();
            return vessel;
        }catch (IOException e) {
            return null;
        }catch (ClassNotFoundException e){
            return null;
        }
    }

    public boolean getUserLoggedIn () {
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
