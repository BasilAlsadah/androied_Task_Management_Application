package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Project_Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        //put the current username above
        TextView username = findViewById(R.id.welcome_username3);
        EditText proj_name = findViewById(R.id.project_name_text);
        EditText proj_desc = findViewById(R.id.description_field);
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

    }
}