package com.e.hiketogether.Models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import static java.lang.Character.isUpperCase;
import static java.lang.Character.toUpperCase;

public class Trail {
    // Static VARIABLES
    private final static String TAG = "TRAIL";

    private int id;
    private String name;
    private String summary;
    private String difficulty;

    @SerializedName("stars")
    private float rating;

    @SerializedName("starVotes")
    private int ratingVotes;

    private String location;
    private float length;
    private float ascent;
    private float descent;

    private String url;
    private String imgSmall;
    //private String img
    //We can add more image URL's as needed

    private float longitude;
    private float latitude;

    private String conditionStatus;
    private String conditionColor;
    private String conditionImg;
    private String conditionDetails;
    private String conditionDate;

    private boolean isFavorite;

    // Normalize difficulty
    private void normalizeDif() {
        Log.d(TAG, "Attempting to normalize difficulty: " + difficulty);
        String normalDif = "";
        for (int i = 0; i < difficulty.length(); i++) {
            if (isUpperCase(difficulty.toCharArray()[i])) {
                normalDif += " ";
            }
            if (i == 0)
                normalDif = String.valueOf(toUpperCase(difficulty.toCharArray()[i]));
            else
                normalDif = normalDif + difficulty.toCharArray()[i];
        }
        setDifficulty(normalDif);
        Log.d(TAG, "Normalized difficulty to: " + difficulty);
    }

    // GETTERS
    public int getId()                  { return id;               }
    public String getName()             { return name;             }
    public String getSummary()          { return summary;          }
    public float getRating()            { return rating;           }
    public int getRatingVotes()         { return ratingVotes;      }
    public String getLocation()         { return location;         }
    public float getLength()            { return length;           }
    public float getAscent()            { return ascent;           }
    public float getDescent()           { return descent;          }
    public String getUrl()              { return url;              }
    public String getImgSmall()         { return imgSmall;         }
    public float getLongitude()         { return longitude;        }
    public float getLatitude()          { return latitude;         }
    public String getConditionStatus()  { return conditionStatus;  }
    public String getConditionColor()   { return conditionColor;   }
    public String getConditionImg()     { return conditionImg;     }
    public String getConditionDetails() { return conditionDetails; }
    public String getConditionDate()    { return conditionDate;    }
    public boolean getFavorite()        { return isFavorite;       }
    public String getDifficulty()       {
        normalizeDif();
        return difficulty;
    }

    // SETTERS
    private void setDifficulty(String dif) { difficulty = dif; }
    public void setFavorite() {
        if (isFavorite)
            isFavorite = false;
        else if (!isFavorite)
            isFavorite = true;
    }


    // Compare the Trail to other Trails
    public boolean compare(Trail rhs) {
        if (this.id == rhs.id)
            return true;
        else
            return false;
    }
}
