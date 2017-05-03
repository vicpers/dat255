package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.ActionBar;
import android.widget.TextView;


public class Vessel_Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText vesselID;
    UserLocalStorage userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vessel__login);

        // Import custom font
        TextView myTextView = (TextView) findViewById(R.id.PortCDM);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/LEIXO.ttf");
        myTextView.setTypeface(typeface);

        Button myTextView2 = (Button) findViewById(R.id.Access_PortCDM);
        Typeface typeface2=Typeface.createFromAsset(getAssets(), "fonts/Quicksand_Bold.otf");
        myTextView2.setTypeface(typeface2);

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
            if (vID.equals("WaleWale")) {
                logUserIn(user);
            }
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
