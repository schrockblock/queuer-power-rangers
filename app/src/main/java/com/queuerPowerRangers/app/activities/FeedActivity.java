package com.queuerPowerRangers.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.adapters.FeedAdapter;
import com.queuerPowerRangers.app.models.Project;
import com.queuerPowerRangers.app.views.EnhancedListView;

import java.util.ArrayList;

/**
 * Created by Michael on 1/15/14.
 */
public class FeedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_feed);

        ArrayList<Project> projects = new ArrayList<Project>(20);
        for(int i =0; i < 20; i++) {
            projects.add(new Project(i, "Project: " + i));
        }

        EnhancedListView listView = (EnhancedListView) findViewById(R.id.lv_projects);
        listView.setAdapter(new FeedAdapter(this, projects));
    }


}
