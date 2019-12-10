package com.e.hiketogether.Presenters.Managers;

import android.util.Log;

import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.Views.Fragments.SettingsFragment;

public class SettingsManager {
    // Static VARIABLES
    private static final String TAG = "SETTINGS_MANAGER";

    // VARIABLES
    private Settings settings;
    private SettingsFragment fragment;

    // Constructor
    public SettingsManager(SettingsFragment fragment) {
        setFragment(fragment);
    }

    // Add Picture to Profile
    public void addPicture(String picture) {
        // TODO On change listener
        settings.setPicture(picture);
        Log.d(TAG, "Changed Profile Picture.");
    }

    // Add First Name to Profile
    public void addFirstName(String first) {
        // TODO On change listener
        settings.setFirstName(first);
        Log.d(TAG, "Added a first name to the account.");
    }

    // Add Last Name to Profile
    public void addLastName(String last) {
        // TODO On change listener
        settings.setLastName(last);
        Log.d(TAG, "Added a last name to the account.");
    }

    // Change Background Theme
    public void changeBackground(String background) {
        // TODO On change listener
        settings.setBackground(background);
        Log.d(TAG, "Changing the background.");
    }

    // Change Distance of Search
    public void changeDistance(String distance) {
        // TODO On change listener
        settings.setDistance(distance);
        Log.d(TAG,"Changing the distance search.");
    }

    // Change the email
    public void changeEmail(String email) {
        // TODO On change listener
        settings.setEmail(email);
        Log.d(TAG, "Changing the email address.");
    }

    // Change the Username
    public void changeUsername(String username) {
        // TODO On change listener
        settings.setUsername(username);
        Log.d(TAG, "Changing the username.");
    }

    // Change the Password
    public void changePassword(String password) {
        // TODO On change listener
        settings.setPassword(password);
        Log.d(TAG, "Changing the password.");
    }

    // GETTERS
    public Settings getSettings() { return settings; }
    public SettingsFragment getFragment() { return fragment; }

    // SETTERS
    public void setFragment(SettingsFragment fragment) { this.fragment = fragment; }
    public void setSettings(Settings settings) { this.settings = settings; }
}
