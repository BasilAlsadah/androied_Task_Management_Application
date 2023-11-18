package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.taskmanagementapplication.databinding.ActivityHomeBinding;

public class Home_Activity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //show all_project page as first page when user log in
        replaceFragment(new All_Projects_Fragment());

        //we want to listen to events and change the frame
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.projects_menu:
                    replaceFragment(new All_Projects_Fragment());
                    break;
                case R.id.tasks_menu:
                    replaceFragment(new All_Tasks_Fragment());
                    break;
                case R.id.settings_menu:
                    replaceFragment(new Account_Settings_Fragment());
                    break;
            }
            return true;
        });



    }

    private void replaceFragment(Fragment newFragment){
        FragmentManager myFragmentManager = getSupportFragmentManager();
        FragmentTransaction myFragmentTransition = myFragmentManager.beginTransaction();
        myFragmentTransition.replace(R.id.frameLayout,newFragment);
        myFragmentTransition.commit();
    }
}