package com.e.hiketogether.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.e.hiketogether.Presenters.LoginManager;
import com.e.hiketogether.R;

/**
 * PURPOSE:
 *      Display the login activity for the user and communicate with the LoginManager to know what
 *      should and should not be executed and when to the user.
 */
public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // After the user has entered the username and password then we need to find there account
    public void onLogin(View view) {
        // Get the info from username and password fields
        EditText text = view.findViewById(R.id.usernameInput);
        username = text.getText().toString();
        text = view.findViewById(R.id.passwordInput);
        password = text.getText().toString();

        // Each time we Login we need to search a new account and therefore need a new login manager
        // TODO onDestroy() make sure that the manager is DELETED
        // Send information to Manager
        LoginManager loginManager = new LoginManager(username, password);

        // Get information back from LoginManager and create HomeActivity with it
        // TODO Create the HomeActivity with specific stuff
        loginManager.findAccount();
    }

    // Start the CreateAccountActivity to create a personal account
    public void onCreateAccount(View view) {
        startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
    }

    // Skip logging in and create a Standard home screen
    public void onSkip(View view) {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO destroy LoginManager Object

    }
}
