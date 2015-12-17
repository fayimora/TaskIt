package com.fayimora.taskit;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    private static final int CREATE_TASK_REQUEST = 20;
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

//        registerForContextMenu(listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.menu_task_list_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == EDIT_TASK_REQUEST) {
                Task t = (Task) data.getSerializableExtra("TaskExtra");
                mTasks.set(mLastPositionClicked, t);
            } else if (requestCode == CREATE_TASK_REQUEST) {
                Task t = (Task) data.getSerializableExtra("TaskExtra");
                mTasks.add(t);
            }
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
        if (id == R.id.add_task) {
            Intent i = new Intent(TaskListActivity.this, TaskActivity.class);
            startActivityForResult(i, CREATE_TASK_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_task_list_context, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete_task) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            mTasks.remove(menuInfo.position);
            mTaskAdapter.notifyDataSetChanged();
             return true;
        }
        return super.onContextItemSelected(item);
    }
}
