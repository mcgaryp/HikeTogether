package com.e.hiketogether.Views.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    // VARIABLES
    private static final String TAG = "LOGIN_ACTIVITY"; //Log tag

    private static final int LOGIN_FAILED = 0;                  //resultCode for MainActivity
    private static final int LOGIN_SUCCESSFUL = 1;              //resultCode for MainActivity

    private static final int CREATE_ACCOUNT_REQUEST = 200;      //requestCode

    private LoginManager loginManager;
    private ProgressBar progressBar;
    private String username;
    private String password;
    private EditText text;
    private Bundle account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Send information to Manager
        loginManager = new LoginManager(this);
        progressBar = findViewById(R.id.loginProgressBar);
        progressBar.bringToFront();
        hideProgressBar();
    }

    // Start the CreateAccountActivity to create a personal account
    public void openCreateAccountActivity(View view) {
        Intent loginIntent = new Intent(this, CreateAccountActivity.class);
        startActivityForResult(loginIntent, CREATE_ACCOUNT_REQUEST);
    }

    // BUTON ONCLICKS
    // Return an empty account
    public void onSkip(View view) {
        setLoginSuccessful(new Account());
    }

    // TODO The user forgot their username!!
    @SuppressLint("ResourceType")
    public void onForgotUsername(View view) {
//        setContentView(R.id.forgot_username);
    }

    // TODO The user forgot their password!!
    @SuppressLint("ResourceType")
    public void onForgotPassword(View view) {
//        setContentView(R.id.forgot_password);
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

        // Get information back from LoginManager and return to the MainActivity
        try {
            loginManager.confirmAccount(username);
            displayProgressBar();
        } catch (Exception e) {
            Log.d(TAG, "Failed to find Account");
            displayToast("Account does not exist");
            return;
        }
    }

    // This will be called from the manager to let us know that the thread is complete.
    // We can login in now!
    public void setLoginSuccessful(Account account) {
        // They logged in!  Return to MainActivity with their data
        Intent returnIntent = new Intent();
        returnIntent.putExtra("requestCode", LOGIN_SUCCESSFUL);
        // Sent the account info to the MainActivity in extra in the intent
        Bundle extras = account.bundleAccount();
        returnIntent.putExtra("account", extras);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        hideProgressBar();
        Log.d(TAG, "Login in from Login Screen.");
    }

    // Set the login for account on creation of a new account
    public void setLoginSuccessful(Bundle account) {
        // They logged in!  Return to MainActivity with their data
        Intent returnIntent = new Intent();
        // Sent the account info to the MainActivity in extra in the intent
        returnIntent.putExtra("account", account);
        setResult(RESULT_OK, returnIntent);
        finish();
        hideProgressBar();
        Log.d(TAG, "Login in from Create Account Screen.");
    }

    // Display progress bar
    @SuppressLint("WrongConstant")
    public void displayProgressBar() {
        progressBar.bringToFront();
        progressBar.setVisibility(0);
    }

    // Hide progress bar
    @SuppressLint("WrongConstant")
    public void  hideProgressBar() {
        progressBar.setVisibility(8);
    }

    // Display any general toast
    public void displayToast(String message) {
        new Toast(getApplicationContext()).makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    // When the CreateAccountActivity is closed, it will return information to this function
    // Two codes indicate whether the process was successful, and any important data
    // Is returned through the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "ON ACTIVITY RESULT");
        Log.d(TAG, "requestCode: " + requestCode + "\nresultCode: " + resultCode);
        if (requestCode == CREATE_ACCOUNT_REQUEST) {
            Log.d(TAG, "ON CREATION ACCOUNT SUCCESSFUL if statement");
            if (resultCode == RESULT_OK) {
                //The user's account was created!
                //The intent will have pertinent information that needs to be passed back in it
                Log.d(TAG,"Attempting to automatically login user");
                // do something with the intent here
                account = data.getBundleExtra("account");
                // Log the user in AUTOMATICALLY with the account they just created
                setLoginSuccessful(account);
            }
            if (resultCode == LOGIN_FAILED)
                displayToast("Login Failed While Attempting to Create Account");
        }
    }

    // GETTERS
    public String getPassword() { return password; }
}
