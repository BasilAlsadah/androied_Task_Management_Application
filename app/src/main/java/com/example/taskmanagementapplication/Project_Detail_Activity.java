package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class Project_Detail_Activity extends AppCompatActivity implements add_member_dialog.DialogDismissListener{
    int current_project_id;
    member_listAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        //put the current username above
        TextView username = findViewById(R.id.welcome_username3);
        EditText proj_name = findViewById(R.id.project_name_text);
        EditText proj_desc = findViewById(R.id.description_field);
        Button add_member = findViewById(R.id.Add_member_btn);
        current_project_id=getIntent().getIntExtra("project_id",0);
        String current_user=getIntent().getStringExtra("current_user");
        String project_clicked=getIntent().getStringExtra("project_clicked");
        //set username above
        username.setText("Welcome "+current_user);
        //set project name
        proj_name.setText(project_clicked);
        //now i want to get all project information
        DatabaseHelper my_helper = new DatabaseHelper(this);
        String get_description = my_helper.getDescribtion(project_clicked);
        proj_desc.setText(get_description);

        //now i want to print all members in the project
        ListView members_listView = findViewById(R.id.projectMembers_list);
        ArrayList<String> members_array = my_helper.member_array(current_project_id);
        adapter = new member_listAdapter(this,R.layout.member_list_adapter,members_array,this);
        members_listView.setAdapter(adapter);


        //add member function
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_addMember_Dialog();
            }
        });

    }

    public void open_addMember_Dialog(){
    add_member_dialog memberDialog = new add_member_dialog();
    memberDialog.show(getSupportFragmentManager(),"Add new member");
    }
    //a method to get current project id to help adding new members
    public int get_projectId(){

        return current_project_id;
    }
    // Implement the DialogDismissListener interface
    @Override
    public void onDialogDismissed() {
        // Dialog has been dismissed, update the member list
        updateMemberList();
    }

    // Method to update the member list
    private void updateMemberList() {
        DatabaseHelper my_helper = new DatabaseHelper(this);
        ArrayList<String> updated_members_array = my_helper.member_array(current_project_id);
        adapter = new member_listAdapter(this,R.layout.member_list_adapter, updated_members_array, this);
        ListView members_listView = findViewById(R.id.projectMembers_list);
        members_listView.setAdapter(adapter);
    }

}