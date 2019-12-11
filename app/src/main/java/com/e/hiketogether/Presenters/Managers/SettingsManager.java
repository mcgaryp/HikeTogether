package com.e.hiketogether.Presenters.Managers;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.Presenters.Helpers.DisableEditText;
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
        setSettings(new Settings());
    }

    // Method for setting click for all the buttons
    public void setClick(final Button button, final DisableEditText view,
                          final String data) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data == "password")
                    return;
                Log.d(TAG, "Attempting to change the " + data + " of user");
                // Call changeState method to know what needs to be done
                view.changeState(button);
                // Send new data to settings to be saved
                if (view.getChange() == true) {
                    switch (data) {
                        case "first":
                            addFirstName(view.getText().toString());
                            // Check to see we saved it right.
                            Log.d(TAG, "Settings first name saved as: " +
                                    getSettings().getFirstName());
                            break;
                        case "last":
                            addLastName(view.getText().toString());
                            // Check to see we saved it right.
                            Log.d(TAG, "Settings last name saved as: " +
                                    getSettings().getLastName());
                            break;
                        case "username":
                            changeUsername(view.getText().toString());
                            // Check to see we saved it right.
                            Log.d(TAG, "Settings username saved as: " +
                                    getSettings().getUsername());
                            break;
                        case "email":
                            changeEmail(view.getText().toString());
                            // Check to see we saved it right.
                            Log.d(TAG, "Settings email saved as: " +
                                    getSettings().getEmail());
                            break;
                        default:
                            Log.d(TAG, "onClick function was called but " +
                                    "could not save to ");
                    }
                }
                // TODO Send to FirebaseStore as update

            }
        });
    }

    // TODO Set OnclickListener for password change button
    public void setClick(final Button button, final DisableEditText password,
                         final DisableEditText verify, final RelativeLayout layout) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Methods
            }
        });
    }

    // TODO Set OnClickListener for picture button
    public void setClick(final Button button, final ImageView image) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Methods
            }
        });
    }

    // Add Picture to Profile
    public void addPicture(String picture) {
        settings.setPicture(picture);
        Log.d(TAG, "Changed Profile Picture: " + picture);
    }

    // Add First Name to Profile
    public void addFirstName(String first) {
        settings.setFirstName(first);
        Log.d(TAG, "Added a first name to the account: " + first);
    }

    // Add Last Name to Profile
    public void addLastName(String last) {
        settings.setLastName(last);
        Log.d(TAG, "Added a last name to the account: " + last);
    }

    // Change Background Theme
    public void changeBackground(String background) {
        settings.setBackground(background);
        Log.d(TAG, "Changing the background: " + background);
    }

    // Change Distance of Search
    public void changeDistance(String distance) {
        settings.setDistance(distance);
        Log.d(TAG,"Changing the distance search: " + distance);
    }

    // Change the email
    public void changeEmail(String email) {
        settings.setEmail(email);
        Log.d(TAG, "Changing the email address: " + email);
    }

    // Change the Username
    public void changeUsername(String username) {
        settings.setUsername(username);
        Log.d(TAG, "Changing the username: " + username);
    }

    // Change the Password
    public void changePassword(String password) {
        settings.setPassword(password);
        Log.d(TAG, "Changing the password: " + password);
    }

    // GETTERS
    public Settings getSettings() { return settings; }
    public SettingsFragment getFragment() { return fragment; }

    // SETTERS
    public void setFragment(SettingsFragment fragment) { this.fragment = fragment; }
    public void setSettings(Settings settings) { this.settings = settings; }
}
