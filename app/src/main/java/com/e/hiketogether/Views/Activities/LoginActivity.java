package com.e.hiketogether.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Managers.LoginManager;
import com.e.hiketogether.R;

/**
 * PURPOSE:
 *      Display the login activity for the user and communicate with the LoginManager to know what
 *      should and should not be executed and when to the user.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN_ACTIVITY"; //Log tag

    private static final int LOGIN_FAILED = 0;  //resultCode for HomeActivity
    private static final int LOGIN_SUCCESSFUL = 1; //resultCode for HomeActivity

    private static final int CREATE_ACCOUNT_REQUEST = 200; //requestCode
    private static final int ACCOUNT_CREATION_FAILED = 0; //resultCode
    private static final int ACCOUNT_CREATION_SUCCESSFUL = 1; //resultCode

    private String username;
    private String password;
    private LoginManager loginManager;
    private EditText text;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Send information to Manager
        loginManager = new LoginManager(this);
    }

    // After the user has entered the username and password then we need to find there account
    public void onLogin(View view) {
        // Point to Username input
        text = findViewById(R.id.usernameInput);
        // Check to see if the username is empty
        try {
            loginManager.checkInput(text, "Username");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        text.setError(null);

        // Set the username
        username = text.getText().toString();

        // Point to the password
        text = findViewById(R.id.passwordInput);
        try {
            loginManager.checkInput(text, "Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        text.setError(null);
        // Set the password
        password = text.getText().toString();

        // Get information back from LoginManager and return to the HomeActivity
        try {
            loginManager.confirmAccount(username);
        } catch (Exception e) {
            Log.d(TAG, "Failed to find Account");
            new Toast(getApplicationContext()).makeText(getApplicationContext(), "Account does not exist", Toast.LENGTH_LONG).show();
            return;
        }
    }

    // TODO confirm the passwords match in the account.
    // Something changed
    public void setLoginSuccessful() {
        //They logged in!  Return to HomeActivity with their data
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", LOGIN_SUCCESSFUL);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void toastFailedPasswords() {
        new Toast(getApplicationContext()).makeText(getApplicationContext(), "Invalid password.", Toast.LENGTH_LONG).show();
    }
    // Something with the listener has to happen here and a notify action


    // Start the CreateAccountActivity to create a personal account
    public void openCreateAccountActivity(View view) {
        Intent loginIntent = new Intent(this, CreateAccountActivity.class);
        startActivityForResult(loginIntent, CREATE_ACCOUNT_REQUEST);
    }

    //When the CreateAccountActivity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is returned through the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACCOUNT_CREATION_SUCCESSFUL) {
            if (resultCode == RESULT_OK) {
                //The user's account was created!
                //The intent will have pertinent information that needs to be passed back in it

                //TODO- do something with the intent here
                //TODO- Log the user in AUTOMATICALLY with the account they just created
                //TODO- Return that info to the HomeActivity, the same as if they had logged in normally

            }
        }
    }

    // GETTERS
    public String getPassword() { return password; }
}
