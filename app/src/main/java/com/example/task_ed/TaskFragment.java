package com.example.task_ed;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.task_ed.MakeTaskFragment.*;

/* fragment that displays the list of tasks, and allows users to add or delete tasks */
public class TaskFragment extends Fragment implements MakeTaskDialogListener {

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

        if(inputText[0].equals("") || inputText[1].equals(""))
            return;

        taskItems task = new taskItems(inputText[0], inputText[1]);

        taskManager.addTask(task);
        listItems.add(inputText);
        ListAdapter adapter = new ListAdapter(getActivity(), listItems);
        //get the listview we will be adding to
        taskList = (ListView)getView().findViewById(R.id.taskList);
        //get message when list is empty
        TextView empty = (TextView)getView().findViewById(R.id.empty);
        //set message if list is empty

        taskList.setEmptyView(empty);
        //use adapter to display list

        taskList.setAdapter(adapter);
        int count = taskList.getAdapter().getCount();
        makeNotification(inputText[0], inputText[1], count);

    }

    public void makeNotification(String task, String time, int number) {
        //create the notification

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("TASK-ED Reminder")
                        .setContentText(task + " - Complete by " + time);
        mBuilder.setVibrate(new long[] { 1000, 500});
        //set notification led color, light duration, and interval between flash
        mBuilder.setLights(0x0000FF, 300, 2000);

        //set up intent and other necessary data
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(getActivity(), 001, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(activity);

        //get reference to created notification
        Notification notification = mBuilder.build();

        Intent notificationIntent = new Intent(getActivity(), NotificationReceiver.class);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 001);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 001, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //set when to display notification

        int hour, minute = 0;
        //get hours and minutes by splitting up string
        try{
            String[] timeHM = time.split(":");
            hour = Integer.parseInt(timeHM[0].trim());
            minute = Integer.parseInt(timeHM[1].trim());
        }catch(ArrayIndexOutOfBoundsException e){
            hour = Integer.parseInt(time);
            minute = 0;
        }

        //filter input to work on clock
        if(hour >=24){
            int factor = hour/24;
            hour -= factor*24;
        }
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        //set time to be something am/pm
        if(hour <=12 && hour !=0) calSet.set(Calendar.HOUR, hour);
        //set time on 24h scale
        else                      calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.HOUR, 12);
        }

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

    }
}