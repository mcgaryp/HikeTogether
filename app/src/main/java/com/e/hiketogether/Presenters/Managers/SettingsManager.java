package com.e.hiketogether.Presenters.Managers;

import android.os.Bundle;

import com.e.hiketogether.Views.Fragments.SettingsFragment;

public class SettingsManager {
    // Static VARIABLES
    private static final String TAG = "SETTINGS_MANAGER";

    // VARIABLES
    private Bundle account;
    private SettingsFragment fragment;
    private String firstName;
    private String lastName;
    private String background;

    // Constructor
    public SettingsManager(SettingsFragment fragment) {
        setFragment(fragment);
    }

    // Setters
    public void setFragment(SettingsFragment fragment) { this.fragment = fragment; }

    // Getters
    public SettingsFragment getFragment() { return fragment; }
}
