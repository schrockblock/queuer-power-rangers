package com.queuerPowerRangers.app.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.queuerPowerRangers.app.Adapters.ProjectAdapter;
import com.queuerPowerRangers.app.Databases.ProjectDataSource;
import com.queuerPowerRangers.app.Models.Task;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Views.EnhancedListView;
import com.queuerPowerRangers.app.Models.Project;
import com.queuerPowerRangers.app.Adapters.FeedAdapter;
import com.queuerPowerRangers.app.activities.ProjectActivity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Rotios on 1/15/14.
 */
public class FeedActivity extends ActionBarActivity {
    private int project_id;
    private ArrayList<Project> projects = new ArrayList<Project>();
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        project_id = getIntent().getIntExtra("project_id",-1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Project " + project_id);

        ArrayList<Project> projects = new ArrayList<Project>(20);
        for (int i = 0; i < 20; i++){
            projects.add(new Project());
        }

        ProjectDataSource projectDataSource = new ProjectDataSource(this);
        projectDataSource.open();
        projects = projectDataSource.getAllProjects();
        projectDataSource.close();

        adapter = new FeedAdapter(this, projects);
        EnhancedListView listView = (EnhancedListView)findViewById(R.id.activity_feed);
        listView.setAdapter(adapter);
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                final Project project = adapter.getItem(position);
                adapter.remove(position);
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        adapter.insert(project, position);
                    }
                };
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FeedActivity.this, ProjectActivity.class);
                intent.putExtra("project_id", (int)adapter.getItemId(position));
                startActivity(intent);
            }
        });

        listView.enableSwipeToDismiss();
        listView.enableRearranging();
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("New Project");

            View layout = getLayoutInflater().inflate(R.layout.new_project, null);

            final EditText projectTitle = (EditText)layout.findViewById(R.id.project);

            // set dialog message
            alertDialogBuilder
                    //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                    .setCancelable(true)
                    .setView(layout)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Project project = new Project();
                                    project.setName(projectTitle.getText().toString());
                                    project.setProject_id(project_id);
                                    projects.add(0, project);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {}
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}