package com.example.peojekpertama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //DATABASE VERSION
    private static final int DATABASE_VERSION = 1;

    //DATABASE NAME
    private static final String DATABASE_NAME = "taskData";

    //Table Name in Database Country
    private static final String TABLE_TASK = "taskTodo";

    //Country Tabel Colums names
    private static final String KEY_ID = "id";
    private static final String TASK = "Todo_List";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASK + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                TASK +" TEXT"+ ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK);
        onCreate(sqLiteDatabase);
    }

    void addTaskTodo(TaskTodo taskTodo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK, taskTodo.getTaskTodo()); //input country name


        //inserting row
        db.insert(TABLE_TASK, null, values);

        db.close();
    }


    public List<TaskTodo> getAllTask() {
        List<TaskTodo> taskList = new ArrayList<TaskTodo>();
        //select all query
        String selectQuery = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                TaskTodo taskTodo = new TaskTodo();
                taskTodo.setId(Integer.parseInt(cursor.getString(0)));
                taskTodo.setTaskTodo(cursor.getString(1));
                taskList.add(taskTodo);
            }while(cursor.moveToNext());
        }
        return taskList;
    }

    public void deleteTaskTodo(TaskTodo tasktodo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK,KEY_ID + "=?",
                new String[] {String.valueOf(tasktodo.getId()) });
        db.close();
    }


}
