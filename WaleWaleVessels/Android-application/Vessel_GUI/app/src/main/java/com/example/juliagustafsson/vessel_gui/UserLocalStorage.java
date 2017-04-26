package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.content.SharedPreferences;

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

    public boolean getUserLoggedIn () {
         if(userLocalDatabase.getBoolean("Logged In", false) == true) {
             return true;
         }
         else {
         return false;
         }
    }
}
