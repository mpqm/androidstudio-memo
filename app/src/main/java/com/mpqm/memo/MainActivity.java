package com.mpqm.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv_todo;
    private FloatingActionButton mBtn_write;
    private ArrayList<TodoItem> mTodoItems;
    private DBHelper mDBHelper;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit(){
        mDBHelper = new DBHelper(this);
        mRv_todo = findViewById(R.id.rv_todo);
        mBtn_write = findViewById(R.id.btn_write);
        mTodoItems = new ArrayList<>();

        loadRecentDB();

        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog_MinWidth);
                dialog.setContentView(R.layout.dialog_edit);
                EditText et_title = dialog.findViewById(R.id.et_title);
                EditText et_content = dialog.findViewById(R.id.et_content);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                EditText et_date = dialog.findViewById(R.id.et_date);
                EditText et_time = dialog.findViewById(R.id.et_time);
                DatePicker datePicker = dialog.findViewById(R.id.datepicker);
                datePicker.init(2021, 1, 13, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String mydate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        et_date.setText(mydate);
                    }
                });

                TimePicker timePicker = dialog.findViewById(R.id.timepicker);
                timePicker.setIs24HourView(true);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        String mytime = hourOfDay + ":" + minute;
                        et_time.setText(mytime);
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String result = et_date.getText().toString()+" "+et_time.getText().toString();
                        String currentTime = result;
                        mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(), currentTime);
                        TodoItem item = new TodoItem();
                        item.setTitle(et_title.getText().toString());
                        item.setContent(et_content.getText().toString());
                        item.setWriteDate(currentTime);
                        mAdapter.addItem(item);
                        mRv_todo.smoothScrollToPosition(0);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void loadRecentDB(){
        mTodoItems = mDBHelper.getTodoList();
        if(mAdapter == null){
            mAdapter = new CustomAdapter(mTodoItems, this);
            mRv_todo.setHasFixedSize(true);
            mRv_todo.setAdapter(mAdapter);
        }
    }
}