package com.queuerPowerRangers.app.Managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;

/**
 * Created by Michael on 1/14/14.
 */
public class CreateAccountManager {

    LoginManagerCallback callback;
    Context context;

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void createAccount(String username, String password) throws Exception{
        if (callback == null) throw new Exception( "Must supply a LoginManagerCallback" );
        callback.startedRequest();
        create(username, password);
    }

    private void create( String username, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Response.Listener<String> listener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
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
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://queuer-rndapp.rhcloud.com/api/v1/users",
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
