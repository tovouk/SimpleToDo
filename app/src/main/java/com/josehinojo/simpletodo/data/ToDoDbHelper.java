package com.josehinojo.simpletodo.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todos.db";

    private static final int DATABASE_VERSION = 3;

    public ToDoDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + ToDoContract.ToDoEntry.TABLE_NAME +
                " (" + ToDoContract.ToDoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoContract.ToDoEntry.COLUMN_TASK_NAME + " TEXT NOT NULL, " +
                ToDoContract.ToDoEntry.COLUMN_DUE_DATE + " DATE NOT NULL," +
                ToDoContract.ToDoEntry.COLUMN_DAY_OF_WEEK + " TEXT NOT NULL," +
                ToDoContract.ToDoEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ToDoContract.ToDoEntry.TABLE_NAME);
        onCreate(db);
    }
}
