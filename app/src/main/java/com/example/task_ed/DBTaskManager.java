package com.example.task_ed;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

public class DBTaskManager extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    public static final String TABLE_TITLE = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_TIME = "task_time";

    public DBTaskManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //produce SQL command/query in string format and execute it on database
        String sqlCommand = "CREATE TABLE " + TABLE_TITLE + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK_NAME + " TEXT, " + COLUMN_TASK_TIME + " TEXT);";
        db.execSQL(sqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if upgrading database, simply delete the old db and create the new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        onCreate(db);
    }

    public void addTask(taskItems task){
        //allows us to set values to columns of a row quickly
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.get__task_name());
        values.put(COLUMN_TASK_TIME, task.get__task_time());
        SQLiteDatabase db = getWritableDatabase();
        //insert new row with given data
        db.insert(TABLE_TITLE, null, values);
        db.close();
    }

    //delete a task from the db. Might also help to overload method to accept task_name as well
    public void deleteTask(taskItems task){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TITLE + " WHERE " + COLUMN_ID + "=" + task.get__id() + ";");
    }

    //add all tasks stored in database to the input arraylist
    public ArrayList getTasks(){
        SQLiteDatabase db = getReadableDatabase();
        //allocate space for arraylist
        ArrayList<String[]> tempArray = new ArrayList<>();
        //command for SQL
        String command = "SELECT " + COLUMN_TASK_NAME + ", " + COLUMN_TASK_TIME + " FROM " + TABLE_TITLE;
        //use cursor to iterate over each row
        Cursor c = db.rawQuery(command, null);
        //go to first row
        c.moveToFirst();
        //iterate over each row until done, get data and append to araylist
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_TASK_NAME)) !=null){
                String temp[] = {c.getString(c.getColumnIndex(COLUMN_TASK_NAME)),
                                 c.getString(c.getColumnIndex(COLUMN_TASK_TIME))};
                tempArray.add(temp);
            }
            //move to next iteration
            c.moveToNext();
        }
        //close cursor and db when done
        c.close();
        db.close();
        return tempArray;
    }
}
