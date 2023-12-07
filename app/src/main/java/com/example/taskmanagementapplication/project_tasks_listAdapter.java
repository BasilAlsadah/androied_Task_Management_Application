package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.taskmanagementapplication.DatabaseHelper;
import com.example.taskmanagementapplication.Project_Detail_Activity;
import com.example.taskmanagementapplication.R;
import com.example.taskmanagementapplication.Task;

import java.util.ArrayList;

public class project_tasks_listAdapter extends ArrayAdapter<Task> {
    private int resourceLayout;
    private Context mContext;
    private DatabaseHelper my_helper;
    private Fragment fragment;

    public project_tasks_listAdapter(Context context, int resource, ArrayList<Task> items,
                                     Project_Detail_Activity activity) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.my_helper = new DatabaseHelper(context);
    }

    public project_tasks_listAdapter(Fragment fragment, int resource, ArrayList<Task> items) {
        super(fragment.requireContext(), resource, items);
        this.resourceLayout = resource;
        this.mContext = fragment.requireContext();
        this.my_helper = new DatabaseHelper(mContext);
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View my_view = convertView;
        if (my_view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            my_view = vi.inflate(resourceLayout, null);
        }

        Task tasks_item = getItem(position);

        if (tasks_item != null) {
            TextView ID = my_view.findViewById(R.id.task_id);
            TextView title = my_view.findViewById(R.id.task_title);
            TextView assignedTo = my_view.findViewById(R.id.task_assignTo);
            TextView due_date = my_view.findViewById(R.id.task_due_date);
            TextView priority = my_view.findViewById(R.id.task_priority);
            TextView status = my_view.findViewById(R.id.task_status);

            ID.setText("Task ID: " + tasks_item.getId());
            title.setText("Title: " + tasks_item.getTitle());
            assignedTo.setText("Assigned To: " + tasks_item.getAssigned_to());
            due_date.setText("Due date: " + tasks_item.getDueDate());
            priority.setText("Priority: " + tasks_item.getPriority());
            status.setText("Status: " + tasks_item.getStatus());
        }
        return my_view;
    }
}