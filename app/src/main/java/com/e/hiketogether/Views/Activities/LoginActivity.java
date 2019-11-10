package com.e.hiketogether.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.e.hiketogether.Presenters.LoginManager;
import com.e.hiketogether.R;

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
        loginManager.findAccount();

        // TODO Create the HomeActivity with specific stuff

    }

    // Skip logging in and create a Standard home screen
    // TODO Implement onSkip()
    public void onSkip(View view) {
        // TODO Skip to HomeActivity
        // Create normal standards for home activity WHAT ARE THEY?

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO destroy LoginManager Object

    }
}
