package com.example.taskmanagementapplication;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class project_listAdapter extends ArrayAdapter<Pair<Integer, Pair<String, String>>> {

    public project_listAdapter(Context context, List<Pair<Integer, Pair<String, String>>> projects) {
        super(context, R.layout.custom_project_list_design, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<Integer, Pair<String, String>> project = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_project_list_design, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.text_id);
        TextView nameTextView = convertView.findViewById(R.id.text_name);
        TextView descTextView = convertView.findViewById(R.id.text_description);

        // Set the project ID, name, and description
        idTextView.setText(String.valueOf(project.first));
        nameTextView.setText(project.second.first);
        descTextView.setText(project.second.second);

        return convertView;
    }
}
