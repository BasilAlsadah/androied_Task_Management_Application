package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class Project_Detail_Activity extends AppCompatActivity implements add_member_dialog.DialogDismissListener,
add_task_dialog.DialogDismissListener{
    int current_project_id;
    member_listAdapter memberListAdapter;
    project_tasks_listAdapter tasksListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        //put the current username above
        TextView username = findViewById(R.id.welcome_username3);
        EditText proj_name = findViewById(R.id.project_name_text);
        EditText proj_desc = findViewById(R.id.description_field);
        Button add_member = findViewById(R.id.Add_member_btn);
        Button add_task_btn = findViewById(R.id.Add_task_btn);
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

        //now i want to print all tasks in the project
        ListView tasks_listView = findViewById(R.id.project_tasks_list);
        //i want to add a Touch Listener to enable nested scroll
        tasks_listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        //now continue to print the tasks on listView
        ArrayList<Task> project_tasks_array=my_helper.project_tasks(current_project_id);
        tasksListAdapter = new project_tasks_listAdapter(this,R.layout.project_tasks_list_adapter,
                project_tasks_array,this);
        tasks_listView.setAdapter(tasksListAdapter);


        //now i want to print all members in the project
        ListView members_listView = findViewById(R.id.projectMembers_list);
        //now adding on touch listener to enable nested scroll
        members_listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        ArrayList<String> members_array = my_helper.member_array(current_project_id);
        memberListAdapter = new member_listAdapter(this,R.layout.member_list_adapter,members_array,this);
        members_listView.setAdapter(memberListAdapter);

        //add task onClick function
        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_addTask_dialog();
            }
        });
        //add member onClick function
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_addMember_Dialog();
            }
        });

    }
    //a method that will open addTask dialog
    public void open_addTask_dialog(){
        add_task_dialog taskDialog= new add_task_dialog();
        taskDialog.show(getSupportFragmentManager(),"Create New Task");
    }
    //a method that will open addMember dialog
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
        updateTaskList();
    }

    // Method to update the member list
    private void updateMemberList() {
        DatabaseHelper my_helper = new DatabaseHelper(this);
        ArrayList<String> updated_members_array = my_helper.member_array(current_project_id);
        memberListAdapter = new member_listAdapter(this,R.layout.member_list_adapter, updated_members_array, this);
        ListView members_listView = findViewById(R.id.projectMembers_list);
        members_listView.setAdapter(memberListAdapter);
    }
    public void updateTaskList(){
        DatabaseHelper my_helper = new DatabaseHelper(this);
        ArrayList<Task> project_tasks_array=my_helper.project_tasks(current_project_id);
        tasksListAdapter = new project_tasks_listAdapter(this,R.layout.project_tasks_list_adapter,
                project_tasks_array,this);
        ListView tasks_listView = findViewById(R.id.project_tasks_list);
        tasks_listView.setAdapter(tasksListAdapter);
    }

}