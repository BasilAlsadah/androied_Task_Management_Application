package com.example.taskmanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class project_tasks_listAdapter extends ArrayAdapter<Task> {
    private int resourceLayout;
    private Context mContext;
    //we put it here so we can set a context to it in the constructor
    private DatabaseHelper my_helper;
    //we will use this to use function called get_projectId()
    private Project_Detail_Activity activity;

    public project_tasks_listAdapter(Context context, int resource, ArrayList<Task> items,
                                     Project_Detail_Activity activity) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.my_helper = new DatabaseHelper(context);
        this.activity = activity;
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
            TextView ID = (TextView) my_view.findViewById(R.id.task_id);
            TextView title = (TextView) my_view.findViewById(R.id.task_title);
            TextView assignedTo = (TextView) my_view.findViewById(R.id.task_assignTo);
            TextView due_date = (TextView) my_view.findViewById(R.id.task_due_date);
            TextView priority = (TextView) my_view.findViewById(R.id.task_priority);
            TextView status = (TextView) my_view.findViewById(R.id.task_status);

            //set texts
            ID.setText("Task ID : "+tasks_item.getId());
            title.setText("Title : " + tasks_item.getTitle());
            assignedTo.setText("Assigned To :"+tasks_item.getAssigned_to());
            due_date.setText("Due date : " + tasks_item.getDueDate());
            priority.setText("Priority :" + tasks_item.getPriority());
            status.setText("Status : " + tasks_item.getStatus());
        }
        return my_view;
    }
}