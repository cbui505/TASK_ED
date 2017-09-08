package com.example.task_ed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MakeTaskFragment extends DialogFragment {

    //for creating new instances of this fragment
    public static MakeTaskFragment newInstance() {
        MakeTaskFragment frag = new MakeTaskFragment();
        return frag;
    }

    //add interface to send data back to TaskFragment
    public interface MakeTaskDialogListener {
        void onFinishTaskDialog(String[] inputText);
    }

    //will use later to send user input
    private MakeTaskDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.make_taskpage, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                // Add action buttons

                //user selects the create button
                .setPositiveButton(R.string.dialog_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // get the user input text
                        EditText getTaskName = (EditText)dialogView.findViewById(R.id.getTaskName);
                        EditText getCompletionTime = (EditText)dialogView.findViewById(R.id.getCompletionTime);
                        //do nothing if they didnt fill in all fields
                        if(getTaskName==null || getCompletionTime==null){
                                Log.d("FINDME","null"); //debug
                        }else{
                            //otherwise, cast convert to String
                            String task = getTaskName.getText().toString();
                            String time = getCompletionTime.getText().toString();
                            //ignore incomplete entries
                            if(task.equals("") || time.equals("")) return;
                            Log.d("FINDME", task + " and " + time);  //debug
                            //store both inputs in an array and send array back to TaskFragment
                            String[] data = new String[2];
                            data[0]=task;
                            data[1]=time;
                            listener = (MakeTaskDialogListener) getTargetFragment();
                            listener.onFinishTaskDialog(data);
                        }

                    }
                })
                //if user hits cancel, close dialog and return
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MakeTaskFragment.this.getDialog().cancel();
                    }
                });
        //returns the entire dialog to display when user initially opts to create new task
        return builder.create();
    }

}
