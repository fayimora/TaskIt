package com.fayimora.taskit;

import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class TaskListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Task[] items = {new Task(), new Task(), new Task()};
        items[0].setName("Task 0");
        items[1].setName("Task 1");
        items[1].setDone(true);
        items[2].setName("Task 2");
        ListView listView = (ListView) findViewById(R.id.task_list);
        listView.setAdapter(new TaskListAdapter(items));
    }

    private class TaskListAdapter extends ArrayAdapter<Task>{
        public TaskListAdapter(Task[] items){
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
