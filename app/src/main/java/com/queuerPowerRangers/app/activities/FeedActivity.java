package com.queuerPowerRangers.app.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.views.EnhancedListView;
import com.queuerPowerRangers.app.models.Project;
import com.queuerPowerRangers.app.adapters.FeedAdapter;
import com.queuerPowerRangers.app.activities.ProjectActivity;
import com.queuerPowerRangers.app.datasource.ProjectDataSource;

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
        } catch (SQLException e) {
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

