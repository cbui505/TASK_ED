package com.example.task_ed;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/* listAdapter responsible for parsing user input into listItems, and adding to listView */
public class ListAdapter extends ArrayAdapter<String[]> {

    public ListAdapter(Context context, ArrayList<String[]> input) {
        super(context, R.layout.list_item, input);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        //get reference to next listitem (listitem is an array that holds task and time
        String[] row = getItem(position);
        //get references to textviews that will be written to
        TextView listItemTask = (TextView)view.findViewById(R.id.listItemTask);
        TextView listItemTime = (TextView)view.findViewById(R.id.listItemTime);

        //set what is displayed for each list item
        listItemTask.setText(row[0]);
        listItemTime.setText(row[1]);

        //get reference to delete button of listitems and set up onClick
        ImageButton deleteButton = (ImageButton)view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //placeholder, will need to delete task from listView and from db
                Toast.makeText(v.getContext(), "Should delete this task",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
