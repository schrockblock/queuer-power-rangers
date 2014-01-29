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

import org.json.JSONObject;

/**
 * Created by Michael on 1/14/14.
 */
public class CreateAccountManager {

    LoginManagerCallback callback;
    Context context;
    RequestQueue requestQueue;

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
        CreateAccountModel model = new CreateAccountModel(new User(username, password));
        Gson gson = new Gson();
        String json = gson.toJson(model);
        System.out.println("Json: " + json);
        // send with volley
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                    Log.d("Connection", "Success Response: " + response.toString());
                    try {
                        createdSuccessfully();
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
                "http://queuer-rndapp.rhcloud.com/api/v1/users",
                new JSONObject(json),
                listener,
                errorListener);


        requestQueue.add(request);

    }

    private void createdSuccessfully() throws Exception{
        if( callback == null) throw new Exception( "Must supply a LoginManagerCallback");
        callback.finishedRequest(true);

        System.out.println( "Success");
    }

    private void createdUnsuccessfully() throws Exception{
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);

        System.out.println("sad");
    }
}
