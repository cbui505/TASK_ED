package com.example.task_ed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.task_ed.MakeTaskFragment.*;

/* fragment that displays the list of tasks, and allows users to add or delete tasks */
public class TaskFragment extends Fragment implements MakeTaskDialogListener{

    //will not display the anyTasks message if we have any tasks (placeholder)
    private static ListView taskList;
    private static ArrayList<String[]> listItems = new ArrayList<>();
    DBTaskManager taskManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate fragment and set pointer to textview defined in xml
        View view = inflater.inflate(R.layout.fragment_task,container,false);

        //set up database
        taskManager = new DBTaskManager(getActivity(), null, null, 1);
        listItems = taskManager.getTasks();

        //display information stored in db when user switches to task tab
        if(!listItems.isEmpty()) {
            ListAdapter adapter = new ListAdapter(getActivity(), listItems);
            //get the listview we will be adding to
            taskList = (ListView) view.findViewById(R.id.taskList);

            //set message if list is empty
            TextView empty = (TextView)view.findViewById(R.id.empty);
            taskList.setEmptyView(empty);

            taskList.setAdapter(adapter);
        }

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

        taskItems task = new taskItems(inputText[0], inputText[1]);
        taskManager.addTask(task);

        //add the user input to the arraylist
        listItems.add(inputText);
        //create listadapter
        ListAdapter adapter = new ListAdapter(getActivity(), listItems);
        //get the listview we will be adding to
        taskList = (ListView)getView().findViewById(R.id.taskList);
        //get message when list is empty
        TextView empty = (TextView)getView().findViewById(R.id.empty);
        //set message if list is empty
        taskList.setEmptyView(empty);
        //use adapter to display list
        taskList.setAdapter(adapter);
    }

}