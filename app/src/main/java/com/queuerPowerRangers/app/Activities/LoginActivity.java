package com.queuerPowerRangers.app.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Managers.LoginManager;

public class LoginActivity extends ActionBarActivity implements LoginManagerCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        Button login = (Button)findViewById(R.id.btn_login);
        final EditText user = (EditText)findViewById(R.id.et_username);
        final EditText pass = (EditText)findViewById(R.id.et_password);
        final CheckBox remember = (CheckBox)findViewById(R.id.remember);
        final TextView create = (TextView)findViewById(R.id.create_account);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change AccountActivity to the class you want to switch to then uncomment
                Intent i = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(i);
                onStop();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("") || pass.getText().toString().equals("")){showAlertDialogueBox();}
                else{
                 if (remember.isChecked()){
                    //save username and password
                    SharedPreferences preferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("remember", true);
                    editor.putString("username", user.getText().toString());
                    editor.putString("password", pass.getText().toString());
                    editor.commit();
                }
                  /Intent i = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(i);
                    finish();*/
               LoginManager manager = LoginManager.getInstance();
                manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    manager.login(user.getText().toString(), pass.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }

        });
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences.getBoolean("remember", false)){
            user.setText(preferences.getString("username", ""));
            pass.setText(preferences.getString("password", ""));
            remember.setChecked(true);
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void startedRequest() {
        findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.login_unsuccessful).setVisibility(View.GONE);
    }

    @Override
    public void finishedRequest(boolean successful) {
        findViewById(R.id.login_progress).setVisibility(View.GONE);
        findViewById(R.id.login_unsuccessful).setVisibility(View.GONE);

        if(successful){
            Log.d("THIS HAPPENED", "LOGIN WAS SUCCESSFUL");
            Intent i = new Intent(getApplicationContext(), com.queuerPowerRangers.app.Activities.FeedActivity.class);
            startActivity(i);
            finish();
    }else{
            Log.d("THIS HAPPENED", "LOGIN WAS UNSUCCESSFUL");
            findViewById(R.id.login_unsuccessful).setVisibility(View.VISIBLE);
            findViewById(R.id.create_account).setVisibility(View.VISIBLE);
        }
    }

    //Cannot login without first typing in something in the name and password field
    public void showAlertDialogueBox(){
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginActivity.this);

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

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}