package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ServiceEntities.Vessel;



/**
 * Created by juliagustafsson on 2017-04-26.
 */

public class UserLocalStorage {


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
         if(userLocalDatabase.getBoolean("Logged In", false) == true) {
             return true;
         }
         else {
         return false;
         }
    }
}
