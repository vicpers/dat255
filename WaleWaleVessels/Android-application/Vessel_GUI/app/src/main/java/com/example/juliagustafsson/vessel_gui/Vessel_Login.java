package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.NoSuchElementException;


/**
 * Activity for the user to log in to the application. By using a VesselIMO, this application is
 * able to verify that the Vessel is connected to PortCDM. All other data about the Vessel is then
 * automatically retrieved from the PortCDM server.
 */
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Access_PortCDM:
                EditText vID =  (EditText) findViewById(R.id.vessel_ID);
                String vesselIMO = "urn:mrn:stm:vessel:IMO:" + vID.getText().toString();
                UserLocalStorage.clearUserData();
                User user;
                try{
                    new User(this, vesselIMO);
                    startActivity(new Intent(this, MainActivity.class ));
                } catch(NoSuchElementException | IllegalArgumentException e){
                    Log.e("LoginExc", e.toString());
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect VesselIMO, try again! ", duration);
                    toast.show();
                }
                break;

            default:
                break;
        }
    }

}
