package com.queuerPowerRangers.app.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        ca_manager.setCallback(CreateAccountActivity.this, CreateAccountActivity.this);
        final TextView goBack = (TextView)findViewById(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change AccountActivity to the class you want to switch to then uncomment
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                onStop();
            }
        });
    }

    public void startedRequest() {
        progressBar.setVisibility(View.VISIBLE);
        account_successful.setVisibility(View.GONE);
        return;
    }

    public void finishedRequest(boolean successful) {
        progressBar.setVisibility(View.GONE);
        if(successful) {
            account_successful.setVisibility(View.VISIBLE);
        } else {
           findViewById(R.id.account_unsuccessful).setVisibility(View.VISIBLE);

        }

    }

    public void create_account(View view) {
        // take username
        String username = username_text.getText().toString();
        // take password
        String password = password_text.getText().toString();
        if(username.equals("") || password.equals("")){
            showAlertDialogueBox();
        } else{
        try {
            ca_manager.createAccount(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    }

    //Cannot login without first typing in something in the name and password field
    public void showAlertDialogueBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CreateAccountActivity.this);

        // set title
        alertDialogBuilder.setTitle("Please provide a username and password!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click OK to go back!")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}