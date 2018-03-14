package com.josehinojo.simpletodo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.josehinojo.simpletodo.data.ToDoContract;
import com.josehinojo.simpletodo.data.ToDoDbHelper;

public class MainActivity extends AppCompatActivity {

    private TaskListAdapter taskListAdapter;
    private SQLiteDatabase taskDb;
    private EditText newTaskEditText;
    private EditText newDueDateEditText;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView taskList;

        taskList = (RecyclerView)findViewById(R.id.taskList);
        newTaskEditText = (EditText)findViewById(R.id.task_text);
        newDueDateEditText = (EditText)findViewById(R.id.task_due);

        taskList.setLayoutManager(new LinearLayoutManager(this));
        ToDoDbHelper dbhelper = new ToDoDbHelper(this);

        taskDb = dbhelper.getWritableDatabase();

        Cursor cursor = getAllTodos();

    }
    //TODO add logic to add and update tasks for adapter
    public void addToTaskList(View view){

    }

    private Cursor getAllTodos(){
        return taskDb.query(
                ToDoContract.ToDoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ToDoContract.ToDoEntry.COLUMN_DUE_DATE
        );
    }
    //TODO create String tag between this class and another class via intent
    //have the other intent contain a DatePicker pass the Date as a String
    //fill EditText for due date with the date String properly formatted for SQLite
    public void openDatePicker(View view){

    }

    //TODO add logic for inserting task to Database
    public long addTask(String task, String dueDate){
        return 0;
    }

    public boolean deleteTask(long id){
        return taskDb.delete(ToDoContract.ToDoEntry.TABLE_NAME,
                ToDoContract.ToDoEntry._ID + "=" + id,null) > 0;
    }


}
