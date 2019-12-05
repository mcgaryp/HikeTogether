package com.e.hiketogether.Models;

import android.util.Log;

/**
 * PURPOSE
 *      This class will allow the user to personalize there account
 *      Reset the password and email.
 *      Add personal things like their name, a picture, and change the theme of their background.
 *      Other things as needed
 */
public class Settings {
    // VARIABLES
    private static final String TAG = "SETTINGS";
    private String picture;
    private String firstName;
    private String lastName;
    private String background;

    // Constructor
    Settings() {

    }

    // Add Picture to Profile
    public void addPicture(String picture) {
        // TODO on change listener
        setPicture(picture);
        Log.d(TAG, "Changed Profile Picture.");
    }

    // Add Name to Profile
    public void addName(String first, String last) {
        // TODO on change listener
        setFirstName(first);
        setLastName(last);
        Log.d(TAG, "Added a first and last name to the account.");
    }

    // Change Background Theme
    public void changeBackground(String background) {
        // TODO on change listener
        setBackground(background);
        Log.d(TAG, "Changing the background.");
    }

    // Setter functions
    public void setPicture(String picture)          { this.picture = picture;       }
    public void setLastName(String lastName)        { this.lastName = lastName;     }
    public void setFirstName(String firstName)      { this.firstName = firstName;   }
    public void setBackground(String background)    { this.background = background; }

    // Getter functions
    public String getPicture()      { return picture;    }
    public String getFirstName()    { return firstName;  }
    public String getLastName()     { return lastName;   }
    public String getBackground()   { return background; }
}
