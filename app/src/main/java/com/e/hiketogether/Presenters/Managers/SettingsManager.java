package com.e.hiketogether.Presenters.Managers;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.Views.SpecializedViews.DisableEditText;
import com.e.hiketogether.Presenters.Helpers.FireBaseHelper;
import com.e.hiketogether.Presenters.Interfaces.UpdateAccountListener;
import com.e.hiketogether.Views.Fragments.SettingsFragment;

public class SettingsManager {
    // Static VARIABLES
    private static final String TAG = "SETTINGS_MANAGER";

    // VARIABLES
    private Account account;
    private Settings settings;
    private SettingsFragment fragment;

    // Constructor
    public SettingsManager(SettingsFragment fragment) {
        setFragment(fragment);
        setAccount(fragment.getAccount());
        setSettings(getSettings());
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
                            // Send update to Firebase
                            if (account.getUsername() != null || account.getUsername() != "")
                                new FireBaseHelper(getAccount().getUsername())
                                        .updateAccount("settings",
                                                "firstName",
                                                getSettings().getFirstName(),
                                                new UpdateAccountListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(TAG, "Successful Update of First Name: "
                                                        + getSettings().getFirstName());
                                                getFragment().displayToast("Saved");
                                            }

                                            @Override
                                            public void onFail() {
                                                Log.d(TAG, "Failure to Update First Name.");
                                                getFragment().displayToast("Failed to Save");
                                            }
                                        });
                            else Log.d(TAG, "Account has not been created properly.");
                            break;
                        case "last":
                            addLastName(view.getText().toString());
                            // Check to see we saved it right.
                            Log.d(TAG, "Settings last name saved as: " +
                                    getSettings().getLastName());
                            // Send update to Firebase
                            if (account.getUsername() != null || account.getUsername() != "")
                                new FireBaseHelper(getAccount().getUsername())
                                        .updateAccount("settings","lastName",
                                                getSettings().getLastName(),
                                                new UpdateAccountListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(TAG, "Successful Update of Last Name: "
                                                        + getSettings().getLastName());
                                                getFragment().displayToast("Saved");
                                            }

                                            @Override
                                            public void onFail() {
                                                Log.d(TAG, "Failure to Update Last Name.");
                                                getFragment().displayToast("Failed to Save");
                                            }
                                        });
                            else Log.d(TAG, "Account has not been created properly.");
                            break;
                        case "username":
                            // TODO Look at the username by document not by just the account value.
                            //  change the document name Might have to copy the collection and
                            //  rename the collection then delete the old collection to be done in
                            //  firebase
//                            final String oldUsername = getAccount().getUsername();
//                            changeUsername(view.getText().toString());
//                            // Check to see we saved it right.
//                            Log.d(TAG, "Settings username saved as: " +
//                                    account.getUsername());
//                            // Send update to Firebase
//                            if (account.getUsername() != null || account.getUsername() != "")
//                                new FireBaseHelper(getAccount().getUsername()).exists(new UpdateAccountListener() {
//                                    @Override
//                                    public void onSuccess() {
//                                        new FireBaseHelper(oldUsername)
//                                                .updateAccount("username", getAccount().getUsername(), new UpdateAccountListener() {
//                                                    @Override
//                                                    public void onSuccess() {
//                                                        Log.d(TAG, "Successful Update of username: " + getAccount().getUsername());
//                                                        fragment.displayToast("Saved");
//                                                    }
//
//                                                    @Override
//                                                    public void onFail() {
//                                                        Log.d(TAG, "Failure to Update username.");
//                                                        fragment.displayToast("Failed to Save");
//                                                    }
//                                                });
//                                    }
//
//                                    @Override
//                                    public void onFail() {
//                                        Log.d(TAG, "Account name Already Exists");
//                                        fragment.displayToast("Username Already Exists");
//                                    }
//                                });
//                            else Log.d(TAG, "Account has not been created properly.");
                            break;
                        case "email":
                            changeEmail(view.getText().toString());
                            // Check to see we saved it right.
                            Log.d(TAG, "Settings email saved as: " +
                                    account.getEmail());
                            // Send update to Firebase
                            if (account.getUsername() != null || account.getUsername() != "")
                                new FireBaseHelper(getAccount().getUsername())
                                        .updateAccount("email", getAccount().getEmail(),
                                                new UpdateAccountListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(TAG, "Successful Update of email: " +
                                                        getAccount().getEmail());
                                                getFragment().displayToast("Saved");
                                            }

                                            @Override
                                            public void onFail() {
                                                Log.d(TAG, "Failure to Update email.");
                                                getFragment().displayToast("Failed to Save");
                                            }
                                        });
                            else Log.d(TAG, "Account has not been created properly.");
                            break;
                        default:
                            Log.d(TAG, "onClick function was called but " +
                                    "could not save to ");
                    }
                }
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
        getSettings().setPicture(picture);
        Log.d(TAG, "Changed Profile Picture: " + picture);
    }

    // Add First Name to Profile
    public void addFirstName(String first) {
        getSettings().setFirstName(first);
        Log.d(TAG, "Added a first name to the account: " + first);
    }

    // Add Last Name to Profile
    public void addLastName(String last) {
        getSettings().setLastName(last);
        Log.d(TAG, "Added a last name to the account: " + last);
    }

    // Change Background Theme
    public void changeBackground(String background) {
        getSettings().setBackground(background);
        Log.d(TAG, "Changing the background: " + background);
    }

    // Change Distance of Search
    public void changeDistance(String distance) {
        getSettings().setDistance(distance);
        Log.d(TAG,"Changing the distance search: " + distance);
    }

    // Change the email
    public void changeEmail(String email) {
        getAccount().setEmail(email);
        Log.d(TAG, "Changing the email address: " + email);
    }

    // Change the Username
    public void changeUsername(String username) {
        getAccount().setUsername(username);
        Log.d(TAG, "Changing the username: " + username);
    }

    // Change the Password
    public void changePassword(String password) {
        getAccount().setPassword(password);
        Log.d(TAG, "Changing the password: " + password);
    }

    // GETTERS
    public Settings getSettings()         { return getAccount().getSettings(); }
    public Account getAccount()           { return account;  }
    public SettingsFragment getFragment() { return fragment; }

    // SETTERS
    public void setFragment(SettingsFragment fragment) { this.fragment = fragment; }
    public void setAccount(Account account)            { this.account = account;   }
    public void setSettings(Settings settings)         { this.settings = settings; }
}
