package com.example.juliagustafsson.vessel_gui;

/**
 * Created by juliagustafsson on 2017-04-26.
 */

public class User {

    public String vesselID;
    private UserLocalStorage userLocalStorage;
    private boolean userLoggedIn;

    /**
     * For creating a User for every instance of the app that is running.
     * The point of the User-class is to store information locally in the application
     * during runtime. If the requested information does not exist in the User-class, then
     * the UserLocalStorage is requested for the information.
     */
    public User() {

    }

    public User (String vesselID) {
        this.vesselID = vesselID;
    }

}
