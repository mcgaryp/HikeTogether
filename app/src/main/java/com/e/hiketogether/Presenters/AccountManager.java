package com.e.hiketogether.Presenters;

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
        account.saveAccount();
    }

    // Checks to make sure that the password and verifying password match
    public Boolean crossCheckPasswords(String p1, String p2) {
        // If the passwords are the same return true
        if (p1 == p2)
            return true;
        // Else return false
        return false;
    }
}
