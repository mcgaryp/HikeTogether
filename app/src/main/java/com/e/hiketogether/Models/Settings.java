package com.e.hiketogether.Models;

/**
 * PURPOSE
 *      This class will allow the user to personalize there account
 *      Reset the password and email.
 *      Add personal things like their name, a picture, and change the theme of their background.
 *      Other things as needed
 */
public class Settings extends Account {
    // VARIABLES
    private static final String TAG = "SETTINGS";
    private String picture;
    private String firstName;
    private String lastName;
    private String background;
    private String distance;

    // Constructor for account
    public Settings() {}

    // Constructor for the settingsFrag
    public Settings(String picture, String fName, String lName, String background, String distance) {
        setPicture(picture);
        setFirstName(fName);
        setLastName(lName);
        setBackground(background);
        setDistance(distance);
    }

    // Setter functions
    public void setPicture(String picture)          { this.picture = picture;       }
    public void setLastName(String lastName)        { this.lastName = lastName;     }
    public void setFirstName(String firstName)      { this.firstName = firstName;   }
    public void setBackground(String background)    { this.background = background; }
    public void setDistance(String distance)       { this.distance = distance;     }

    // Getter functions
    public String getPicture()      { return picture;    }
    public String getFirstName()    { return firstName;  }
    public String getLastName()     { return lastName;   }
    public String getBackground()   { return background; }
    public String getDistance()     { return distance;   }
}
