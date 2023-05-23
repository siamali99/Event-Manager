package com.example.eventmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class CustomEventAdapter extends ArrayAdapter<Event> {

    private final Context context;
    private final ArrayList<Event> values;


    public CustomEventAdapter(@NonNull Context context, @NonNull ArrayList<Event> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.new_event, parent, false);

        TextView eventName = rowView.findViewById(R.id.tveventname);
        TextView eventDateTime = rowView.findViewById(R.id.tvdate);
        TextView eventPlaceName = rowView.findViewById(R.id.tveventplace);

        eventName.setText(values.get(position).name);
        eventDateTime.setText(values.get(position).datetime);
        eventPlaceName.setText(values.get(position).place);



        return rowView;
    }
}
