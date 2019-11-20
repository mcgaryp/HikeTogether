package com.e.hiketogether.Models;

import com.google.gson.annotations.SerializedName;

public class Trail {

    public Trail(String name, String summary, String difficulty, float rating, int ratingVotes, String location, float length, String imgSmall) {
        this.name = name;
        this.summary = summary;
        this.difficulty = difficulty;
        this.rating = rating;
        this.ratingVotes = ratingVotes;
        this.location = location;
        this.length = length;
        this.imgSmall = imgSmall;
    }

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

    private float longitude;
    private float latitude;

    private String conditionStatus;
    private String conditionColor;
    private String conditionImg;
    private String conditionDetails;
    private String conditionDate;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public float getRating() {
        return rating;
    }

    public int getRatingVotes() {
        return ratingVotes;
    }

    public String getLocation() {
        return location;
    }

    public float getLength() {
        return length;
    }

    public String getUrl() {
        return url;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
