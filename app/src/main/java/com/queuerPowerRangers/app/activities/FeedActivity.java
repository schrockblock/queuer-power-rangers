package com.queuerPowerRangers.app.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Views.EnhancedListView;
import com.queuerPowerRangers.app.Models.Project;
import com.queuerPowerRangers.app.Adapters.FeedAdapter;

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
            projects.add(new Project(i, "Project " + i));
        }

        adapter = new FeedAdapter(this, projects);
        EnhancedListView listView = (EnhancedListView)findViewById(R.id.activity_feed);
        listView.setAdapter(adapter);
        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                final Project project = adapter.getItem(position);
                adapter.remove(position);
 return null;
            }
        });
        //listView.setOnClickListener();
    }

}