package com.example.taskmanagementapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class add_task_dialog extends AppCompatDialogFragment {
    @NonNull
    EditText task_id,username_editText,task_title,task_dueDate;
    Spinner priority_spinner,status_spinner;
    DatabaseHelper myHelper;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_task,null);
        //declare all edit texts and spinners
        //task_id=view.findViewById(R.id.task_id);
        username_editText=view.findViewById(R.id.username_editText);
        task_title=view.findViewById(R.id.task_title);
        task_dueDate=view.findViewById(R.id.task_due_date);
        priority_spinner=view.findViewById(R.id.priority_spinner);
        status_spinner=view.findViewById(R.id.status_spinner);
        // Create a list of items for the spinner.
        String[] priority_items = new String[]{"High", "Medium", "Low"};
        String[] status_items = new String[]{"not started","in progress","Done"};
        // Create an adapter to describe how the items are displayed.
        ArrayAdapter<String> priority_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                priority_items);
        //create another adapter for task status
        ArrayAdapter<String> status_adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,status_items);
        //set adapters
        priority_spinner.setAdapter(priority_adapter);
        status_spinner.setAdapter(status_adapter);

        builder.setView(view).setTitle("Create New Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                    Project_Detail_Activity activity = (Project_Detail_Activity) getActivity();
                    int get_projectId = activity.get_projectId();
                    //String get_task_id=task_id.getText().toString();
                    String get_username=username_editText.getText().toString();
                    String get_task_title=task_title.getText().toString();
                    String get_date=task_dueDate.getText().toString();
                    String get_priority=priority_spinner.getSelectedItem().toString();
                    String get_status=status_spinner.getSelectedItem().toString();

                    myHelper = new DatabaseHelper(getActivity());
                    boolean isInserted = myHelper.insertTask(get_projectId,get_username,
                            get_task_title,get_date,get_priority,get_status);
                    if(isInserted){
                        Toast.makeText(getActivity(), "Task inserted successfully.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Adding Task Fail", Toast.LENGTH_LONG).show();
                    }
                    }
                });
        return builder.create();
    }
}