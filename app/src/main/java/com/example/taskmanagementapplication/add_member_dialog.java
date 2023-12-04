package com.example.taskmanagementapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_member_dialog extends AppCompatDialogFragment {

    @NonNull
    DatabaseHelper my_Helper;
    EditText username_editText;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_member,null);

        myBuilder.setView(view).setTitle("Add new member").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        username_editText=view.findViewById(R.id.username_plainText);
                        Project_Detail_Activity activity = (Project_Detail_Activity) getActivity();
                        String get_username=username_editText.getText().toString();
                        //because admin can not add himself
                        String is_admin="no";

                        my_Helper = new DatabaseHelper(getActivity());

                        int project_id=activity.get_projectId();
                        boolean isInserted=my_Helper.insertMember(get_username,project_id,is_admin);
                        if (isInserted) {
                            Toast.makeText(getActivity(), "Member inserted successfully.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to insert Member.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
        return myBuilder.create();
    }
}