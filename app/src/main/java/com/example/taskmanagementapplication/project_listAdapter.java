package com.example.taskmanagementapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class project_listAdapter extends ArrayAdapter<Project> {
    //a method for search bar
    private ArrayList<Project> project_list;
       public void setFilteredList(ArrayList<Project> filteredList) {
        this.project_list = filteredList;
        notifyDataSetChanged();
    }

    public project_listAdapter(Context context, ArrayList<Project> projects) {
        super(context, R.layout.custom_project_list_design, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Project project = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_project_list_design, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.text_id);
        TextView nameTextView = convertView.findViewById(R.id.text_name);
        TextView descTextView = convertView.findViewById(R.id.text_description);

        // Set the project ID, name, and description
        idTextView.setText(String.valueOf(project.getId()));
        nameTextView.setText(project.getName());
        descTextView.setText(project.getDescription());

        return convertView;
    }
}
