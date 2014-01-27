package com.queuerPowerRangers.app.Packages;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Rotios on 1/25/14.
 */
public class QueuerApplication extends Application {
    private RequestQueue queue;

    public void setRequestQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public RequestQueue getRequestQueue(){
        if (queue == null) queue = Volley.newRequestQueue(this);
        return queue;
    }
}