package com.example.juliagustafsson.vessel_gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by juliagustafsson on 2017-05-20.
 */

class CustomAdapterLSU extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> positions;
    ArrayList<String> times;
    ArrayList<String> timeTypes;
    int resource;

    CustomAdapterLSU(Context context, int resource, ArrayList<String> positions,ArrayList<String> timeTypes,ArrayList<String> times ) {
        super(context, resource, positions);
        this.context = context;
        this.resource = resource;
        this.positions = positions;
        this.times = times;
        this.timeTypes = timeTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lsuInflator = LayoutInflater.from(getContext());
        View row = lsuInflator.inflate(resource, parent, false);

        TextView pos = (TextView) row.findViewById(R.id.position);
        TextView time = (TextView) row.findViewById(R.id.time);
        TextView timeType = (TextView) row.findViewById(R.id.timeType);

        pos.setText(positions.get(position));
        time.setText(times.get(position));
        timeType.setText(timeTypes.get(position));
        return row;
        }
}
