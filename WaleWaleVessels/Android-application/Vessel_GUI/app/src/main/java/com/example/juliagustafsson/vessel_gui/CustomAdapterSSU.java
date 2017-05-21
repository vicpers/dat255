package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by juliagustafsson on 2017-05-20.
 * Class for making ServiceState-objects presented in a view
 */

public class CustomAdapterSSU extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> positions;
    private ArrayList<String> times;
    private ArrayList<String> dates;
    private ArrayList<String> timeTypes;
    private ArrayList<String> timeSeq;
    private Drawable image;
    private int resource;

    CustomAdapterSSU(Context context, int resource,  ArrayList<String> positions, ArrayList<String> timeTypes, ArrayList<String> times, ArrayList<String> dates, ArrayList<String> timeSeq, Drawable image) {
        super(context, resource, positions);
        this.context = context;
        this.resource = resource;
        this.positions = positions;
        this.times = times;
        this.dates = dates;
        this.timeTypes = timeTypes;
        this.timeSeq = timeSeq;
        this.image = image;
        Log.e("Constructor", "Constructor SSU working");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lsuInflator = LayoutInflater.from(getContext());
        View row = lsuInflator.inflate(resource, parent, false);
        //Log.e("GetView", "GetView called");


        TextView pos = (TextView) row.findViewById(R.id.position);
        TextView time = (TextView) row.findViewById(R.id.time);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView timeType = (TextView) row.findViewById(R.id.timeType);
        TextView timeSequence = (TextView) row.findViewById(R.id.timeSequence);
        ImageView iconImage = (ImageView) row.findViewById(R.id.imageView);

        pos.setText(positions.get(position));
        time.setText(times.get(position));
        date.setText(dates.get(position));
        timeType.setText(timeTypes.get(position));
        timeSequence.setText(timeSeq.get(position));
        iconImage.setImageDrawable(image);
        return row;
    }
}
