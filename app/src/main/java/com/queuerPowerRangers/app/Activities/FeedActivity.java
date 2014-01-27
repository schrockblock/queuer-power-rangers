package com.queuerPowerRangers.app.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.queuerPowerRangers.app.Adapters.FeedAdapter;
import com.queuerPowerRangers.app.Databases.ProjectDataSource;
import com.queuerPowerRangers.app.Models.Project;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Views.EnhancedListView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Rotios on 1/15/14.
 */
public class FeedActivity extends ActionBarActivity {
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ArrayList<Project> projects = new ArrayList<Project>(20);
        for (int i = 0; i < 20; i++){
            projects.add(new Project());
        }

        ProjectDataSource projectDataSource = new ProjectDataSource(this);
        try {
            projectDataSource.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}

