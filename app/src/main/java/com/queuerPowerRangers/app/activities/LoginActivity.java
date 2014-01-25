package com.queuerPowerRangers.app.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;
=======
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
>>>>>>> 8b52c7fbfd7a3d07fb397aaaaa329fb7c736355d
import android.widget.TextView;

import com.queuerPowerRangers.app.Packages.QueuerApplication;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.managers.LoginManager;
<<<<<<< HEAD

public class LoginActivity extends ActionBarActivity{
=======
>>>>>>> 8b52c7fbfd7a3d07fb397aaaaa329fb7c736355d

public class LoginActivity extends ActionBarActivity implements LoginManagerCallback{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        Button login = (Button)findViewById(R.id.btn_login);
        final EditText user = (EditText)findViewById(R.id.et_username);
        final EditText pass = (EditText)findViewById(R.id.et_password);
        final CheckBox remember = (CheckBox)findViewById(R.id.remember);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remember.isChecked()){
                    //save username and password
                    SharedPreferences preferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("remember", true);
                    editor.putString("username", user.getText().toString());
                    editor.putString("password", pass.getText().toString());
                    editor.commit();
                }

                LoginManager manager = LoginManager.getInstance();
                manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    manager.login(user.getText().toString(), pass.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences.getBoolean("remember", false)){
            user.setText(preferences.getString("username", ""));
            pass.setText(preferences.getString("password", ""));
            remember.setChecked(true);
        }    }




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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

<<<<<<< HEAD
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginPageFragment extends Fragment {

        public LoginPageFragment() {

        }
=======
    @Override
    public void startedRequest() {
        findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
    }
>>>>>>> 8b52c7fbfd7a3d07fb397aaaaa329fb7c736355d

    @Override
    public void finishedRequest(boolean successful) {
        findViewById(R.id.login_progress).setVisibility(View.GONE);
        findViewById(R.id.login_unsuccessful).setVisibility(View.GONE);
        if(successful){
            new FeedActivity();
    }else{
            findViewById(R.id.login_unsuccessful).setVisibility(View.VISIBLE);
        }
    }

}
