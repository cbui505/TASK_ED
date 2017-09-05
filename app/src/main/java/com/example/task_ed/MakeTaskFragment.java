package com.example.task_ed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Christopher on 9/5/2017.
 */

public class MakeTaskFragment extends DialogFragment {

    public static MakeTaskFragment newInstance() {
        MakeTaskFragment f = new MakeTaskFragment();
        return f;
    }

    public interface MakeTaskDialogListener {
        void onFinishTaskDialog(String inputText);
    }

    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        MakeTaskDialogListener listener = (MakeTaskDialogListener) getTargetFragment();
        dismiss();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.make_taskpage, null))
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MakeTaskFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
