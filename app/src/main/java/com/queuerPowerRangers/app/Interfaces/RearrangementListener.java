package com.queuerPowerRangers.app.Interfaces;

/**
 * Created by Michael on 1/22/14.
 */
public interface RearrangementListener {
    public void onStartedRearranging();
    public void swapElements(int indexOne, int indexTwo);
    public void onFinishedRearranging();
}