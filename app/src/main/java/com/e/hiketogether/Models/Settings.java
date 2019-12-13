package com.e.hiketogether.Models;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.Map;

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
    private String distance;
    private ImageView profilePicture;

    // Constructor for account
    public Settings() {
        setFirstName("");
        setLastName("");
        setBackground("blueBackground");
        setDistance("10");
        setProfilePicture(null);
        Log.d(TAG, "Created settings with empty everything.");
    }

    // Constructor for the settingsFrag
    public Settings(Bundle bundle) {
//        setProfilePicture(bundle.getImageView("picture"));
        setFirstName(bundle.getString("firstName"));
        setLastName(bundle.getString("lastName"));
        setBackground(bundle.getString("background"));
        setDistance(bundle.getString("distance"));
        Log.d(TAG, "Created settings with Bundle constructor");
    }

    // Constructor for individual
    public Settings(String firstName, String lastName, String distance, String background,
                    ImageView profilePicture) {
        setFirstName(firstName);
        setLastName(lastName);
        setDistance(distance);
        setBackground(background);
        setProfilePicture(profilePicture);
        Log.d(TAG,"Created settings with all specific individual variables");
    }

    // Constructor for map helper
    public Settings(Map<String, String> map) {
//        setProfilePicture(map.get("picture"));
        setFirstName(map.get("firstName"));
        setLastName(map.get("lastName"));
        setBackground(map.get("background"));
        setDistance(map.get("distance"));
    }

    // Setter functions
    public void setPicture(String picture)           { this.picture = picture;        }
    public void setLastName(String lastName)         { this.lastName = lastName;      }
    public void setFirstName(String firstName)       { this.firstName = firstName;    }
    public void setBackground(String background)     { this.background = background;  }
    public void setDistance(String distance)         { this.distance = distance;      }
    public void setProfilePicture(ImageView picture) { this.profilePicture = picture; }

    // Getter functions
    public String getPicture()           { return picture;        }
    public String getFirstName()         { return firstName;      }
    public String getLastName()          { return lastName;       }
    public String getBackground()        { return background;     }
    public String getDistance()          { return distance;       }
    public ImageView getProfilePicture() { return profilePicture; }

    // Creates a bundle
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("firstName", getFirstName());
        bundle.putString("lastName", getLastName());
        bundle.putString("background", getBackground());
        bundle.putString("distance", getDistance());
        // TODO Set the imageView in the bundle
//        bundle.putImageView("profilePicture", getProfilePicture());
        return bundle;
    }
}
