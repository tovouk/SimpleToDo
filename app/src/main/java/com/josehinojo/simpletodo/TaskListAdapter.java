package com.josehinojo.simpletodo;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.josehinojo.simpletodo.data.ToDoContract;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{

    private Cursor taskCursor;
    private Context taskContext;

    public TaskListAdapter(Context context, Cursor cursor){
        this.taskContext = context;
        this.taskCursor = cursor;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(taskContext);
        View view = inflater.inflate(R.layout.task_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position){
        if(!taskCursor.moveToPosition(position)){
            return;
        }
        String task = taskCursor.getString(taskCursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TASK_NAME));
        String dueDate = taskCursor.getString(taskCursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_DUE_DATE));
        long id = taskCursor.getLong(taskCursor.getColumnIndex(ToDoContract.ToDoEntry._ID));
        holder.taskView.setText(task);
        holder.dateView.setText(dueDate);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount(){
        return taskCursor.getCount();
    }

    public void changeCursor(Cursor newCursor){
        if(taskCursor != null){
            taskCursor.close();
        }

        taskCursor = newCursor;

        if(newCursor != null){
            this.notifyDataSetChanged();
        }

    }


    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskView;
        TextView dateView;

        public TaskViewHolder(View taskItem){
            super(taskItem);
            taskView = (TextView)taskItem.findViewById(R.id.task_text_view);
            dateView = (TextView)taskItem.findViewById(R.id.date_text_view);
        }

    }
}
