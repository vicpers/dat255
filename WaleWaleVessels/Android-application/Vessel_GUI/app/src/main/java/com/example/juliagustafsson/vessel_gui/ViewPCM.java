package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewPCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);

        // TODO Läs in rätt Port Call Message, koden nedan bör modifieras
        // Get the Intent that started this activity and extract the string
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("Hej Max");
    }
}
