package com.e.hiketogether.Presenters.Managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private String query;
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

    public TrailManager(String lat, String lon, Context context) {
        this.lat = "lat=" + lat;
        this.lon = "lon=" + lon;
        this.context = context;

        fManager = new FileManager(context);
    }

    //Simply checks if the app has access to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Requests the trail, either from the cache or from the internet
    private String requestTrails(String url) throws ExecutionException, InterruptedException {

        //See if the trail query is currently cached
        if (!fManager.isCacheNull() && fManager.isQueryCached(url)) {
                Log.d(TAG, "Retrieved query (" + url + ") from cache");
                Toast.makeText(context, "Retrieved trails from cache", Toast.LENGTH_LONG).show();
                return fManager.getCachedQuery(url);
        }
        //It wasn't cached, get it from the internet and cache it, then return it for the recycler view to use
        else if (isNetworkAvailable()) {
            Log.d(TAG, "Successfully connected to the internet");
            String trailList = new TrailHTTPHelper().execute(url).get();

            //Add new trail list to the cache
            fManager.addToCache(url, trailList);
            Log.d(TAG, "Added trails to the cache");

            //Write the cache to the phone since we updated it
            fManager.writeCache(trailList);

            return trailList;
        }
        //We couldn't connect to the internet.  Return an error
        else {
            Log.d(TAG, "Failed to connect to the internet");
            return null;
        }
    }

    //Gets trails through the latitude and longitude
    public TrailList getTrails() {
        String url = apiURL + "get-trails?" + lat + "&" + lon + "&" + maxDistance + "&" + KEY;
        String tlJson = null;
        try {
            tlJson = requestTrails(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        if (tlJson != null) {
            Log.d(TAG, tlJson);
            return gson.fromJson(tlJson, TrailList.class);
        }
        else {
            Log.d(TAG, "Incoming JSON string is null.  No trails available");
            Toast.makeText(context, "No trails could be retrieved.  Please check your internet connection and try again", Toast.LENGTH_LONG).show();
            return new TrailList();
        }
    }


    //Gets the trails by ID
    public TrailList getTrailsById() {
        String url = apiURL + "get-trails-by-id?ids=" + "&" + KEY;
        String tlJson = null;
        try {
            tlJson = requestTrails(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        if (tlJson != null) {
            Log.d(TAG, tlJson);
            return gson.fromJson(tlJson, TrailList.class);
        }
        else {
            Log.d(TAG, "Incoming JSON string is null.  No trails available");
            Toast.makeText(context, "No trails could be retrieved. Please check your internet connection and try again", Toast.LENGTH_LONG).show();
            return new TrailList();
        }
    }

    //Gets the conditions of the trails
    public TrailList getConditions() {
        String url = apiURL + "get-conditions?ids=" + "&" + KEY;
        String tlJson = null;
        try {
            tlJson = requestTrails(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        if (tlJson != null) {
            Log.d(TAG, tlJson);
            return gson.fromJson(tlJson, TrailList.class);
        }
        else {
            Log.d(TAG, "Incoming JSON string is null.  No trail conditions available");
            Toast.makeText(context, "No trail conditions could be retrieved. Please check your internet connection and try again", Toast.LENGTH_LONG).show();
            return new TrailList();
        }
    }
}
