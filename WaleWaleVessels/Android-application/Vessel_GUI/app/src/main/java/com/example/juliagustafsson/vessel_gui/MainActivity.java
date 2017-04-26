package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  {

   // private Button logout;
    private UserLocalStorage userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText("Current ETA: " + Send_ETA.newDate + "\n" + Send_ETA.newETA);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //logout = (Button) findViewById(R.id.logout);
        //logout.setOnClickListener(this);

        userLocalStore = new UserLocalStorage(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //If someone is logged in access MainActivity page
        if (authenticate() == true) {
        displayVesselID();
        }
        //If noone is logged in access Login page
        else {
            startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
        }
    }

    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();
    }

    public void displayVesselID() {
        User user = userLocalStore.getLoggedInUser();
        TextView textView = (TextView) findViewById(R.id.loggedIn);
        textView.setText("Active Vessel: " + user.vesselID);
    }

    public void logoutAction(View view) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
    }

    public void viewPCM(View view) {
        Intent intent = new Intent(this, ViewPCM.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        // TODO Fixa källan till texten, dvs här ska ett PCM läsas is till ett textfält

        startActivity(intent);

//        HttpUrlConnectionPortCDM foo = new HttpUrlConnectionPortCDM();
        //String pcmStr = foo.xmlTester();
    }

    public void sendETA(View view) {
        Intent intent = new Intent(this, Send_ETA.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        startActivity(intent);
    }
}
