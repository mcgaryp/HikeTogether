package com.e.hiketogether.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.Presenters.LoginManager;
import com.e.hiketogether.R;

/**
 * PURPOSE:
 *      Display the login activity for the user and communicate with the LoginManager to know what
 *      should and should not be executed and when to the user.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN_ACTIVITY";
    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // After the user has entered the username and password then we need to find there account
    public void onLogin(View view) {
        // Send information to Manager
        LoginManager loginManager = new LoginManager();

        // Point to Username input
        EditText text = findViewById(R.id.usernameInput);
        // Check to see if the username is empty
        try {
            loginManager.checkInput(text, "Username");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            new Toast(getApplicationContext()).makeText(getApplicationContext(),"Enter Username", Toast.LENGTH_SHORT);
            return;
        }
        // Set the username
        username = text.getText().toString();

        // Point to the password
        text = findViewById(R.id.passwordInput);
        try {
            loginManager.checkInput(text, "Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            new Toast(getApplicationContext()).makeText(getApplicationContext(),"Enter Password", Toast.LENGTH_SHORT);
            return;
        }
        // Set the password
        password = text.getText().toString();

        // Get information back from LoginManager and create HomeActivity with it
        try {
            loginManager.confirmAccount(username, password);
        } catch (Exception e) {
            Log.d(TAG, "Failed to find Account");
            new Toast(getApplicationContext()).makeText(getApplicationContext(),"Account does not exist", Toast.LENGTH_SHORT);
            return;
        }
        // Start the activity since they logged in!
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    // Start the CreateAccountActivity to create a personal account
    public void onCreateAccount(View view) {
        startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
    }

    // Skip logging in and create a Standard home screen
    public void onSkip(View view) {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }
}
