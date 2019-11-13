package com.e.hiketogether.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.Presenters.CreateAccountManager;
import com.e.hiketogether.R;

/**
 * PURPOSE OF THE CLASS
 *      Intent is to present the user with a user friendly display and allow them to create a
 *      personal account that will allow them to do special things with the account.
 *      After creating an account it will send them to the login page.
 */
public class CreateAccountActivity extends AppCompatActivity {
    // Variables
    private static final String TAG = "CREATE_ACCOUNT_ACTIVITY";
    private String password;
    private String username;
    private String email;
    private EditText editText;
    private String secondPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    // Takes input and creates account!
    public void onCreateAccount(View view) {
        // Need some managing of accounts happening
        CreateAccountManager accountManager = new CreateAccountManager();

        // Get the passwords to check them by
        editText = findViewById(R.id.createPassword);

        // Check constraints
        try {
            accountManager.checkPassword(editText, "Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // Set password to not empty string
        password = editText.toString();

        // Set the edit to next password
        editText = findViewById(R.id.createVerifyPassword);
        // Check constraints
        try {
            accountManager.checkPassword(editText, "Verify Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // Set password
        secondPassword = editText.toString();

        // Check to make sure the two passwords match
        try {
            accountManager.crossCheckPasswords(password, secondPassword);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }

        // Okay it's alright to create the account now.
        // Set editText to username
        editText = findViewById(R.id.createUsername);
        // Check to make sure username is within constraints
        try {
            accountManager.checkUsername(editText, "username");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // Set username
        username = editText.toString();

        // Set the edit text to email
        editText = findViewById(R.id.createEmail);
        // Check to make sure email is within constraints
        try {
            accountManager.checkEmail(editText, "email");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // Set email
        email = editText.toString();

        // Create the account
        accountManager.createAccount(username, password, email);

        // Send the user to the login activity
        // TODO destroy this activity.
        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
    }
}
