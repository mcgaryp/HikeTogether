package com.e.hiketogether.Presenters;

import android.text.TextUtils;
import android.widget.EditText;

import com.e.hiketogether.Models.Account;

/**
 * PURPOSE:
 *      This is to manage all of the logic that is needed manage an account.
 *      I, Porter have thought about merging this class with the login class...
 *      or implementing a hashing interface or something of the sort.
 */
public class AccountManager {
    // VARIABLES
    private String email;
    private String username;
    private String password;

    // Constructor
    public AccountManager() {

    }

    // Creates and saves an account in the database
    public void createAccount(String username, String password, String email) {
        // TODO hashing function to store the password as a hashed value
        Account account = new Account(password, username, email);
        // TODO think about storing a local account in cache??
        account.saveAccount();
    }

    // Checks to make sure that the password and verifying password match
    public boolean crossCheckPasswords(String p1, String p2) {
        // If the passwords are not the same return false
        if (p1 != p2) {
            return false;
        }
        // Else return true
        return true;
    }

    // Check to make sure the string is not empty
    // Takes in string to create error message
    public boolean checkInput(EditText editText, String error) {
        // Check that the string is not empty
        if (editText.toString().isEmpty()) {
            editText.setError("Enter " + error);
            return false;
        }

        // Check that the string is at least 8 char
        if (editText.toString().length() <= 7 ) {
            editText.setError(error + " must be at least 8 characters");
            return false;
        }

        // Check that the password has at least 1 number
        if (!editText.toString().matches("[^0-9]++ | [{Punct}]++")) {
            editText.setError(error + " must contain at least one number or symbol");
            return false;
        }

        return true;
    }
}
