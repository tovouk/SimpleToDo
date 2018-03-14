package com.josehinojo.simpletodo.data;


import android.provider.BaseColumns;

public class ToDoContract {

    public static final class ToDoEntry implements BaseColumns{
        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_TASK_NAME = "taskName";
        public static final String COLUMN_DUE_DATE = "due";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
