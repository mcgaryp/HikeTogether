package com.e.hiketogether.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.hiketogether.R;

/**
 * PURPOSE:
 *      This class will display the home screen including a map and buttons that will transfer the
 *      user to different fragments such as the settings, Favorites, Trails, Map and Search Bar
 */
public class HomeActivity extends AppCompatActivity {
    static final int LOGIN_REQUEST = 1; //Request code for LoginActivity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //When user clicks "Login", this function will create the Activity and receive the user's account
    public void openLoginActivity(View view) {
        Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);
    }

    //When the Login Activity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is return through the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
