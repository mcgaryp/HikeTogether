package com.e.hiketogether.Views.SpecializedViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class UniqueEditText extends androidx.appcompat.widget.AppCompatEditText {
    // VARIABLES
    private final static String TAG = "DISABLE_EDIT_TEXT";
    private boolean change;

    // Constructors
    public UniqueEditText(Context context) {
        super(context);
        change = true;
    }

    public UniqueEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        change = true;
    }

    public UniqueEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        change = true;
    }

    // Disable the Edit Text
    private void disableText(Button button) {
        setEnabled(false);
        Log.d(TAG, "Setting editText to disable");
        button.setText("Edit");
    }

    // Enable the Edit Text
    private void enableText(Button button) {
        setEnabled(true);
        Log.d(TAG, "Setting editText to enable");
        button.setText("Save");
        requestFocus();
    }

    // What is the state and what should I do?
    public void changeState(Button button) {
        Log.d(TAG,"");
        if (change == true) {
//            setText("");
            enableText(button);
            change = false;
        }
        else {
            disableText(button);
            change = true;
        }
    }

    public boolean getChange() { return change; }
}
