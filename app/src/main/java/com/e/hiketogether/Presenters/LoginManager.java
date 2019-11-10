package com.e.hiketogether.Presenters;

import com.e.hiketogether.Views.Activities.LoginActivity;
import com.e.hiketogether.Models.Account;

public class LoginManager {
    // Variables
    private Account account;
    private LoginActivity activity;
    private String password;
    private String username;

    // Constructor
    public LoginManager(String username, String password) {
        this.password = password;
        this.username = username;
    }

    // Hashing Password Function RETURN SOMETHING HASHED
    private String hashPassword() {
        // TODO implement hashing algorithm
        return "";
    }

    // Confirm account with that in our database
    private Boolean confirmPassword() {
        // Call hashing on password
        String tempHash = hashPassword();
        // TODO confirm hashed saved password with entered hash
//        if (tempHash != account.password)
//            return false;

        return true;
    }

    // **OPTIONAL** forgot password option
    public void forgotPassword(String email) {

    }

    // Find account and confirm passwords
    public void findAccount() {
        // TODO search through the account and find account with same username
    }
}
