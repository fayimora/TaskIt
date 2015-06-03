package com.fayimora.taskit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.DateFormat;


public class TaskActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Task task = (Task) getIntent().getSerializableExtra("TaskExtra");

        EditText taskNameInput = (EditText) findViewById(R.id.task_name);
        taskNameInput.setText(task.getName());

        Button taskDateButton = (Button) findViewById(R.id.task_date);
        if(task.getDueDate() != null) {
            DateFormat df = DateFormat.getDateInstance();
            taskDateButton.setText(df.format(task.getDueDate()));
        } else
            taskDateButton.setText("No Date!");

        Button saveButton = (Button) findViewById(R.id.save_button);

        CheckBox taskDoneInput = (CheckBox) findViewById(R.id.task_done);
        taskDoneInput.setChecked(task.isDone());

        taskDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
