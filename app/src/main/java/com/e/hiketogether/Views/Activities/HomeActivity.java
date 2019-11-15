package com.e.hiketogether.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.R;

/**
 * PURPOSE:
 *      This class will display the home screen including a map and buttons that will transfer the
 *      user to different fragments such as the settings, Favorites, Trails, Map and Search Bar
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HOME_ACTIVITY"; //Log tag
    private static final int LOGIN_REQUEST = 100; //Request code for LoginActivity
    private static final int LOGIN_FAILED = 0;  //resultCode for HomeActivity
    private static final int LOGIN_SUCCESSFUL = 1; //resultCode for HomeActivity
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //When user clicks "Login", this function will create the Activity and receive the user's account
    public void openLoginActivity(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);
    }

    //When the Login Activity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is return through the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_SUCCESSFUL) {
            if (resultCode == RESULT_OK) {
                //The user was logged in!
                //The intent will have pertinent information that needs to be passed back in it

                //TODO do something with the intent here
                // maybe save the account?
//                account = data.getAccount()
            }
        }
    }
}
