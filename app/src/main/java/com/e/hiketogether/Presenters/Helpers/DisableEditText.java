package com.e.hiketogether.Presenters.Helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

@SuppressLint("AppCompatCustomView")
public class DisableEditText extends androidx.appcompat.widget.AppCompatEditText {
    // VARIABLES
    private final static String TAG = "DISABLE_EDIT_TEXT";
    private boolean change = true;

    // Constructors
    public DisableEditText(Context context) {
        super(context);
    }

    public DisableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Disable the Edit Text
    private void disableText() {
        setEnabled(false);
        Log.d(TAG, "Setting editText to disable");
    }

    // Enable the Edit Text
    private void enableText() {
        setEnabled(false);
        Log.d(TAG, "Setting editText to enable");
    }

    // What is the state and what should I do?
    public void changeState() {
        Log.d(TAG,"");
        if (change == true) {
            setText("");
            enableText();
            change = false;
        }
        else {
            disableText();
            change = true;
        }
    }
}
