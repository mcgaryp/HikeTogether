package com.e.hiketogether.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

/**
 * PURPOSE:
 *      This class will handle the data and the model. I think it should save things and be directly
 *      related and connected to the firebase database that we are setting up.
 */
public class Account {
    // VARIABLES
    private static final String TAG = "ACCOUNT";
    private String password;
    private String username;
    private TrailList trailList;
    private String email;
    private Settings settings;

    // Default Constructor
    public Account() {
        Log.d(TAG, "Created account Object with no set variables.");
    }

    // Optional Constructor for creating account
    public Account(String username, String password, String email) {
        this.username = username;
        this.password = hashPassword(password);
        this.email = email;
        trailList = new TrailList();
        settings = new Settings();
        Log.d(TAG, "Succesful creation of account.");
        Log.d(TAG, "Username: " + this.username);
        Log.d(TAG, "Hashed password: " + this.password);
        Log.d(TAG, "Password: " + password);
        Log.d(TAG, "Email: " + this.email);
    }

    // Hashing Password Function RETURN SOMETHING HASHED
    public String hashPassword(String password) {
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
        Log.d(TAG, "Successfully created hashed password");
        Log.d(TAG, "Hasehd password: " + hashTemp);

        return hashTemp;
    }

    // ADD a Trail to the accounts trail list
    public void addTrail() {
        // TODO add new trail to the account
        Log.d(TAG, "Successfully added a Favorites Trail.");
    }

    // Getter functions
    public String getPassword()     { return password;  }
    public String getUsername()     { return username;  }
    public String getEmail()        { return email;     }
    public Settings getSettings()   { return settings;  }
    public TrailList getTrailList() { return trailList; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTrailList(TrailList trailList) {
        this.trailList = trailList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
