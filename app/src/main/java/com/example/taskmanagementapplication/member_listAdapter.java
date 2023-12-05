package com.example.taskmanagementapplication;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class member_listAdapter extends ArrayAdapter<String> {
    private int resourceLayout;
    private Context mContext;
    //we put it here so we can set a context to it in the constructor
    private DatabaseHelper my_helper;
    //we will use this to use function called get_projectId()
    private Project_Detail_Activity activity;
    public member_listAdapter(Context context, int resource, ArrayList<String> items,Project_Detail_Activity activity) {
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

        String item = getItem(position);

        if (item != null) {
            TextView member_name = (TextView) my_view.findViewById(R.id.member_name);
            Button btn = (Button) my_view.findViewById(R.id.delete_button);

            if (member_name != null) {
                member_name.setText(item);
            }
            //remove button onClick function
            if (btn != null) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get the project id
                        int project_id=activity.get_projectId();
                        String name_of_member=member_name.getText().toString();
                        Boolean isRemoved = my_helper.removeMember(project_id,name_of_member);
                        if(isRemoved) {
                            remove(item);
                            notifyDataSetChanged();
                            Toast.makeText(mContext,"Removed successfully",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(mContext,"You can't remove the Admin!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }

        return my_view;
    }
}