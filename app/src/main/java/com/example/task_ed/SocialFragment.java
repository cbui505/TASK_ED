package com.example.task_ed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class SocialFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //set view as xml file
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        //use to store data as key-value pair in file within user device
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //get unique id, return -1 if nonexistant
        int check = shared.getInt("uniqueID", -1);
        if(check==-1){
            //if no unique id exists, generate one
            Random r = new Random();
            check = r.nextInt(9999999)+1;
            //store id inside key value pair
            SharedPreferences.Editor editor = shared.edit();
            editor.putInt("uniqueID", check);
            //apply changes to file
            editor.apply();
        }
        //update what is shown to the user on the screen
        TextView uniqueID = (TextView)view.findViewById(R.id.uniqueID);
        uniqueID.setText(String.valueOf(check));
        return view;
    }

}
