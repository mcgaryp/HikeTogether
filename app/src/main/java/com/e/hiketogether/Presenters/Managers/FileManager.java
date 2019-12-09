package com.e.hiketogether.Presenters.Managers;

import android.content.Context;
import android.util.Log;

import com.e.hiketogether.Models.TrailCache;
import com.e.hiketogether.Presenters.Helpers.FileHelper;
import com.google.gson.Gson;

import java.io.IOException;

//This class manages the cache of data in the file.
public class FileManager {
    public static final String TAG = "FILE_MANAGER";

    private FileHelper fHelper;
    private TrailCache currentCache;

    FileManager(Context context) {
        //Create our file helper with the context of whatever wants to write
            try {
                fHelper = new FileHelper(context);
            } catch (IOException e) {
                Log.d(TAG, "FileManager failed while initializing the FileHelper");
                e.printStackTrace();
            }

        //Get the trail list stored in the cache
        Gson gson = new Gson();
        currentCache = new TrailCache();

        //Log.d(TAG, "Empty cache found when initializing FileManager");
        currentCache = gson.fromJson(fHelper.readFile(), TrailCache.class);
    }

    boolean isQueryCached(String query) {
        if (currentCache.isEmpty())
            return false;

        Log.d(TAG, "Checking cache for query: " + query);
       return currentCache.isRequestCached(query);
    }

    //This returns a TrailList in Json format
    String getCachedQuery(String query) {
        return currentCache.getCachedQuery(query);
    }

    void addToCache(String query, String trailList) {
        Log.d(TAG, "Adding (" + query + ") to the cache");
        currentCache.addtoCache(query, trailList);
    }

    boolean isCacheEmpty() throws Exception {
        if (currentCache != null)
            return currentCache.isEmpty();
        else
            throw new Exception("Cache is null object");
    }


    public void writeCache(String newTL) {
        Gson gson = new Gson();

        Log.d(TAG, "Writing new cache to the phone...");
        fHelper.writeFile(gson.toJson(currentCache.getTrailCache()));
    }


}
