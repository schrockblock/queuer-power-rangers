package com.queuerPowerRangers.app.managers;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.managers.LoginManager;

/**
 * Created by Ross on 1/7/14.
 */
public class LoginManager {

    private LoginManagerCallback callback;
    private static LoginManager instance;
    protected LoginManager(){}

    public void setCallback(LoginManagerCallback callback) {
        this.callback = callback;
    }

    public void login(String username, String password) throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        authenticate(username, password);
    }

    private void authenticate(String username, String password) {
        Gson gson = (Gson) new Gson();

        String userfield = gson.toJson(R.string.username);
         String name = gson.toJson(username);

        String passfield = gson.toJson(R.string.password);
        String passtext = gson.toJson(password);

        

    }

    private void authenticatedSuccessfully() throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
    }

    private void authenticatedUnsuccessfully() throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
    public static LoginManager getInstance() {
        if(instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }
}