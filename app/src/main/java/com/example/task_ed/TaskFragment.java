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

import static com.example.task_ed.MakeTaskFragment.*;


public class TaskFragment extends Fragment implements MakeTaskDialogListener{

    //will not display the anyTasks message if we have any tasks (placeholder)
    private static TextView anyTasks;
    private static int taskNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate fragment and set pointer to textview defined in xml
        View view = inflater.inflate(R.layout.fragment_task,container,false);
        anyTasks = (TextView)view.findViewById(R.id.anyTasks);

        //get reference to button that allows user to create a new task
        Button makeTask = (Button)view.findViewById(R.id.makeTask);
        //when clicked, call function to display dialog fragment
        makeTask.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        makeNewTask(v);
                    }
                }
        );


        return view;
    }

    /* setup dialogfragment view to get user input */
    public void makeNewTask(View view){
        //increment the number of tasks (do later when we implement the delete task method)

        //create instance of dialog fragment
        MakeTaskFragment task = newInstance();
        //this allows us to get target fragment for listener back in MakeTaskFragment
        task.setTargetFragment(this,0);
        //display the dialogfragment
        task.show(getActivity().getSupportFragmentManager(), getString(R.string.taskFrag_id));
    }

    @Override
    /* Take in user input from MakeTaskFragment. */
    public void onFinishTaskDialog(String[] inputText) {
        //will need to implement means to store the task next
        if(inputText[0].equals("") || inputText[1].equals("")) return;
        anyTasks.setText(inputText[0] + " - do by " + inputText[1]);
    }
}