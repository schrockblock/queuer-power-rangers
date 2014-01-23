package com.queuerPowerRangers.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.*;

import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;

/**
 * Created by Michael on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity implements LoginManagerCallback {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar_layout);
        progressBar.setVisibility(View.GONE);
    }

    public void startedRequest() {
        return;
    }

    public void finishedRequest(boolean successful) {
        return;
    }
}
