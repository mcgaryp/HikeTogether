package com.e.hiketogether.Presenters.Managers;

import android.content.Context;
import android.util.Log;

import com.e.hiketogether.BuildConfig;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Helpers.TrailHTTPHelper;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;


public class TrailManager {
    //Hiking Project API Key
    private static final String KEY = "key=" + BuildConfig.HPKEY;
    private static final String TAG = "TRAIL_MANAGER";

    //Needed to store JSON data
    private TrailList tl;

    //Used to cache data, and return data from cache
    FileManager fManager;

    Context context;

    //Needed to request JSON data from HikingProject API
    private String apiURL = "https://www.hikingproject.com/data/";
    private String lat;
    private String lon;
    private String maxDistance = "maxDistance=30";          //TODO Could be something to change in settings
    private String maxResults;
    private String sort = "quality";
    private String minLength;
    private String minStars;


    // Getters
    public String getLon() { return lon; }
    public String getLat() { return lat; }
    public String getMaxDistance() { return maxDistance; }

    // Setters
    public void setTL(TrailList tl) { this.tl = tl; }
    public void setLat(double lat) { this.lat = "lat=" + lat; }
    public void setLon(double lon) { this.lon = "lon=" + lon; }
    public void setMaxDistance(int maxDistance) { this.maxDistance = "maxDistance=" + maxDistance; }
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

    // Requests the url on a background thread
    private String requestURL(String url) throws ExecutionException, InterruptedException {
            return new TrailHTTPHelper().execute(url).get();
    }

    //Gets trails through the latitude and longitude
    // TODO having trouble reading the json? or maybe just cant connect to www.hikingproject.com
    //  we may need to add either a listener or exception so that this is done on a thread and that
    //  notify's the user that it was complete....progressbar?
    public TrailList getTrails() {
        String url = apiURL + "get-trails?" + lat + "&" + lon + "&" + maxDistance + "&" + KEY;
        String tlJson = null;
        try {
            tlJson = requestURL(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Log.d("TRAIL_MANAGER", tlJson);
        return gson.fromJson(tlJson, TrailList.class);
    }


    //Gets the trails by ID
    public TrailList getTrailsById() {
        String url = apiURL + "get-trails-by-id?ids=" + "&" + KEY;
        String tlJson = null;
        try {
            tlJson = requestURL(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        return gson.fromJson(tlJson, TrailList.class);
    }

    //Gets the conditions of the trails
    public TrailList getConditions() {
        String url = apiURL + "get-conditions?ids=" + "&" + KEY;
        String tlJson = null;
        try {
            tlJson = requestURL(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        return gson.fromJson(tlJson, TrailList.class);
    }
}
