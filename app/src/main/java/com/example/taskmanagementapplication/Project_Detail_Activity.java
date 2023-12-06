package com.example.taskmanagementapplication;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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

        //now i want to add an item on click listener to tasks listview
        tasks_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task clickedTask = (Task) tasksListAdapter.getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(Project_Detail_Activity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_update_task, null);
                builder.setView(dialogView);

                // Get references to the input fields or UI elements inside the dialog
                EditText usernameEditText = dialogView.findViewById(R.id.username_editText);
                EditText titleEditText = dialogView.findViewById(R.id.task_title);
                EditText due_dateEditText = dialogView.findViewById(R.id.task_due_date);
                Spinner priority_spinner=dialogView.findViewById(R.id.priority_spinner);
                Spinner status_spinner=dialogView.findViewById(R.id.status_spinner);
                // now declare spinners
                // Create a list of items for the spinner.
                String[] priority_items = new String[]{"High", "Medium", "Low"};
                String[] status_items = new String[]{"not started","in progress","Done"};
                // Create an adapter to describe how the items are displayed.
                ArrayAdapter<String> priority_adapter = new ArrayAdapter<>(Project_Detail_Activity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        priority_items);
                //create another adapter for task status
                ArrayAdapter<String> status_adapter = new ArrayAdapter<>(Project_Detail_Activity.this,
                        android.R.layout.simple_spinner_dropdown_item,status_items);
                //set adapters
                priority_spinner.setAdapter(priority_adapter);
                status_spinner.setAdapter(status_adapter);

                // Set initial values for the input fields as it was set before
                usernameEditText.setText(clickedTask.getAssigned_to());
                titleEditText.setText(clickedTask.getTitle());
                due_dateEditText.setText(clickedTask.getDueDate());

                // Set spinner selections based on set values
                String priority = clickedTask.getPriority();
                String status = clickedTask.getStatus();

                //check index of the selection
                int priorityIndex = Arrays.asList(priority_items).indexOf(priority);
                if (priorityIndex != -1) {
                    priority_spinner.setSelection(priorityIndex);
                }

                int statusIndex = Arrays.asList(status_items).indexOf(status);
                if (statusIndex != -1) {
                    status_spinner.setSelection(statusIndex);
                }

                // Set listeners for buttons or other UI elements inside the dialog
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve the updated values from the input fields
                        String get_taskId=clickedTask.getId();
                        int task_id_int=Integer.parseInt(get_taskId);
                        String newUser = usernameEditText.getText().toString();
                        String newTitle = titleEditText.getText().toString();
                        String newDate = due_dateEditText.getText().toString();
                        String new_priority=priority_spinner.getSelectedItem().toString();
                        String new_status=status_spinner.getSelectedItem().toString();


                        // Update the clickedTask object with the updated values
                        clickedTask.setAssigned_to(newUser);
                        clickedTask.setTitle(newTitle);
                        clickedTask.setDueDate(newDate);
                        clickedTask.setPriority(new_priority);
                        clickedTask.setStatus(new_status);
                        boolean updateTask = my_helper.updateTask(task_id_int,clickedTask.getTitle(),
                                clickedTask.getAssigned_to(),clickedTask.getDueDate(),
                                clickedTask.getPriority(),clickedTask.getStatus());
                        if(updateTask){
                            Toast.makeText(Project_Detail_Activity.this,
                                    "Task Updated successfully.",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Project_Detail_Activity.this
                                    , "Task Updated Fail!.",Toast.LENGTH_LONG).show();
                        }

                        // Notify the adapter that the data has changed
                        tasksListAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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