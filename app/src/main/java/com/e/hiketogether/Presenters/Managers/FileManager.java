package com.e.hiketogether.Presenters.Managers;

import android.content.Context;

import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Helpers.FileHelper;
import com.google.gson.Gson;

import java.util.List;

//This class manages the cache of data in the file.
public class FileManager implements Runnable {
    // VARIABLES
    public static final String TAG = "FILE_MANAGER";
    public static final int MAX_CACHE_SIZE = 250;

    private FileHelper fHelper;
    private TrailList currentCache;
    private TrailList updateCache;
    private String filename = "trail_cache.txt";
    private Gson gson;
    private List<Integer> duplicates;

    // Constructor
    public FileManager(Context context, TrailList updateCache) {
        //Create our file helper with the context of whatever wants to write
        fHelper = new FileHelper(context);
        gson = new Gson();

        //Get the trail list stored in the cache
        currentCache = gson.fromJson(fHelper.readFile(filename), TrailList.class);

        //Store the trails we want to cache in here
        this.updateCache = updateCache;
    }

    // Gets current cache
    public TrailList getCurrentCache() {
        return currentCache;
    }


    //This will see which trails from the desired file are already cached
    //Check each trail with stored trails, calculate which are redundant
    private void compareCache() {
        //TODO- Improve the search function to faster speeds
        //Loop through new cache
        for (int i = 0; i < updateCache.getTrailList().size(); i++) {
            //loop through old cache
            for (int j = 0; j < currentCache.getTrailList().size(); j++) {
                //If they are the same trail, add to list of duplicates
                if (updateCache.getTrail(i).compare(currentCache.getTrail(j)))
                    duplicates.add(j);
            }
        }
    }

    //We only want to be able to store around 250 trails at a time
    //So this will pop the oldest trails off the front of the trail list
    //and maintain whatever size we choose
    private void limitSize() {
        //Remove each of the duplicate trails from the new trail list
        for (int i = 0; i < duplicates.size(); i++)
            updateCache.removeTrail(i);
    }

    //Since FileHelper overwrites all old data, we need to combine these
    //two cache's together
    private void createNewCache() {
        for (int i = 0; i < updateCache.getTrailList().size(); i++)
            currentCache.addTrail(updateCache.getTrail(i));
    }


    //Once we've performed all comparisons, we can cache the data
    private void cacheData() {
        fHelper.writeFile(filename, gson.toJson(currentCache));
    }

    //This function can be called after the constructor to automatically
    //write to the cache on the background thread
    @Override
    public void run() {
        //First see what duplicated
        compareCache();

        //Make sure we don't exceed our limit of trails
        if (currentCache.getTrailList().size() + updateCache.getTrailList().size() > MAX_CACHE_SIZE)
            limitSize();

        //Since FileHelper overwrites all old data, we need to combine these
        //two cache's together
        createNewCache();

        //Now that we've made sure we have space, add the trails to the cache!
        cacheData();
    }
}
