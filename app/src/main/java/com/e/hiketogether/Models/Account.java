package com.e.hiketogether.Models;

import android.os.Bundle;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * PURPOSE:
 *      This class will handle the data and the model. I think it should save things and be directly
 *      related and connected to the firebase database that we are setting up.
 */
// TODO Implement so that only one of these can be created... Singleton Model
public class Account {
    // VARIABLES
    private static final String TAG = "ACCOUNT";
    private String password;
    private String username;
    private String email;
    private Settings settings;
    private List<Integer> favTrails;                 //stores the ID values of favorite trails

    // Default Constructor
    public Account() {
        Log.d(TAG, "Creating empty account.");
        setUsername("");
        setPassword("");
        setEmail("");
        favTrails = new ArrayList<>();
        setSettings(new Settings());
    }

    // Creating a new account from the create account manager
    public Account(String username, String password, String email) {
        favTrails = new ArrayList<>();              // Set the trails to and empty list
        settings = new Settings();                  // Set new Settings
        setUsername(username);                      // Set the Username
        setEmail(email);                            // Set the email
        try {
            setPassword(hashPassword(password));    // Set the Password
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        Log.d(TAG, "Succesful creation of account.");
        Log.d(TAG, "Username: " + getUsername());
        Log.d(TAG, "Password: " + getPassword());
        Log.d(TAG, "Original Password: " + password);
        Log.d(TAG, "Email: " + getEmail());
    }

    // Construct account from a bundle
    public Account(Bundle bundle) {
        Log.d(TAG, "Setting account from bundle.");
        setUsername(bundle.getString("username"));
        setEmail(bundle.getString("email"));
        setPassword(bundle.getString("password"));
        setFavTrails(bundle.getIntegerArrayList("trails"));
        Bundle settingsBundle = bundle.getBundle("settings");
        setSettings(new Settings(settingsBundle));
    }

    // Constructor
    public Account(String username, String password, String email,
                   List<Integer> favTrails, Settings settings) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setFavTrails(favTrails);
        setSettings(settings);
    }

    // Translates the bytes to Hexadecimal
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Hashes the password to hash form for save storage on web server
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest; {
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw e;
            }
        }

        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        password = bytesToHex(encodedhash);
        return password;
    }

    // ADD a Trail to the accounts favTrails
    public void addTrail(Integer trailID) throws Exception {
        // Try to add a trail
            if (favTrails == null)
                favTrails = new ArrayList<>();
        if (favTrails.add(trailID))
            Log.d(TAG, "Successfully added a Favorites Trail.");
        else
            throw new Exception("Failed to add Trail to Favorites");
    }

    public void removeTrail(Integer trailID) throws Exception{
        if (favTrails.remove(trailID))
            Log.d(TAG, "Successfully removed a Favorites Trail.");
        else
            throw new Exception("Failed to remove trail to favorites");
    }

    // Bundle the account to sent to activity
    public Bundle bundleAccount() {
        Bundle extra = new Bundle();
        extra.putString("username", getUsername());
        extra.putString("password", getPassword());
        extra.putString("email", getEmail());
        extra.putIntegerArrayList("favoriteTrails", (ArrayList<Integer>) getFavTrails());
        extra.putBundle("settings", getSettings().toBundle());
        return extra;
    }

    // Getter functions
    public String getPassword()         { return password;  }
    public String getUsername()         { return username;  }
    public String getEmail()            { return email;     }
    public Settings getSettings()       { return settings;  }
    public List<Integer> getFavTrails() { return favTrails; }

    // Setter Functions
    public void setPassword(String password)          { this.password = password;   }
    public void setUsername(String username)          { this.username = username;   }
    public void setEmail(String email)                { this.email = email;         }
    public void setSettings(Settings settings)        { this.settings = settings;   }
    public void setFavTrails(List<Integer> trailList) { this.favTrails = trailList; }
}
