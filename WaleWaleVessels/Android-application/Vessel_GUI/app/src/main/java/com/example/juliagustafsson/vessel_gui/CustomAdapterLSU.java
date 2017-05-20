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
    String[] serviceTimeSequences;
    String[] positions;
    String[]times;
    String[] dates;

    CustomAdapterLSU(Context context, ArrayList<ArrayList<String>> lists ) {
        super(context, R.layout.custom_listview_row_lsu, lists);
        this.context = context;
        this.serviceTimeSequences = lists[0];
        this.positions = lists[1]
        this.times = lists[2];
        this.dates = lists[3];

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lsuInflator = LayoutInflater.from(getContext());
        View row = lsuInflator.inflate(R.layout.custom_listview_row_lsu, parent, false);

        TextView serviceTimeSequence = (TextView) row.findViewById(R.id.serviceTimeSequence);
        TextView position = (TextView) row.findViewById(R.id.position);
        TextView time = (TextView) row.findViewById(R.id.time);
        TextView date = (TextView) row.findViewById(R.id.date);

        serviceTimeSequence.setText(serviceTimeSequences[position]);
        position.setText(positions[position]);
        time.setText(times[position]);
        date.setText(dates[position]);
        return row;
        }
}
