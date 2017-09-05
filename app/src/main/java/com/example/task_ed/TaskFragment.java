package com.example.task_ed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.task_ed.MakeTaskFragment.MakeTaskDialogListener;

public class TaskFragment extends Fragment implements MakeTaskDialogListener{

    //will not display the anyTasks message if we have any tasks
    private static TextView anyTasks;
    private static int taskNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate fragment and set pointer to textview defined in xml
        View view = inflater.inflate(R.layout.fragment_task,container,false);
        anyTasks = (TextView)view.findViewById(R.id.anyTasks);

        Button makeTask = (Button)view.findViewById(R.id.makeTask);
        makeTask.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        makeNewTask(v);
                    }
                }
        );


        return view;
    }

    public void makeNewTask(View view){
        //increment the number of tasks (do later when we implement the delete task method)
        //taskNumber++;
        MakeTaskFragment task = MakeTaskFragment.newInstance();
        task.show(getActivity().getFragmentManager(), "create_task");
}

    @Override
    public void onFinishTaskDialog(String inputText) {
        return;
    }
}