package com.e.hiketogether.Presenters.Interfaces;

public interface Interact {
    void displayToast(String message);
    void displayProgressBar();
    void hideProgressBar();
    void setTouchDisabled();
    void setTouchEnabled();
}
