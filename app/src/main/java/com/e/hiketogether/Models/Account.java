package com.e.hiketogether.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.security.KeyPair;

import javax.crypto.SealedObject;

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
    private Encryption encryptor;
    private Gson gson;
    private SealedObject sealedPassword;
    private KeyPair key;

    // Default Constructor
    public  Account() {}
    public Account(KeyPair key) {
        setKey(key);
        encryptor.setMyPair(key);
        Log.d(TAG, "Created account Object with a key.");
    }

    // Optional Constructor for creating account
    public Account(String username, String password, String email) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        trailList = new TrailList();
        settings = new Settings();
//        encryptor = new Encryption();
//        try {
//            setSealedPassword(encryptor.encrypt(password));
//            key = encryptor.getMyPair();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, e.getMessage());
//        }
        Log.d(TAG, "Succesful creation of account.");
        Log.d(TAG, "Username: " + getUsername());
        Log.d(TAG, "Password: " + getPassword());
        Log.d(TAG, "Email: " + getEmail());
//        Log.d(TAG, "SealedPasword: " + getSealedPassword());
    }

    // Hashing Password Function RETURN SOMETHING HASHED
//    public String encryptPassword(String password) {
//        try {
//            sealedPassword = encryptor.encrypt(password);
//            String gsonString = gson.toJson(sealedPassword);
//            Log.d(TAG, "Gson String of password SealedObject: " + gsonString);
//            return gsonString;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, e.getMessage());
//            return null;
//        }
//    }

    // Decrypt the object
    public String decryptPassword(SealedObject myPassword) {
        try {
            Log.d(TAG, "Attempting to Decrypt Password.");
            return encryptor.decrypt(myPassword);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to decrypt Password.");
        }
        return null;
    }

    // ADD a Trail to the accounts trail list
    public void addTrail() {
        // TODO add new trail to the account
        Log.d(TAG, "Successfully added a Favorites Trail.");
    }

    // Getter functions
    public String getPassword()             { return password;       }
    public SealedObject getSealedPassword() { return sealedPassword; }
    public String getUsername()             { return username;       }
    public String getEmail()                { return email;          }
    public Settings getSettings()           { return settings;       }
    public TrailList getTrailList()         { return trailList;      }
    public KeyPair getKey()                 { return key;            }

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

    public void setSealedPassword(SealedObject sealedPassword) {
        this.sealedPassword = sealedPassword;
    }

    public void setKey(KeyPair key) { this.key = key; }
}

//
//        // implement hashing algorithm
//        String hashTemp = null;
//
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
//            messageDigest.update(salt);
//            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
//            hashTemp = hashedPassword.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        // Display in the log just to make sure that it worked right!
//        Log.d(TAG, "Successfully created hashed password");
//        Log.d(TAG, "Hasehd password: " + hashTemp);
//
//        return hashTemp;