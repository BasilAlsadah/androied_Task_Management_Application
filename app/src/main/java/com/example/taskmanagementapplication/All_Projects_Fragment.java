package com.example.taskmanagementapplication;

import static android.content.Intent.getIntent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_Projects_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All_Projects_Fragment extends Fragment implements myDialog.OnDismissListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> own=new ArrayList<String>();

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
    List<Pair<Integer, Pair<String, String>>> projectsList;
    DatabaseHelper myHelper = new DatabaseHelper(getActivity());
    listAdapter adapter;
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
        DatabaseHelper my_helper = new DatabaseHelper(getActivity());

        own.add("item1");own.add("bro");own.add("Sekiro");
        //find project list
        /*
        ListView projects_listview = rootView.findViewById(R.id.projects_list);
        DatabaseHelper myHelper = new DatabaseHelper(getActivity());
        ArrayList<String> All_projectList = myHelper.getAll_projects();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_expandable_list_item_2,All_projectList);
        projects_listview.setAdapter(myAdapter);


         */
        //new VERSION
        boolean is_exist = my_helper.isTableExists("projects");
        if(is_exist) {
            ListView projects_listview = rootView.findViewById(R.id.projects_list);
            DatabaseHelper myHelper = new DatabaseHelper(getActivity());
            List<Pair<Integer, Pair<String, String>>> projectsList = myHelper.getAllProjects(current_user);
            adapter = new listAdapter(getActivity(), projectsList);
            projects_listview.setAdapter(adapter);

            //now i want to create an item click listener
            projects_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the ID and name of the clicked project
                    Pair<Integer, Pair<String, String>> clickedProject = projectsList.get(position);
                    int projectId = clickedProject.first;
                    String projectName = clickedProject.second.first;
                    //create intent
                    Intent my_intent = new Intent(getActivity(), Project_Detail_Activity.class);
                    //put extra
                    my_intent.putExtra("current_user", current_user);
                    my_intent.putExtra("project_id", projectId);
                    my_intent.putExtra("project_clicked", projectName);
                    //Start Activity
                    startActivity(my_intent);
                }
            });
        }else{

        }
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
    @Override
    public void onDialogDismiss() {
            Home_Activity activity = (Home_Activity) getActivity();
            String current_user = activity.getUsername();
            //When the dialog dismissed we will update the listView
            DatabaseHelper myHelper = new DatabaseHelper(getActivity());
            //get all projects
            List<Pair<Integer, Pair<String, String>>> updatedProjectsList = myHelper.getAllProjects(current_user);

            // Update the adapter with the new data
            adapter.clear();
            adapter.addAll(updatedProjectsList);

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();

    }

    public void openMydialofg(){
        myDialog mydialog=new myDialog();
        mydialog.setOnDismissListener((myDialog.OnDismissListener) this);
        mydialog.show(getActivity().getSupportFragmentManager(), "Create project page");
    }

}