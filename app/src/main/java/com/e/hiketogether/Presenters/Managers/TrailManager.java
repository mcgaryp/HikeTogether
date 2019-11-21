package com.e.hiketogether.Presenters.Managers;

import android.util.Log;

import com.e.hiketogether.Models.Trail;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Helpers.HTTPHelper;
import com.google.gson.Gson;

public class TrailManager {

    //Needed to store JSOn data
    private TrailList tl;

    //Fetches JSON data and returns it as a string
    private HTTPHelper helper = new HTTPHelper();

    //Needed to request JSON data from HikingProject API
    private String apiURL = "https://www.hikingproject.com/data/";
    private String lat;
    private String lon ;
    private String maxDistance = "maxDistance=30";
    private String maxResults;
    private String sort = "quality";
    private String minLength;
    private String minStars;
    private String key = "key=SECRET";


    public void setTL(TrailList tl) {
        this.tl = tl;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public void setMinStars(String minStars) {
        this.minStars = minStars;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //Gets trails through the latitude and longitude
    public TrailList getTrails() {
        String url = apiURL + "get-trails?" + lat + "&" + lon + "&" + maxDistance + "&" + key;
        String tl = helper.readHTTP(url);

        Gson gson = new Gson();
        Log.d("TRAIL_MANAGER", tl);
        return gson.fromJson(tl, TrailList.class);
    }


    //Gets the trails by ID
    public TrailList getTrailsById() {
        String url = apiURL + "get-trails-by-id?ids=" + "&" + key;
        String tl = helper.readHTTP(url);

        Gson gson = new Gson();

        return gson.fromJson(tl, TrailList.class);
    }

    //Gets the conditions of the trails
    public TrailList getConditions() {
        String url = apiURL + "get-conditions?ids=" + "&" + key;
        String tl = helper.readHTTP(url);

        Gson gson = new Gson();

        return gson.fromJson(tl, TrailList.class);
    }
}
