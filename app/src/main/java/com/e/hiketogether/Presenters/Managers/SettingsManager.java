package com.e.hiketogether.Presenters.Managers;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.Presenters.Helpers.FireBaseHelper;
import com.e.hiketogether.Presenters.Interfaces.UpdateAccountListener;
import com.e.hiketogether.Views.Fragments.SettingsFragment;
import com.e.hiketogether.Views.SpecializedViews.UniqueEditText;

import java.security.NoSuchAlgorithmException;

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
    public void setClick(final Button button, final UniqueEditText view,
                          final String data) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Attempting to change the " + data + " of user");
                // Call changeState method to know what needs to be done
                view.changeState(button);
                // Send new data to settings to be saved
                if (view.getChange()) {
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
    public void setClick(final Button button, final UniqueEditText password,
                         final UniqueEditText verify, final RelativeLayout layout) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Look at the state of the button
                if (!password.getChange()) {
                    // User just tried to save... lets make sure the input is correct
                    try {
                        checkPassword(password, "Password");
                        checkPassword(verify, "Verify Password");
                        crossCheckPasswords(password.getText().toString(), verify.getText().toString());
                    } catch (Exception e) {
                        // The user didn't enter something properly
                        Log.d(TAG, e.getMessage());
                        // Let them know what went wrong and don't change our state by returning
                        getFragment().displayToast(e.getMessage());
                        return;
                    }
                    // Hash the password
                    String newPassword = password.getText().toString();
                    try {
                        newPassword = getAccount().hashPassword(newPassword);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        getFragment().displayToast("Failed to Save");
                        return;
                    }
                    getAccount().setPassword(newPassword);
                    // update account
                    new FireBaseHelper(getAccount().getUsername()).updateAccount("password", newPassword, new UpdateAccountListener() {
                        @Override
                        public void onSuccess() {
                            // Set the right text
                            button.setText("Change Password");
                            // Hide the password layout
                            layout.setVisibility(View.GONE);
                            // Change our state because everything checked out!
                            password.changeState(button);
                            getFragment().displayToast("Saved");
                        }

                        @Override
                        public void onFail() {
                            getFragment().displayToast("Failed to Save");
                        }
                    });
                }
                else {
                    layout.setVisibility(View.VISIBLE);
                    password.changeState(button);
                }
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

    // Change the Password
    public void changePassword(String password) {
        getAccount().setPassword(password);
        Log.d(TAG, "Changing the password: " + password);
    }

    // Checks to make sure that the password and verifying password match
    private void crossCheckPasswords(String p1, String p2) throws Exception{
        if (!p1.matches(p2)) {
            getFragment().setFocus("verifyPassword");
            throw new Exception("Passwords do not match.");
        }
    }

    // Check to make sure the string is not empty and other checks
    private void checkPassword(UniqueEditText editText, String error) throws Exception {
        // Check that the string is not empty
        try {
            checkInput(editText, error);
        } catch (Exception e) {
            if (error == "Password")
                getFragment().setFocus("password");
            else
                getFragment().setFocus("verifyPassword");
            throw e;
        }

        // Check that the string is at least 8 char
        if (editText.getText().toString().length() < 8 ) {
            editText.setError(error + " must be at least 8 characters");
            if (error == "Password")
                getFragment().setFocus("password");
            else
                getFragment().setFocus("verifyPassword");
            throw new Exception("Password has less than 8 characters.");
        }

        // Check that the password has at least 1 number
        if (!editText.getText().toString().matches(".*[\\d\\p{Punct}].*")) {
            editText.setError(error + " must contain at least one number or symbol");
            if (error == "Password")
                getFragment().setFocus("password");
            else
                getFragment().setFocus("verifyPassword");
            throw new Exception("Password does not have a symbol.");
        }
    }

    // Checks input to make sure its not empty
    private void checkInput(UniqueEditText editText, String error) throws Exception {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Empty " + error + " field.");
            throw new Exception("Empty " + error + " field.");
        }
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
