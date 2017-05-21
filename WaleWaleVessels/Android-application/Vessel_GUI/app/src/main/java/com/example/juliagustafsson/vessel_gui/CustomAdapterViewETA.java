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
 */

public class CustomAdapterViewETA extends ArrayAdapter<String> {

    private ArrayList<String> locFrom;
    private ArrayList<String> locTo;
    private ArrayList<String> times;
    private ArrayList<String> dates;
    private ArrayList<String> timeSeq;
    private ArrayList<String> posAt;
    private ArrayList<String> serviceObj;
    private Drawable image;
    private int resource;

    CustomAdapterViewETA(Context context, int resource, ArrayList<String> serviceObj, ArrayList<String> posAt, ArrayList<String> locFrom, ArrayList<String> locTo, ArrayList<String> times, ArrayList<String> dates, ArrayList<String> timeSeq, Drawable image) {
        super(context, resource, locFrom);

        this.resource = resource;
        this.locFrom = locFrom;
        this.locTo = locTo;
        this.times = times;
        this.dates = dates;
        this.timeSeq = timeSeq;
        this.posAt = posAt;
        this.serviceObj = serviceObj;
        this.image = image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Tests if the PCM is a LocationState
        if (serviceObj.get(position).equals("")) {
            LayoutInflater lsuInflator = LayoutInflater.from(getContext());
            View rowLSU = lsuInflator.inflate(R.layout.custom_listview_row_lsu, parent, false);

            TextView pos = (TextView) rowLSU.findViewById(R.id.position);
            TextView time = (TextView) rowLSU.findViewById(R.id.time);
            TextView date = (TextView) rowLSU.findViewById(R.id.date);
            TextView serviceObjects = (TextView) rowLSU.findViewById(R.id.timeType);
            ImageView iconImage = (ImageView) rowLSU.findViewById(R.id.imageView);
            pos.setText(posAt.get(position));
            time.setText(times.get(position));
            date.setText(dates.get(position));
            serviceObjects.setText("Vessel");
            iconImage.setImageDrawable(image);
            return rowLSU;
        }
        // Tests if the PCM is a BeetweenServiceState
        else if (posAt.get(position).equals("")) {
            LayoutInflater lsuInflator = LayoutInflater.from(getContext());
            View rowBSSU = lsuInflator.inflate(R.layout.custom_listview_row_bssu, parent, false);

            TextView locationFrom = (TextView) rowBSSU.findViewById(R.id.locationFrom);
            TextView locationTo = (TextView) rowBSSU.findViewById(R.id.locationTo);
            TextView time = (TextView) rowBSSU.findViewById(R.id.time);
            TextView date = (TextView) rowBSSU.findViewById(R.id.date);
            TextView serviceObjects = (TextView) rowBSSU.findViewById(R.id.timeType);
            TextView timeSequence = (TextView) rowBSSU.findViewById(R.id.timeSequence);
            ImageView iconImage = (ImageView) rowBSSU.findViewById(R.id.imageView);

            locationFrom.setText(locFrom.get(position));
            locationTo.setText(locTo.get(position));
            time.setText(times.get(position));
            date.setText(dates.get(position));
            serviceObjects.setText(serviceObj.get(position));
            timeSequence.setText(timeSeq.get(position));
            iconImage.setImageDrawable(image);
            return rowBSSU;
        }
        // Else it is a regular Service State
        else {
            LayoutInflater lsuInflator = LayoutInflater.from(getContext());
            View rowSSU = lsuInflator.inflate(R.layout.custom_listview_row_ssu, parent, false);

            TextView pos = (TextView) rowSSU.findViewById(R.id.position);
            TextView time = (TextView) rowSSU.findViewById(R.id.time);
            TextView date = (TextView) rowSSU.findViewById(R.id.date);
            TextView serviceObject = (TextView) rowSSU.findViewById(R.id.timeType);
            TextView timeSequence = (TextView) rowSSU.findViewById(R.id.timeSequence);
            ImageView iconImage = (ImageView) rowSSU.findViewById(R.id.imageView);

            pos.setText(posAt.get(position));
            time.setText(times.get(position));
            date.setText(dates.get(position));
            serviceObject.setText(serviceObj.get(position));
            timeSequence.setText(timeSeq.get(position));
            iconImage.setImageDrawable(image);
            return rowSSU;
        }

    }
}
