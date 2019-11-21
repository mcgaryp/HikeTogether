package com.e.hiketogether.Presenters.Interfaces;

/**
 * PURPOSE
 *      This is a listener interface intended to listen and let managers know if some
 *      thread was successful or not in what ever needs done in the background
 */
public interface Listener {
    void onSuccess();
    void onFail();
}
