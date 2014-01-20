package com.queuer.app.interfaces;

/**
 * Created by Anthoney on 1/17/14.
 */
public interface LoginManagerCallback {
    public void startedRequest();
    public void finishedRequest(boolean successful);
}