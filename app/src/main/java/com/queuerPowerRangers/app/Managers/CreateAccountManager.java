package com.queuerPowerRangers.app.Managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;
import com.queuerPowerRangers.app.Models.CreateAccountModel;
import com.queuerPowerRangers.app.Models.User;
import com.queuerPowerRangers.app.Packages.Constants;

import org.json.JSONObject;

/**
 * Created by Michael on 1/14/14.
 */
public class CreateAccountManager {

    LoginManagerCallback callback;
    Context context;
    RequestQueue requestQueue;

   //Create Account Manager singleton
    private static CreateAccountManager instance = null;
    protected CreateAccountManager() {}

    public static CreateAccountManager getInstance(){
        if(instance == null){
            instance = new CreateAccountManager();
        } return instance;
    }

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void createAccount(String username, String password) throws Exception {
        if (callback == null) throw new Exception( "Must supply a LoginManagerCallback" );
        callback.startedRequest();
        create(username, password);
    }

    private void create( String username, String password)throws Exception{
        // package in Gson
        Gson gson = new Gson();
        String json = gson.toJson(new CreateAccountModel(new User(username, password)));
        System.out.println("Json: " + json);
        // send with volley
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                Log.d("Connection", "Success Response: " + response.toString());
                if(!response.has("errors")){
                try {
                    createdSuccessfully();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else try {
                    System.out.println(response);
                    createdUnsuccessfully();
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }

        };
        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse( VolleyError error){
                if (error.networkResponse != null){
                    Log.d("Connection", "Error Response code: " + error.networkResponse.statusCode);
                    try {
                        createdUnsuccessfully();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Constants.QUEUER_CREATE_ACCOUNT_URL,
                new JSONObject(json),
                listener,
                errorListener);
        requestQueue.add(request);
    }

    private void createdSuccessfully() throws Exception{
        if( callback == null) throw new Exception( "Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
    }

    private void createdUnsuccessfully() throws Exception{
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
}
