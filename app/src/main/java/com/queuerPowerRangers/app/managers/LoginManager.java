package com.queuerPowerRangers.app.managers;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.queuerPowerRangers.app.Packages.Constants;
import com.queuerPowerRangers.app.Packages.QueuerApplication;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.queuerPowerRangers.app.Models.SignInModel;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.activities.LoginActivity;
import com.queuerPowerRangers.app.managers.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static com.queuerPowerRangers.app.Packages.Constants.QUEUER_SESSION_URL;

/**
 * Created by Ross on 1/7/14.
 */
public class LoginManager{
    private Context context;
    private LoginManagerCallback callback;
    private QueuerApplication application = new QueuerApplication();

    private static LoginManager instance = null;
    protected LoginManager(){}
    public static LoginManager getInstance(){
        if(instance == null){
            instance = new LoginManager();
        } return instance;
    }

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void login(String username, String password) throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        authenticate(username, password);
    }

    private void authenticate(String username, String password){
        application.setRequestQueue(Volley.newRequestQueue(context));
        SignInModel model = new SignInModel(username, password);
        JSONObject signInJson = null;
        String jsonString = new Gson().toJson(model);
        try {
            Log.d("LoginManager", "This happened" + jsonString );
            signInJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void authenticatedSuccessfully() throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
    }

    private void authenticatedUnsuccessfully() throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }

    private Response.ErrorListener createErrorListener(){
     return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    Log.d("LoginActivity", "Error Response code: " + error.networkResponse.statusCode);
                    try {
                        authenticatedUnsuccessfully();
                    } catch (Exception e) {
                        Log.d("LoginActivity", "Error authenticating");
                        e.printStackTrace();
                    }
                }
            }};
     }
    private Response.Listener<JSONObject> createListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject JSONObject) {
                try {
                    Log.d("LoginActivity", "Success Response: " + JSONObject.toString());
                    authenticatedSuccessfully();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("LoginActivity", "Error Authenticating");
                }
            }
        };
}

}