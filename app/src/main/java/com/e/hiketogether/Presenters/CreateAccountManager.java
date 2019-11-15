package com.e.hiketogether.Presenters;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import com.e.hiketogether.Models.Account;

/**
 * PURPOSE:
 *      This is to manage all of the logic that is needed manage an account.
 *      I, Porter have thought about merging this class with the login class...
 *      or implementing a hashing interface or something of the sort.
 */
public class CreateAccountManager {
    // VARIABLES
    private static final String TAG = "CREATE_ACCOUNT_MANAGER";

    // Constructor
    public CreateAccountManager() {

    }

    // Creates and saves an account in the database
    public void createAccount(String username, String password, String email) {
        new FireBaseHelper(username).saveAccount(new Account(username,password,email));
    }

    // Checks to make sure that the password and verifying password match
    public void crossCheckPasswords(String p1, String p2) throws Exception{
        if (!p1.matches(p2))
            throw new Exception("Passwords do not match.");
    }

    // Check to make sure the string is not empty
    public void checkPassword(EditText editText, String error) throws Exception {
        // Check that the string is not empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            throw e;
        }

        // Check that the string is at least 8 char
        if (editText.getText().toString().length() < 8 ) {
            editText.setError(error + " must be at least 8 characters");
            throw new Exception("Password has less than 8 characters.");
        }

        // Check that the password has at least 1 number
        if (!editText.getText().toString().matches(".*[\\d\\p{Punct}].*")) {
            editText.setError(error + " must contain at least one number or symbol");
            throw new Exception("Password does not have a symbol.");
        }
    }

    // Checks to make sure the email is an email
    public void checkEmail(EditText editText, String error) throws Exception {
        // Check to see if empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            throw e;
        }

        // check to make sure it has at least '@' character
        if (!editText.getText().toString().matches(".*@.*")) {
            editText.setError(error + " invalid");
            throw new Exception("Email invalid.");
        }
    }

    // Check the username against all usernames in database so it's not repeated
    public void checkUsername(EditText editText, String error) throws Exception {
        // Check to see if empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            throw e;
        }

        // TODO Check to make sure the username is not already used
//        try {
//            new FireBaseHelper(editText.getText().toString()).exists();
//        } catch (Exception e) {
//            throw e;
//        }
        
    }

    // Checks input to make sure its not empty
    private void checkInput(EditText editText, String error) throws Exception {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Empty " + error + " field.");
            throw new Exception("Empty " + error + " field.");
        }
    }
}
