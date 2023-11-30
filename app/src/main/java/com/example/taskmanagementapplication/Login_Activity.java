package com.example.taskmanagementapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.usernameEditText);
                EditText passwordEditText = findViewById(R.id.passwordEditText);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if the username and password match in the database
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login_Activity.this,"Please fill all fields",Toast.LENGTH_LONG).show();
                }
                if (databaseHelper.checkCredentials(username, password)) {
                    // Display success message or navigate to the next screen
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    // Add code to navigate to the next screen or perform any desired action
                    Intent my_intent= new Intent(Login_Activity.this,Home_Activity.class);
                    my_intent.putExtra("Log_in_username",username);
                    startActivity(my_intent);
                } else {
                    // Display error message for invalid credentials
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the sign-up activity
                Intent intent = new Intent(Login_Activity.this, Signup_Activity.class);
                startActivity(intent);
            }
        });
    }
}