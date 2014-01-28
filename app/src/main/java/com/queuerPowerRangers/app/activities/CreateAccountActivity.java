package com.queuerPowerRangers.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.*;

import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.Managers.CreateAccountManager;

/**
 * Created by Michael on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity implements LoginManagerCallback {

    EditText username_text;
    EditText password_text;
    CreateAccountManager ca_manager;
    View progressBar;
    TextView account_successful;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        progressBar = findViewById(R.id.prog_bar);
        progressBar.setVisibility(View.GONE);

        account_successful = (TextView) findViewById(R.id.account_successful);
        account_successful.setVisibility(View.GONE);

        username_text = (EditText) findViewById(R.id.et_username);
        password_text = (EditText) findViewById(R.id.et_password);

        ca_manager = new CreateAccountManager();
        ca_manager.setCallback(this, this);
    }

    public void startedRequest() {
        progressBar.setVisibility(View.VISIBLE);
        account_successful.setVisibility(View.GONE);
        return;
    }

    public void finishedRequest(boolean successful) {
        progressBar.setVisibility(View.GONE);
        if(successful) {
            account_successful.setText("Account Created Successfully");
        } else {
            account_successful.setText("Error: Account could not be created");
        }
        account_successful.setVisibility(View.VISIBLE);
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