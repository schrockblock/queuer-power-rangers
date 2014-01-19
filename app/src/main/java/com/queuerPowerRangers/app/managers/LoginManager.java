package com.queuerPowerRangers.app.managers;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.queuerPowerRangers.app.Interfaces.LoginManagerCallback;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
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
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Ross on 1/7/14.
 */
public class LoginManager {
    private Context context;
    private LoginManagerCallback callback;
    private static LoginManager instance;
    private static final String DEFAULT_CACHE_DIR = "volley";

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.callback = callback;
        this.context = context;
    }

    public void login(String username, String password) throws Exception {
        if(callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        authenticate(username, password);
    }

    public static RequestQueue newRequestQueue(Context context) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();

            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }   HttpStack stack;
        if (Build.VERSION.SDK_INT >= 9) {
            stack = new HurlStack();
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }


    private void authenticate(String username, String password) {


        Gson gson = (Gson) new Gson();
        String userfield = gson.toJson(R.string.username);
        String passfield = gson.toJson(R.string.password);
        String passtext = gson.toJson(password);
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, R.string.website_login, new JSONObject(new Gson().toJson(null, null), new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            if (true){
                try {
                    authenticatedSuccessfully();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else try {
                authenticatedUnsuccessfully();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }){



    }
        callback.startedRequest();
        newRequestQueue(context);
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
            instance = null;
        }
        return instance;
    }

    public AssetManager getAssets() {
        return null;
    }
}