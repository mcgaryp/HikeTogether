package com.e.hiketogether.Presenters.Managers;

import android.util.Log;
import android.widget.EditText;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Helpers.FireBaseHelper;
import com.e.hiketogether.Presenters.Interfaces.SaveAccountListener;
import com.e.hiketogether.Views.Activities.CreateAccountActivity;

/**
 * PURPOSE:
 *      This class will manage the basic logic and logistics when a user is creating and account
 *      in our database. It will send an intent to the calling activity with information like the
 *      users account that was created in the data base and then tell the activity to end.
 */
public class CreateAccountManager {
    // VARIABLES
    private static final String TAG = "CREATE_ACCOUNT_MANAGER";
    private CreateAccountActivity activity;

    // Constructor
    public CreateAccountManager(CreateAccountActivity activity) {
        setActivity(activity);
    }

    // Creates and saves an account in the database
    public void createAccount(String username, String password, String email) {
        new FireBaseHelper(username).exists(new Account(username,password,email), new SaveAccountListener() {
            @Override
            public void onSuccess(Account account) {
                Log.d(TAG,"");
                activity.displayToast("Account Created!");
                activity.onSuccess(account);
            }

            @Override
            public void onFail() {
                activity.displayToast("Username Already Taken.");
                activity.setFocus("username");
                activity.setTouchEnabled();
            }
        });
        //saveAccount(new Account(username,password,email));
    }

    // Checks to make sure that the password and verifying password match
    public void crossCheckPasswords(String p1, String p2) throws Exception{
        if (!p1.matches(p2)) {
            activity.setFocus("verifyPassword");
            throw new Exception("Passwords do not match.");
        }
    }

    // Check to make sure the string is not empty
    public void checkPassword(EditText editText, String error) throws Exception {
        // Check that the string is not empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            if (error == "Password")
                activity.setFocus("password");
            else
                activity.setFocus("verifyPassword");
            throw e;
        }

        // Check that the string is at least 8 char
        if (editText.getText().toString().length() < 8 ) {
            editText.setError(error + " must be at least 8 characters");
            if (error == "Password")
                activity.setFocus("password");
            else
                activity.setFocus("verifyPassword");
            throw new Exception("Password has less than 8 characters.");
        }

        // Check that the password has at least 1 number
        if (!editText.getText().toString().matches(".*[\\d\\p{Punct}].*")) {
            editText.setError(error + " must contain at least one number or symbol");
            if (error == "Password")
                activity.setFocus("password");
            else
                activity.setFocus("verifyPassword");
            throw new Exception("Password does not have a symbol.");
        }
    }

    // Checks to make sure the email is an email
    public void checkEmail(EditText editText, String error) throws Exception {
        // Check to see if empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            activity.setFocus("email");
            throw e;
        }

        // check to make sure it has at least '@' character
        if (!editText.getText().toString().matches(".*@.*")) {
            editText.setError(error + " invalid");
            activity.setFocus("email");
            throw new Exception("Email invalid.");
        }
    }

    // Check the username against all usernames in database so it's not repeated
    public void checkUsername(EditText editText, String error) throws Exception {
        // Check to see if empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            activity.setFocus("username");
            throw e;
        }
    }

    // Checks input to make sure its not empty
    private void checkInput(EditText editText, String error) throws Exception {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Empty " + error + " field.");
            throw new Exception("Empty " + error + " field.");
        }
    }


    // Setter Functions
    private void setActivity(CreateAccountActivity activity) { this.activity = activity; }
}
