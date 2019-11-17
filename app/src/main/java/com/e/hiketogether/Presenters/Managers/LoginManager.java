package com.e.hiketogether.Presenters.Managers;

import android.util.Log;
import android.widget.EditText;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Helpers.FireBaseHelper;
import com.e.hiketogether.Presenters.Interfaces.FirebaseListener;
import com.e.hiketogether.Views.Activities.LoginActivity;

/**
 * PURPOSE:
 *      This class will handle the logic behind login into our database and create a unique
 *      environment for the apps users.
 */
public class LoginManager implements FirebaseListener {
    // Variables
    private static final String TAG = "LOGIN_MANAGER"; // Log TAG
    private LoginActivity activity;

    // Constructor
    public LoginManager(LoginActivity activity) {
        this.activity = activity;
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
        // Find the account
        findAccount(username);
    }

    // check to make sure the password matches with the one on file
    public void confirmPassword(Account account, String password) throws Exception {
        // Confirm passwords
        // TODO LOOKS like the hashing function is just not working right?
        Log.d(TAG, "Password on file: " + account.getPassword());
        Log.d(TAG, "Password by user: " + account.hashPassword(password));
        if (account.getPassword() != account.hashPassword(password))
            throw new Exception("Password is incorrect.");
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
        new FireBaseHelper(username, this).updateAccount("password", password);
    }

    // Search for account in dataBase
    private void findAccount(String username) {
        new FireBaseHelper(username, this).loadAccount();
    }

    // IMPLEMENTATIONS OF INTERFACE
    @Override
    public void onSaveSuccess() {
    }

    @Override
    public void onSaveFail() {
    }

    @Override
    public void onLoadSuccess(Account account) {
        Log.d(TAG, "We found the account!");
        try {
            Log.d(TAG, "Checking if passwords match.");
            confirmPassword(account, activity.getPassword());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            activity.toastFailedPasswords();
        }
        activity.setLoginSuccessful();
    }

    @Override
    public void onLoadFail() {
        Log.d(TAG, "We did not find the account!");
        activity.notify();
    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFail() {

    }

    @Override
    public void onUpdateSuccess() {

    }

    @Override
    public void onUpdateFail() {

    }
}
