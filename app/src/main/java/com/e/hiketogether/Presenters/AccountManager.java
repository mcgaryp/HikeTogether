package com.e.hiketogether.Presenters;

import com.e.hiketogether.Models.Account;

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
