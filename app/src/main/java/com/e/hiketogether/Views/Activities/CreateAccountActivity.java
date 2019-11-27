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
import com.e.hiketogether.Presenters.Managers.CreateAccountManager;
import com.e.hiketogether.R;

/**
 * PURPOSE
 *      Intent is to present the user with a user friendly display and allow them to create a
 *      personal account that will allow them to do special things with the account.
 *      After creating an account it will send them to the login page.
 */
public class CreateAccountActivity extends AppCompatActivity {
    // VARIABLES
    private static final String TAG = "CREATE_ACCOUNT_ACTIVITY";
    private ProgressBar progressBar;
    private String secondPassword;
    private EditText editText;
    private String password;
    private String username;
    private String email;

    // OnCreate Function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        progressBar = findViewById(R.id.createAccountProgressBar);
        progressBar.bringToFront();
        hideProgessBar();
    }

    // Takes input and creates account!
    public void onCreateAccount(View view) {
        // Need some managing of accounts happening
        CreateAccountManager accountManager = new CreateAccountManager(this);

        // Set editText to username
        editText = findViewById(R.id.createUsername);
        // Check to make sure username is within constraints
        try {
            accountManager.checkUsername(editText, "Username");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        editText.setError(null);
        // Set username
        username = editText.getText().toString();

        // point to the first password
        editText = findViewById(R.id.createPassword);
        // Check constraints
        try {
            accountManager.checkPassword(editText, "Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset setError
        editText.setError(null);
        // Set password
        password = editText.getText().toString();

        // point to verifyPassword
        editText = findViewById(R.id.createVerifyPassword);
        // Check constraints
        try {
            accountManager.checkPassword(editText, "Verify Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        editText.setError(null);
        // Set SecondPassword
        secondPassword = editText.getText().toString();

        // Set the edit text to email
        editText = findViewById(R.id.createEmail);
        // Check constraints
        try {
            accountManager.checkEmail(editText, "email");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        editText.setError(null);
        // Set email
        email = editText.getText().toString();

        // Check to make sure the two passwords match
        try {
            Log.d(TAG,"First password: " + password);
            Log.d(TAG, "Second password: " + secondPassword);
            accountManager.crossCheckPasswords(password, secondPassword);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            displayToast("Passwords do not match");
            return;
        }
        // reset the setError
        editText.setError(null);

        // Attempt to Create the account
        accountManager.createAccount(username, password, email);
        displayProgressBar();
    }

    // Display a toast
    public void displayToast(String message) {
        new Toast(getApplicationContext())
                .makeText(getApplicationContext(),message,Toast.LENGTH_LONG)
                .show();
    }

    // Hide Progress bar
    @SuppressLint("WrongConstant")
    public void hideProgessBar() {
        progressBar.setVisibility(8);
    }

    // Display Progress bar
    @SuppressLint("WrongConstant")
    public void displayProgressBar() {
        progressBar.setVisibility(0);
    }

    // Now that we created an account lets go HOME
    public void onSuccess(Account account) {
        //Account created successfully!  Return to LoginActivity with their data
        // Return the account info in the intent, so they can be logged in
        Intent returnIntent = new Intent();
        Bundle extra = account.bundleAccount();
        returnIntent.putExtra("account", extra);
        Log.d(TAG, "Bundle name is \'account\'");
        setResult(Activity.RESULT_OK, returnIntent);
        hideProgessBar();
        finish();
    }
}
