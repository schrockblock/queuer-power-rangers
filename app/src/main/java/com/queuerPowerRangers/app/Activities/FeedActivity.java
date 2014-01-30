package com.queuerPowerRangers.app.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.queuerPowerRangers.app.Databases.ProjectDataSource;
import com.queuerPowerRangers.app.Models.Task;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Views.EnhancedListView;
import com.queuerPowerRangers.app.Models.Project;
import com.queuerPowerRangers.app.Adapters.FeedAdapter;
import com.queuerPowerRangers.app.Activities.ProjectActivity;

import java.util.ArrayList;
import java.util.Date;

import static android.widget.AdapterView.*;

/**
 * Created by Rotios on 1/15/14.
 */
public class FeedActivity extends ActionBarActivity {
    private int project_id = 0;
    private ArrayList<Project> projects = new ArrayList<Project>();
    private FeedAdapter adapter;
    ProjectDataSource projectDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Project List");

        projectDataSource = new ProjectDataSource(this);
        try {
            projectDataSource.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        projects = projectDataSource.getAllProjects();
        projectDataSource.close();

        //get the adapter for the view
        adapter = new FeedAdapter(this, projects);
        EnhancedListView listView = (EnhancedListView)findViewById(R.id.activity_feed);
        listView.setAdapter(adapter);


        //Set a dismissCallback to swipe things off, but allow user to bring it back if necessary
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                final Project project = adapter.getItem(position);
                adapter.remove(position);
                projectDataSource.open();
                projectDataSource.deleteProject(project);
                projectDataSource.close();
                adapter.notifyDataSetChanged();
            return new EnhancedListView.Undoable() {
                @Override
                public void undo() {
                    adapter.insert(project, position);
                }
            };
            }});

        //allow Items to be clicked long to take them to the tasks associated with it
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProjectActivity.class);
                intent.putExtra("project_name", adapter.getItem(i).getName());
                intent.putExtra("project_id", project_id);
                startActivity(intent);
                onPause();
                return true;
            }
        });

        //set listener to check if an item is clicked to be modified
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEditDialogueBox(adapter.getItem(position));
            }
        });

        if (projects.isEmpty()){ openCreateDialogueBox(); }
        listView.enableSwipeToDismiss();
       // listView.enableRearranging();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_project) {
         openCreateDialogueBox();
        }
        else if (id == R.id.action_log_out){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openEditDialogueBox(final Project edit_project){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Edit Project");

        View layout = getLayoutInflater().inflate(R.layout.edit_project, null);

        final EditText projectTitle = (EditText)layout.findViewById(R.id.project);
        projectTitle.setText(edit_project.getName());
        final EditText position = (EditText)layout.findViewById(R.id.position);
        //position.setEnabled(false);

        alertDialogBuilder
                //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                .setCancelable(true)
                .setView(layout)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String project_name = projectTitle.getText().toString();
                                if (project_name == null || project_name.equals("")) {
                                    projectTitle.requestFocus();
                                    projectTitle.setHint("ide a task title");
                                } else {
                                    projects.remove(edit_project);
                                    edit_project.setName(project_name);
                                    projects.add(0, edit_project);
                                    //projectDataSource.updateProject(edit_project);
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

    private void openCreateDialogueBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("New Project");

        View layout = getLayoutInflater().inflate(R.layout.new_project, null);

        final EditText projectTitle = (EditText)layout.findViewById(R.id.project);
        final EditText projectId = (EditText)layout.findViewById(R.id.position);
        //projectId.setText(project_id);

        // set dialog message
        alertDialogBuilder
                //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                .setCancelable(true)
                .setView(layout)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String project_pos = projectId.getText().toString();
                                String project_title = projectTitle.getText().toString();
                                int project_current_id = Integer.parseInt(project_pos);
                                if (project_pos == null || project_pos.equals("") || project_title == null || project_title.equals("")) {
                                    projectTitle.requestFocus();
                                    projectTitle.setHint("Please type in a project name");
                                } else {
                                    projectDataSource.open();
                                    Project project = projectDataSource.createProject(projectTitle.getText().toString(), 0, 0, new Date(), new Date());
                                    projectDataSource.close();
                                    projects.add(0, project);
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
}

