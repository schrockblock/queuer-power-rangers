package com.queuerPowerRangers.app.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.queuerPowerRangers.app.Databases.TaskDataSource;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Adapters.ProjectAdapter;
import com.queuerPowerRangers.app.Models.Task;
import com.queuerPowerRangers.app.Views.EnhancedListView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eschrock on 1/17/14.
 */
public class ProjectActivity extends ActionBarActivity {
    private int project_id;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ProjectAdapter adapter;
    private String project_name;
    TaskDataSource taskDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

    // build the screen we are looking for with the project Title at the top
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        project_name = extras.getString("project_name" );
        project_id = extras.getInt("project_id");}
        else {
            project_id = 100;
            project_name = "No Name Given";
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(project_name + " " + project_id);

        // Get all the tasks available from the dataSource
        taskDataSource = new TaskDataSource(this);
        taskDataSource.open();
        tasks = taskDataSource.getAllTasks(project_id);
        taskDataSource.close();

        //set the adapter to change the listView
        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_tasks);
        adapter = new ProjectAdapter(this, tasks);
        listView.setAdapter(adapter);

        //what to do when you want to swipe to remove a task
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                final Task task = adapter.getItem(position);
                adapter.remove(position);
                taskDataSource.open();
                taskDataSource.deleteTask(task);
                taskDataSource.close();
                adapter.notifyDataSetChanged();
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        adapter.insert(task, position);
                        adapter.notifyDataSetChanged();
                    }
                };
            }
        });

        //set Long Click Listener so that all problems with long clicks are gone
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                openEditDialogueBox(adapter.getItem(i));
                return true;
            }
        });

        //set listener to check if an item is clicked to be modified
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            openEditDialogueBox(adapter.getItem(position));
        }
    });

        if (tasks.isEmpty()){ openCreateDialogueBox(); }

        listView.enableSwipeToDismiss();
        //disable enable rearranging to avoid problems (no code for it yet anyway)
        //listView.enableRearranging();
    }

    //Open a dialogue box to create a new task
    private void openCreateDialogueBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("New Task");
        //grab the components
        View layout = getLayoutInflater().inflate(R.layout.new_task, null);
        final EditText taskTitle = (EditText)layout.findViewById(R.id.task);
        final EditText colorTask  = (EditText)layout.findViewById(R.id.position);

        alertDialogBuilder
                .setCancelable(true)
                .setView(layout)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String task_name = taskTitle.getText().toString();
                                int color = Integer.parseInt(colorTask.getText().toString());

                                //do not allow the user to create a new task without first actually
                                // writing something in the name section
                                if (task_name == null || task_name == "") {
                                    taskTitle.requestFocus();
                                    taskTitle.setHint("Please provide a task title");
                                } else {
                                    taskDataSource.open();
                                    Task task = taskDataSource.createTask(task_name, project_id, 0, 0, false);
                                    taskDataSource.close();
                                    Log.d("THIS HAPPENED", task_name + "  ");
                                    //task.setColor(color)
                                    tasks.add(0, task);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void openEditDialogueBox(final Task edit_task){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    // set title
    alertDialogBuilder.setTitle("Edit Task");

    View layout = getLayoutInflater().inflate(R.layout.new_task, null);

    final EditText taskTitle = (EditText)layout.findViewById(R.id.task);
        taskTitle.setText(edit_task.getName());

    alertDialogBuilder
            //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
            .setCancelable(true)
            .setView(layout)
    .setPositiveButton("Ok",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String task_name = taskTitle.getText().toString();
                    if (taskTitle == null || task_name.equals("")) {
                        taskTitle.requestFocus();
                        taskTitle.setHint("Please provide a task title");
                    } else{
                    tasks.remove(edit_task);
                    edit_task.setName(task_name);
                    edit_task.setProject_id(project_id);
                    tasks.add(0, edit_task);
                    adapter.notifyDataSetChanged();
                    }
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Allow the user to create a task or log out
        int id = item.getItemId();
        if (id == R.id.action_add_task) {
            openCreateDialogueBox();
        }else if (id == R.id.action_log_out){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}