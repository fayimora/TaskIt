package com.fayimora.taskit;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class TaskListActivity extends ActionBarActivity {

    private static final int EDIT_TASK_REQUEST = 10;
    private ArrayList<Task> mTasks;
    private int mLastPositionClicked;
    private TaskListAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        mTasks = new ArrayList<>();
        mTasks.add(new Task());
        mTasks.add(new Task());
        mTasks.add(new Task());
        mTasks.get(0).setName("Task 0");
        mTasks.get(0).setDueDate(new Date());

        mTasks.get(1).setName("Task 1");
        mTasks.get(1).setDone(true);

        mTasks.get(2).setName("Task 2");
        ListView listView = (ListView) findViewById(R.id.task_list);
        mTaskAdapter = new TaskListAdapter(mTasks);
        listView.setAdapter(mTaskAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLastPositionClicked = position;
                Intent i = new Intent(TaskListActivity.this, TaskActivity.class);
                Task t = (Task) parent.getAdapter().getItem(position);
                i.putExtra("TaskExtra", t);
                startActivityForResult(i, EDIT_TASK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK) {
            Task t = (Task) data.getSerializableExtra("TaskExtra");
            mTasks.set(mLastPositionClicked, t);
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    private class TaskListAdapter extends ArrayAdapter<Task>{
        public TaskListAdapter(ArrayList<Task> items){
            super(TaskListActivity.this, R.layout.task_list_row, R.id.task_item_name, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Task t = getItem(position);
            TextView taskName = (TextView) convertView.findViewById(R.id.task_item_name);
            taskName.setText(t.getName());

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.task_item_done);
            checkBox.setChecked(t.isDone());
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
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
