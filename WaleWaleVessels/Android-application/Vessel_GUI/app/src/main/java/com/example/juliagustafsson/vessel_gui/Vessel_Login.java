package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Vessel_Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText vesselID;
    UserLocalStorage userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vessel__login);

        vesselID = (EditText) findViewById(R.id.vessel_ID);
        login = (Button) findViewById(R.id.Access_PortCDM);

        login.setOnClickListener(this);

        userLocalStore = new UserLocalStorage(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Access_PortCDM:
                EditText vID =  (EditText) findViewById(R.id.vessel_ID);
                String vesselID = vID.getText().toString();
                User user = new User(vesselID);
                authenticate(user);
                break;
        }
    }

    public void authenticate(User user) {
        // TODO Lägg till riktig kod mot backend för att kolla om användaren finns i vår lista med användare
        // Provisorisk autentisering av användare för att testa funktionaliteten
        String vID = user.vesselID;
        //for (String vesselID : vesselIDStorage) {
            if (vID.equals("WaleWale")) {
                logUserIn(user);
            }
       // }
        if (userLocalStore.getUserLoggedIn() != true){
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Vessel_Login.this);
        dialogBuilder.setMessage("Incorrect Vessel ID, try again!");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn (User returnedUser) {
        startActivity(new Intent(this, MainActivity.class));
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

    }
}