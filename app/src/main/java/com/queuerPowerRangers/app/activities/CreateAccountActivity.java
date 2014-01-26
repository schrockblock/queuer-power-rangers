package com.queuerPowerRangers.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.managers.CreateAccountManager;
import com.queuerPowerRangers.app.managers.LoginManager;
import com.queuerPowerRangers.app.models.CreateAccountModel;
import com.queuerPowerRangers.app.models.User;

/**
 * Created by Michael on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity implements LoginManagerCallback {

    EditText username_text;
    EditText password_text;
    CreateAccountManager ca_manager;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.prog_bar);
        progressBar.setVisibility(View.GONE);


        username_text = (EditText) findViewById(R.id.et_username);
        password_text = (EditText) findViewById(R.id.et_password);

        ca_manager = new CreateAccountManager();
        ca_manager.setCallback(this, this);
    }

    public void startedRequest() {
        return;
    }

    public void finishedRequest(boolean successful) {
        return;
    }

    public void create_account(View view) {

        // take username
        String username = username_text.getText().toString();
        // take password
        String password = password_text.getText().toString();

        try {
            ca_manager.createAccount(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
