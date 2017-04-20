package com.example.juliagustafsson.vessel_gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Send_ETA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_eta);
    }

    public void sendNewETA(View view) {
        //TODO Kolla inputs så att de innehåller giltiga värden (Datum och tid)
        //TODO kod för att uppdatera senast skickat ETA
        //update_str = Context.getResources().getString (R.string.update_identifier)
        //Update the label using ((TextView)findViewById (R.id.textview)).setText (updated_str)
    }
}
