package com.example.taskmanagementapplication;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class listAdapter extends ArrayAdapter<Pair<String, String>> {

    public listAdapter(Context context, List<Pair<String, String>> projects) {
        super(context, R.layout.custom_project_list_design, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<String, String> project = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_project_list_design, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.text_name);
        TextView descTextView = convertView.findViewById(R.id.text_description);

        // Swap the name and description positions dynamically
        nameTextView.setText(project.first);
        descTextView.setText(project.second);

        return convertView;
    }
}
