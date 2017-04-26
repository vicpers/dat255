package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText("Current ETA: " + Send_ETA.newDate + "\n" + Send_ETA.newETA);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
