package com.example.taskmanagementapplication;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_Projects_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All_Projects_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public All_Projects_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment All_Projects_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static All_Projects_Fragment newInstance(String param1, String param2) {
        All_Projects_Fragment fragment = new All_Projects_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //Here will start our code
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_projects, container, false);
        //find the create project button
        Button create_project_btn = rootView.findViewById(R.id.add_project_btn);
        //put the current username above
        TextView username = rootView.findViewById(R.id.welcome_username);
        Home_Activity activity = (Home_Activity) getActivity();
        String current_user = activity.getUsername();
        username.setText("Welcome "+current_user);

        //find project list
        ListView projects_listview = rootView.findViewById(R.id.projects_list);
        DatabaseHelper myHelper = new DatabaseHelper(getActivity());
        /*
        ArrayList<String> All_projectList = myHelper.getAll_projects();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(rootView.getContext(),
                R.layout.custom_project_list_design,All_projectList);
        projects_listview.setAdapter(myAdapter);

         */
        //create on click listener
        create_project_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //create dialog
                openMydialofg();

            }

        });


        return rootView;
    }
    public void openMydialofg(){
        myDialog mydialog=new myDialog();
        mydialog.show(getActivity().getSupportFragmentManager(), "Create project page");

    }
}