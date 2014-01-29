package com.queuerPowerRangers.app.Managers;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.queuerPowerRangers.app.Models.User;
import com.queuerPowerRangers.app.Packages.Constants;
import com.queuerPowerRangers.app.Packages.QueuerApplication;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;

import android.content.Context;
import android.util.Log;

import com.queuerPowerRangers.app.Models.SignInModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
        JSONObject signInJson = null;
        String jsonString = new Gson().toJson(new User(username, password));
        try {
            Log.d("THIS HAPPENED", "This happened " + jsonString );
            signInJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.QUEUER_SESSION_URL, signInJson, createListener(), createErrorListener()) {
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(
                            response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(
                            new Gson().fromJson(json, JSONObject.class), HttpHeaderParser.parseCacheHeaders(response));
                }catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        application.getRequestQueue().add(request);
    }

    private void authenticatedSuccessfully() throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        Log.d("THIS HAPPENED", "SUCCESSFULLY AUTHENTICATED LOGINMANAGER");
        callback.finishedRequest(true);
    }

    private void authenticatedUnsuccessfully() throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        Log.d("THIS HAPPENED", "UNSUCCESSFULLY AUTHENTICATED LOGINMANAGER");
        callback.finishedRequest(false);
    }

    private Response.ErrorListener createErrorListener(){
     return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    Log.d("THIS HAPPENED", "Error Response code: " + error.networkResponse.statusCode);
                    try {
                        authenticatedUnsuccessfully();
                    } catch (Exception e) {
                        Log.d("THIS HAPPENED", "Error authenticating");
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
                    Log.d("THIS HAPPENED", "Success Response: " + JSONObject.toString());
                    authenticatedSuccessfully();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("THIS HAPPENED", "Error Authenticating");
                }
            }
        };
}

}