package com.e.hiketogether.Views.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

// TODO fix this "W/ManagedChannelImpl: [{0}] Failed to resolve name. status={1}"
//  after people log in this will sometimes show up it repeats.
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
    private EditText usernameView;
    private EditText passwordView;
    private Bundle account;
    private Button loginButton;
    private Button createAccountButton;
    private Button skipButton;
    private Button forgotUsernameButton;
    private Button forgotPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Send information to Manager
        loginManager = new LoginManager(this);
        progressBar = findViewById(R.id.loginProgressBar);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        skipButton = findViewById(R.id.skipButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        forgotUsernameButton = findViewById(R.id.forgotUsernameButton);
        forgotUsernameButton.setVisibility(View.INVISIBLE);
        forgotPasswordButton.setVisibility(View.INVISIBLE);
        hideProgressBar();
        passwordView = findViewById(R.id.passwordInput);
        usernameView = findViewById(R.id.usernameInput);
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

    // disable the screen so users cannot touch it and interact
    public void setTouchDisabled() {
        Log.d(TAG, "Setting the touch screen to: DISABLED");
        displayProgressBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    // Enable the screen so users can touch it and interact
    public void setTouchEnabled() {
        Log.d(TAG, "Setting the touch screen to: ENABLED");
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        hideProgressBar();
    }

    // After the user has entered the username and password then we need to find there account
    public void onLogin(View view) {
        setTouchDisabled();

        // Check to see if the username is empty
        try {
            loginManager.checkInput(usernameView, "Username");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            setTouchEnabled();
            return;
        }
        // reset the setError
        usernameView.setError(null);

        // Set the username
        username = usernameView.getText().toString();

        try {
            loginManager.checkInput(passwordView, "Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            setTouchEnabled();
            return;
        }
        // reset the setError
        passwordView.setError(null);

        // Set the password
        password = passwordView.getText().toString();

        // Get information back from LoginManager and return to the MainActivity
        try {
            loginManager.confirmAccount(username);
        } catch (Exception e) {
            Log.d(TAG, "Failed to find Account");
            displayToast("Account does not exist");
            setTouchEnabled();
            return;
        }
        // TODO make the keyboard disappear
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
        setTouchEnabled();
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
        setTouchEnabled();
        Log.d(TAG, "Login in from Create Account Screen.");
    }

    // Display progress bar
    public void displayProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    // Hide progress bar
    public void  hideProgressBar() {
        progressBar.setVisibility(View.GONE);
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

    public void setFocus(String view) {
        if (view == "password")
            passwordView.requestFocus();
        else if (view == "username")
            usernameView.requestFocus();
        else
            Log.d(TAG, "Unable to set focus");
    }

    // GETTERS
    public String getPassword() { return password; }
}
