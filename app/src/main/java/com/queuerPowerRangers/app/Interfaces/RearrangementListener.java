package com.queuerPowerRangers.app.Interfaces;

/**
 * Created by eschrock on 12/26/13.
 */
public interface RearrangementListener {
    public void onStartedRearranging();
    public void swapElements(int indexOne, int indexTwo);
    public void onFinishedRearranging();
}
