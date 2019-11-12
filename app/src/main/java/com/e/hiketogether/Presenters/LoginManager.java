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
    private Account account;
    private LoginActivity activity;
    private String password;
    private String username;

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

    // Hashing Password Function RETURN SOMETHING HASHED
    private String hashPassword(String password) {
        // implement hashing algorithm
        String hashTemp = null;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashTemp = hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        // Display in the log just to make sure that it worked right!
        Log.i(TAG, hashTemp);

        return hashTemp;
    }

    // Confirm account with that in our database
    private boolean confirmPassword() {
        // Display in log to check and see if the hashes are the same
        Log.i(TAG, account.getHashedPassword());

        // Call hashing on password
        // Confirm hashed saved password with entered hash
        if (hashPassword(password) != account.getHashedPassword()){
            return false;
        }
        return true;
    }

    // Find account and confirm passwords
    public Account findAccount(String username) {
        // Load account from the Database
        return account.loadAccount();
    }

    // **OPTIONAL** helper function to reset password
    private void resetPassword() {

    }

    // **OPTIONAL** forgot password option
    public void forgotPassword(String email) {
        // TODO search for email in AccountList once found have user answer security question?
//        for (int i = 0; i < accountList.length(); i++) {
//            if(accountList.getEmail[i] == email)
//                resetPassword();
    }
}
