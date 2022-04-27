package com.example.peojekpertama;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomTaskList extends BaseAdapter {


    private Activity context;
    ArrayList<TaskTodo> tasks;
    DatabaseHelper db;
    BaseAdapter ba;

    public CustomTaskList(Activity context, ArrayList<TaskTodo> tasks, DatabaseHelper db) {
        this.context = context;
        this.tasks = tasks;
        this.db = db;
    }

    public static class ViewHolder {

        TextView textViewTask;
        Button complete;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        CustomTaskList.ViewHolder vh;
        if (convertView == null) {
            vh = new CustomTaskList.ViewHolder();
            row = inflater.inflate(R.layout.list_todo, null, true);

            vh.textViewTask = (TextView) row.findViewById(R.id.textViewTask);
            vh.complete = (Button) row.findViewById(R.id.taskCompleted);

            //store the holder with the view
            row.setTag(vh);
        } else {
            vh = (CustomTaskList.ViewHolder) convertView.getTag();
        }

        vh.textViewTask.setText(tasks.get(position).getTaskTodo());

        final int positionPopup = position;

        vh.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Last Index", "" + positionPopup);
                //integer index
                db.deleteTaskTodo(tasks.get(positionPopup));

                tasks = (ArrayList) db.getAllTask();
                Log.d("TaskTodo Size", "" + tasks.size());
                notifyDataSetChanged();
            }
        });
        return row;
    }



}
