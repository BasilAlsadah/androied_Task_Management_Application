package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Project_Detail_Activity extends AppCompatActivity {
    int current_project_id;
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
}