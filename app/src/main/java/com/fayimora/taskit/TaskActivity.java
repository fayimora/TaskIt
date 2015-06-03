package com.fayimora.taskit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaskActivity extends ActionBarActivity {

    private Task mTask;
    private Button mDateButton;
    private Button mSaveButton;
    private Calendar mCal;
    private EditText mNameInput;
    CheckBox mDoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mTask = (Task) getIntent().getSerializableExtra("TaskExtra");

        mNameInput = (EditText) findViewById(R.id.task_name);
        mNameInput.setText(mTask.getName());

        mCal = Calendar.getInstance();
        mDateButton = (Button) findViewById(R.id.task_date);
        if(mTask.getDueDate() != null) {
            mCal.setTime(mTask.getDueDate());
            updateButton();
        } else {
            mCal.setTime(new Date());
            mDateButton.setText("No Date!");
        }

        mSaveButton = (Button) findViewById(R.id.save_button);

        mDoneInput = (CheckBox) findViewById(R.id.task_done);
        mDoneInput.setChecked(mTask.isDone());

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mCal.set(year, monthOfYear, dayOfMonth);
                        updateButton();
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setName(mNameInput.getText().toString());
                mTask.setDueDate(mCal.getTime());
                mTask.setDone(mDoneInput.isChecked());

                Intent i = new Intent();
                i.putExtra("TaskExtra", mTask);
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }

    private void updateButton(){
        DateFormat df = DateFormat.getDateInstance();
        mDateButton.setText(df.format(mTask.getDueDate()));
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
