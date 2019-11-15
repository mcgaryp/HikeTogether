package com.e.hiketogether.Presenters;

import android.util.Log;
import android.widget.EditText;

import com.e.hiketogether.Views.Activities.LoginActivity;
import com.e.hiketogether.Models.Account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * PURPOSE:
 *      This class will handle the logic behind login into our database and create a unique
 *      environment for the apps users.
 */
public class LoginManager {
    // Variables
    private static final String TAG = "LOGIN_MANAGER"; //Log tag

    // Constructor
    public LoginManager() {

    }

    // Check the input to make sure it's not empty
    public void checkInput(EditText editText, String error) throws Exception {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(error + " field is empty");
            throw new Exception(error + " field is empty");
        }
    }

    // Confirm passwordsare the same with that in our database
    private void confirmPassword(String p1, String p2) throws Exception {
        if (p1 != p2) {
            throw new Exception("Passwords do not match");
        }
    }

    // Search for account in dataBase
    private Account findAccount(String username) {
        return new FireBaseHelper(username).loadAccount();
    }

    // Confirm account and confirm passwords
    public void confirmAccount(String username, String password) throws Exception {
        // Start by creating an account
        Account account;
        // Find the account
        try {
            account = findAccount(username);
        } catch (Exception e) {
            throw e;
        }

        // Confirm the passwords are the same
        try {
            // Confirm passwords
            confirmPassword(account.getPassword(), account.hashPassword(password));
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            // TODO Send notification to user that password was not found with toast

            throw e;
        }
    }

    // **OPTIONAL** helper function to reset password
    private void resetPassword() {

    }

    // **OPTIONAL** forgot password option
    public void forgotPassword(String email) {

    }
}
