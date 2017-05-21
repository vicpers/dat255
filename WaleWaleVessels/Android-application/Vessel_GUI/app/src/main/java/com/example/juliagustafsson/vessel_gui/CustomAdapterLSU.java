package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by juliagustafsson on 2017-05-20.
 * Class for making LocationState-objects presented in a view.
 */

class CustomAdapterLSU extends ArrayAdapter<String> {

    private ArrayList<String> positions;
    private ArrayList<String> times;
    private ArrayList<String> dates;
    private ArrayList<String> timeTypes;
    private Drawable image;

    int resource;

    CustomAdapterLSU(Context context, int resource, ArrayList<String> positions, ArrayList<String> timeTypes, ArrayList<String> times, ArrayList<String> dates, Drawable image) {
        super(context, resource, positions);
        this.resource = resource;
        this.positions = positions;
        this.times = times;
        this.dates = dates;
        this.timeTypes = timeTypes;
        this.image = image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lsuInflator = LayoutInflater.from(getContext());
        View row = lsuInflator.inflate(resource, parent, false);

        TextView pos = (TextView) row.findViewById(R.id.position);
        TextView time = (TextView) row.findViewById(R.id.time);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView timeType = (TextView) row.findViewById(R.id.timeType);
        ImageView iconImage = (ImageView) row.findViewById(R.id.imageView);

        pos.setText(positions.get(position));
        time.setText(times.get(position));
        date.setText(dates.get(position));
        timeType.setText(timeTypes.get(position));
        iconImage.setImageDrawable(image);
        return row;
        }
}
