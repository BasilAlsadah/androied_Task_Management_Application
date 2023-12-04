package com.example.taskmanagementapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;

public class myDialog extends AppCompatDialogFragment {

    EditText proj_name , proj_desc;
    DatabaseHelper myHelper;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            myHelper = new DatabaseHelper(getActivity());
        }
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_project,null);

        myBuilder.setView(view).setTitle("Create new Project")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        proj_name = view.findViewById(R.id.project_name_txt);
                        proj_desc = view.findViewById(R.id.project_desc_txt);
                        Home_Activity activity = (Home_Activity) getActivity();


                        String projectName = proj_name.getText().toString();
                        String projectDescription = proj_desc.getText().toString();
                        String proj_admin = activity.getUsername();
                        myHelper = new DatabaseHelper(getActivity());
                        boolean project_table_is_exist = myHelper.isTableExists("projects");
                        boolean member_table_is_exist = myHelper.isTableExists("Members");
                        if (project_table_is_exist && member_table_is_exist) {
                            boolean project_isInserted = myHelper.insertProject(projectName, projectDescription,
                                    proj_admin);

                            if (project_isInserted) {
                                int last_project_in = myHelper.getLastProject_Id();
                                boolean member_isInserted = myHelper.insertMember(proj_admin,
                                        last_project_in,"yes");
                                if(member_isInserted) {
                                    Toast.makeText(getActivity(), "project inserted successfully.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Failed to insert data.", Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            SQLiteDatabase db = myHelper.getWritableDatabase();
                            myHelper.createProjectsTable(db);
                        }
                    }
                });
        return myBuilder.create();


    }
    public interface OnDismissListener {
        void onDialogDismiss();
    }

    private OnDismissListener listener;

    public void setOnDismissListener(OnDismissListener listener) {

        this.listener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null) {
            listener.onDialogDismiss();
        }
    }
}
