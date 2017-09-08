package com.example.task_ed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String[]> {

    public ListAdapter(Context context, ArrayList<String[]> input) {
        super(context, R.layout.list_item, input);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        String[] row = getItem(position);
        TextView listItemTask = (TextView)view.findViewById(R.id.listItemTask);
        TextView listItemTime = (TextView)view.findViewById(R.id.listItemTime);

        listItemTask.setText(row[0]);
        listItemTime.setText(row[1]);
        return view;
    }
}
