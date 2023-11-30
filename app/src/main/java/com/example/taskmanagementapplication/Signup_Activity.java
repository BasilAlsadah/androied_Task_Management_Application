package com.example.taskmanagementapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class Signup_Activity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.usernameEditText);
                EditText passwordEditText = findViewById(R.id.passwordEditText);
                EditText reenterPasswordEditText = findViewById(R.id.reenterPasswordEditText);

                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String reenterPassword = reenterPasswordEditText.getText().toString().trim();
                // Check if any of the fields are empty
                if (username.isEmpty() || password.isEmpty() || reenterPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (!password.equals(reenterPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the username already exists in the database
                if (databaseHelper.usernameExists(username)) {
                    Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert the username and password into the database
                databaseHelper.insertUser(username, password);
                // Display success message
                Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();

                // Finish the current activity, which will automatically navigate back to the previous activity (Login activity)
                finish();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity to navigate back to the previous activity (Login activity)
                finish();
            }
        });
}
}
