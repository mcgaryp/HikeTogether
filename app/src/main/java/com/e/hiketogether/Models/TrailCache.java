package com.e.hiketogether.Models;

import java.util.HashMap;
import java.util.Map;

//This lets us store each list of trails according to the URL which requested them.
//This allows us to look up recent queries from local storage instead of the internet
public class TrailCache {
    private static final int CACHE_MAX_SIZE = 250;  //We don't want more TrailLists than this

    private Map<String, String> trailCache = new HashMap<>();

    public Map<String, String> getTrailCache() {
        return trailCache;
    }

    public void addtoCache(String query, String trailList) {
        limitSize();
        trailCache.put(query, trailList);
    }

    public boolean isRequestCached(String query) {
        return trailCache.get(query) != null;
    }

    public String getCachedQuery(String query) {
        return trailCache.get(query);
    }

    public boolean isEmpty() {
        return trailCache.size() == 0;
    }

    //We only want to be able to store around 250 trails at a time
    //So this will pop the oldest trails off the front of the trail list
    //and maintain whatever size we choose
    private void limitSize() {
        if (trailCache.size() > CACHE_MAX_SIZE) {
            trailCache.clear();
        }
    }

}
