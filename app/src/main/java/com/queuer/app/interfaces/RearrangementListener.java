package com.queuer.app.interfaces;

/**
 * Created by Anthoney on 1/17/14.
 */
public interface RearrangementListener {
    public void onStartedRearranging();
    public void swapElements(int indexOne, int indexTwo);
    public void onFinishedRearranging();
}