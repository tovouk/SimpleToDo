package com.josehinojo.simpletodo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.josehinojo.simpletodo.data.ToDoContract;
import com.josehinojo.simpletodo.data.ToDoDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListener{

    private TaskListAdapter taskListAdapter;
    private SQLiteDatabase taskDb;
    private EditText newTaskEditText;
    private EditText newDueDateEditText;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DIALOG_DATE = "MainActivity.DateDialog";
    private java.sql.Date sqlFormattedDate;
    private String friendlyDateFormat;

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

        taskListAdapter = new TaskListAdapter(this,cursor);

        taskList.setAdapter(taskListAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long)viewHolder.itemView.getTag();
                deleteTask(id);
                taskListAdapter.changeCursor(getAllTodos());

            }
        }).attachToRecyclerView(taskList);

    }
    public void addToTaskList(View view){
        if(newTaskEditText.getText().length() == 0 || newDueDateEditText.getText().length() == 0){
            return;
        }
        addTask(newTaskEditText.getText().toString(),sqlFormattedDate);
        taskListAdapter.changeCursor(getAllTodos());
        newTaskEditText.clearFocus();
        newTaskEditText.getText().clear();
        newDueDateEditText.getText().clear();

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

    public void openDatePicker(View view){
        DatePickerFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), DIALOG_DATE);
    }

    public long addTask(String task, java.sql.Date dueDate){
        ContentValues cv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        cv.put(ToDoContract.ToDoEntry.COLUMN_TASK_NAME,task);
        cv.put(ToDoContract.ToDoEntry.COLUMN_DUE_DATE,dateFormat.format(sqlFormattedDate));
        cv.put(ToDoContract.ToDoEntry.COLUMN_DAY_OF_WEEK,friendlyDateFormat);
        return taskDb.insert(ToDoContract.ToDoEntry.TABLE_NAME,null,cv);
    }

    public boolean deleteTask(long id){
        return taskDb.delete(ToDoContract.ToDoEntry.TABLE_NAME,
                ToDoContract.ToDoEntry._ID + "=" + id,null) > 0;
    }


    @Override
    public void onFinishDialog(Date date) {
        friendlyDateFormat = getDayofWeek(date);
        try {
            newDueDateEditText.setText(formatDate(date), TextView.BufferType.EDITABLE);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getDayofWeek(Date date){
        SimpleDateFormat dayofWeek = new SimpleDateFormat("EEEE MM/dd/YYYY");
        return dayofWeek.format(date);
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(date);
        sqlFormattedDate =  java.sql.Date.valueOf(stringDate);

        return sdf.format(date);
    }
}
