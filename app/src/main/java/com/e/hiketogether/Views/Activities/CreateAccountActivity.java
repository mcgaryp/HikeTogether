package com.e.hiketogether.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Interfaces.interact;
import com.e.hiketogether.Presenters.Managers.CreateAccountManager;
import com.e.hiketogether.R;

/**
 * PURPOSE
 *      Intent is to present the user with a user friendly display and allow them to create a
 *      personal account that will allow them to do special things with the account.
 *      After creating an account it will send them to the login page.
 */
public class CreateAccountActivity extends AppCompatActivity implements interact {
    // VARIABLES
    private static final String TAG = "CREATE_ACCOUNT_ACTIVITY";
    private ProgressBar progressBar;
    private String verifyPassword;
    private String password;
    private String username;
    private String email;
    private EditText usernameView;
    private EditText passwordView;
    private EditText verifyPasswordView;
    private EditText emailView;


    // OnCreate Function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        progressBar = findViewById(R.id.createAccountProgressBar);
        progressBar.bringToFront();
        hideProgressBar();
        usernameView = findViewById(R.id.createUsername);
        passwordView = findViewById(R.id.createPassword);
        verifyPasswordView = findViewById(R.id.createVerifyPassword);
        emailView = findViewById(R.id.createEmail);
    }

    // Takes input and creates account!
    public void onCreateAccount(View view) {
        setTouchDisabled();
        // Need some managing of accounts happening
        CreateAccountManager accountManager = new CreateAccountManager(this);

        // Check to make sure username is within constraints
        try {
            accountManager.checkUsername(usernameView, "Username");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        usernameView.setError(null);
        // Set username
        username = usernameView.getText().toString();

        // Check constraints
        try {
            accountManager.checkPassword(passwordView, "Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset setError
        passwordView.setError(null);
        // Set password
        password = passwordView.getText().toString();

        // Check constraints
        try {
            accountManager.checkPassword(verifyPasswordView, "Verify Password");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        verifyPasswordView.setError(null);
        // Set SecondPassword
        verifyPassword = verifyPasswordView.getText().toString();

        // Check constraints
        try {
            accountManager.checkEmail(emailView, "email");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return;
        }
        // reset the setError
        emailView.setError(null);
        // Set email
        email = emailView.getText().toString();

        // Check to make sure the two passwords match
        try {
            Log.d(TAG,"First password: " + password);
            Log.d(TAG, "Second password: " + verifyPassword);
            accountManager.crossCheckPasswords(password, verifyPassword);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            displayToast("Passwords do not match");
            return;
        }

        // Attempt to Create the account
        accountManager.createAccount(username, password, email);
//        setTouchEnabled();
    }

    // Display a toast
    public void displayToast(String message) {
        new Toast(getApplicationContext())
                .makeText(getApplicationContext(),message,Toast.LENGTH_LONG)
                .show();
    }

    // Hide Progress bar

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTouchDisabled() {
        Log.d(TAG, "Setting the touch screen to: DISABLED");
        displayProgressBar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void setTouchEnabled() {
        Log.d(TAG, "Setting the touch screen to: ENABLED");
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        hideProgressBar();
    }

    // Display Progress bar
    public void displayProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
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
        setTouchEnabled();
        finish();
    }

    public void setFocus(String view) {
        if (view == "username")
            usernameView.requestFocus();
        else if (view == "password")
            passwordView.requestFocus();
        else if (view == "verifyPassword")
            verifyPasswordView.requestFocus();
        else if (view == "email")
            emailView.requestFocus();
        else
            Log.d(TAG, "Unable to set focus");
    }
}
