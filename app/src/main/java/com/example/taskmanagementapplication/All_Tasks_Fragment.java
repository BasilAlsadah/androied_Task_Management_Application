package com.example.taskmanagementapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_Tasks_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All_Tasks_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public All_Tasks_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment All_Tasks_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static All_Tasks_Fragment newInstance(String param1, String param2) {
        All_Tasks_Fragment fragment = new All_Tasks_Fragment();
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

    //HERE WE PUT OUR CODE
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        //HERE !
        //find the spinner in the fragment
        Spinner my_spinner= rootView.findViewById(R.id.sort_spinner);
        //create an ArrayAdapter
        ArrayAdapter<CharSequence> my_adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        //This determines the appearance of the drop-down items in the Spinner
        my_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set my_adapter to the spinner
        my_spinner.setAdapter(my_adapter);
        //now adding item listener
        my_spinner.setOnItemSelectedListener(this);


        return rootView;
    }

    //Methods that must implements when using AdapterView.OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}