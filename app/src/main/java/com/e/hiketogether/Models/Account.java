package com.e.hiketogether.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

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
    private String email;
    private Settings settings;
    private Encryption encrypter;
    private Gson gson;
    private SealedObject sealedPassword;
    private KeyPair key;
    private List<Integer> favTrails;                                //stores the ID values of favorite trails

    // Default Constructor
    public  Account() {}

    // Idea is to create this with a new account from loading from the cloud
    public Account(KeyPair key) {
        setKey(key);                                                // Not sure this is needed value...
        encrypter.setMyPair(key);                                   // Setting the value of the key in the encrypter
        Log.d(TAG, "Created account Object with a key.");
    }

    // Creating a new account from the create account manager
    public Account(String username, String password, String email) {
        setUsername(username);          // Set the Username
        setPassword(password);          // Set the Password
        setEmail(email);                // Set the email
        settings = new Settings();      // Set new Settings
        favTrails = new ArrayList<>();  // Set the trails to and empty list

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

          // Encryption Function to call the encrypter for the password safe storage
//        public String encryptPassword(String password) {
//        try {
//            sealedPassword = encrypter.encrypt(password);
//            String gsonString = gson.toJson(sealedPassword);
//            Log.d(TAG, "Gson String of password SealedObject: " + gsonString);
//            return gsonString;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, e.getMessage());
//            return null;
//        }
//    }

    // Decrypt the Sealed object to a password we can compare to the user input
    public String decryptPassword(SealedObject myPassword) {
        try {
            Log.d(TAG, "Attempting to Decrypt Password.");
            return encrypter.decrypt(myPassword);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to decrypt Password.");
        }
        return null;
    }

    // ADD a Trail to the accounts favTrails
    public void addTrail(Integer trailID) throws Exception {
        // Try to add a trail
        if (favTrails.add(trailID))
            Log.d(TAG, "Successfully added a Favorites Trail.");
        else
            throw new Exception("Failed to add Trail to Favorites");
    }

    // Getter functions
    public String getPassword()             { return password;       }
    public String getUsername()             { return username;       }
    public String getEmail()                { return email;          }
    public Settings getSettings()           { return settings;       }
    public List<Integer> getFavTrails()     { return favTrails;      }
    public KeyPair getKey()                 { return key;            }
    public SealedObject getSealedPassword() { return sealedPassword; }

    // Setter Functions
    public void setPassword(String password)                    { this.password = password;             }
    public void setUsername(String username)                    { this.username = username;             }
    public void setEmail(String email)                          { this.email = email;                   }
    public void setSettings(Settings settings)                  { this.settings = settings;             }
    public void setFavTrails(List<Integer> trailList)           { this.favTrails = trailList;           }
    public void setKey(KeyPair key)                             { this.key = key;                       }
    public void setSealedPassword(SealedObject sealedPassword)  { this.sealedPassword = sealedPassword; }
}

//        // The old implementation fo the hashing algorithem... it didn't work
//        // because the hashing was never the same when you created a new account....

//        String hashTemp = null;
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
//            messageDigest.update(salt);
//            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
//            hashTemp = hashedPassword.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        // Display in the log just to make sure that it worked right!
//        Log.d(TAG, "Successfully created hashed password");
//        Log.d(TAG, "Hasehd password: " + hashTemp);
//        return hashTemp;