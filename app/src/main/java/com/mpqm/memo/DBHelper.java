package com.mpqm.memo;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static int DB_VERSION = 1;
    private static String DB_NAME = "simplememo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Todolist(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT , content TEXT , writeDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    //메모조회(db)
    public ArrayList<TodoItem> getTodoList(){
        ArrayList<TodoItem> todoItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                TodoItem todoItem = new TodoItem();
                todoItem.setId(id);
                todoItem.setTitle(title);
                todoItem.setContent(content);
                todoItem.setWriteDate(writeDate);
                todoItems.add(todoItem);
            }
        }
        cursor.close();
        return todoItems;
    }
    //메모넣기(db)
    public void InsertTodo(String _title, String _content, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES('" +_title+ "','"+ _content+ "','" +_writeDate+ "');");
    }
    //메모수정(db)
    public void UpdateTodo(String _title, String _content, String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET title='"+_title+"', content='"+_content+"', writeDate='"+_writeDate+"' WHERE writeDate = '"+_beforeDate+"'");
    }
    //메모제거(db)
    public void deleteTodo(String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate ='" + _beforeDate + "'");
    }
}
