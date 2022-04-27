package com.example.peojekpertama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TaskTodo> tasks;
    DatabaseHelper db;

    PopupWindow pwindo;
    Activity activity;
    ListView listView;
    CustomTaskList customTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        db = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.list);

        Log.d("MainActivity ", "Before reading mainActivity");
        tasks = (ArrayList) db.getAllTask();

        for (TaskTodo taskTodo : tasks) {
            String log = "Id: " + taskTodo.getId() + " ,Task: " + taskTodo.getTaskTodo();

            Log.d("Name: ", log);
        }

        CustomTaskList customTaskList = new CustomTaskList(this, tasks, db);
        listView.setAdapter(customTaskList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You Selected " + tasks.get(position).getTaskTodo() + " as Task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_popup, null);
        dialogBuilder.setView(dialogView);


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final EditText taskEdit = (EditText) dialogView.findViewById(R.id.editTaks);


        Button save = (Button) dialogView.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskStr = taskEdit.getText().toString();

                TaskTodo taskTodo = new TaskTodo(taskStr);
                db.addTaskTodo(taskTodo);
                if (customTaskList == null) {
                    customTaskList = new CustomTaskList(activity, tasks, db);
                    listView.setAdapter(customTaskList);
                }
                customTaskList.tasks = (ArrayList) db.getAllTask();
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

                for (TaskTodo taskTodo1 : tasks) {
                    String log = "Id: " + taskTodo1.getId() + " ,Task: " + taskTodo1.getTaskTodo();

                    //Writing country to log
                    Log.d("Name: ", log);
                }
                alertDialog.dismiss();
            }
        });

        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_task:
                addPopup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}