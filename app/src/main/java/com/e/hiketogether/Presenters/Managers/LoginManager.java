package com.e.hiketogether.Presenters.Managers;

import android.util.Log;
import android.widget.EditText;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Helpers.FireBaseHelper;
import com.e.hiketogether.Presenters.Interfaces.LoadAccountListener;
import com.e.hiketogether.Presenters.Interfaces.UpdateAccountListener;
import com.e.hiketogether.Views.Activities.LoginActivity;

/**
 * PURPOSE:
 *      This class will handle the logic behind login into our database and
 *      send the user to their own unique environment.
 */
public class LoginManager {
    // VARIABLES
    private static final String TAG = "LOGIN_MANAGER"; // Log TAG
    private LoginActivity activity;

    // Constructor
    public LoginManager(LoginActivity activity) {
        setActivity(activity);
    }

    // Check the input to make sure it's not empty
    public void checkInput(EditText editText, String error) throws Exception {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(error + " field is empty");
            throw new Exception(error + " field is empty");
        }
    }

    // Confirm account and confirm passwords
    public void confirmAccount(String username) {
        new FireBaseHelper(username).loadAccount(new LoadAccountListener() {
            @Override
            public void onSuccess(Account account) {
                Log.d(TAG, "We found the account!");
                try {
                    Log.d(TAG, "Checking if passwords match.");
                    confirmPassword(account, account.hashPassword(activity.getPassword()));
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    activity.setTouchEnabled();
                    activity.setFocus("password");
                    return;
                }
                // Send account to activity
                activity.setLoginSuccessful(account);
            }

            @Override
            public void onFail() {
                Log.d(TAG, "We did not find the account!");
                activity.displayToast("Account not Found");
                activity.setFocus("username");
                activity.setTouchEnabled();
            }
        });
    }

    // check to make sure the password matches with the one on
    public void confirmPassword(Account account, String password) throws Exception {
        // Confirm passwords
        Log.d(TAG, "Password on file: " + account.getPassword());
        Log.d(TAG, "Password by user: " + password);
        if (!account.getPassword().matches(password)) {
            activity.displayToast("Password Invalid!");
            throw new Exception("Password is incorrect.");
        }
    }

    // **OPTIONAL** forgot password option
    public void forgotPassword(String email) {
        // Firebase Firestore search for account with that username and password?
//      Account account = findAccount(email);???
        // Prompt to reset & Verify new password

        // Check password

        // Reset the password
//      resetPassword(account.getUsername(), account.getPassword);???
    }

    // **OPTIONAL** helper function to reset password
    private void resetPassword(String username, String password) {
        new FireBaseHelper(username).updateAccount("password", password, new UpdateAccountListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }
        });
    }

    // Setter Functions
    private void setActivity(LoginActivity activity) { this.activity = activity; }
}
