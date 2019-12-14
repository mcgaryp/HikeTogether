package com.e.hiketogether.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailList {
    // VARIABLES
    @SerializedName("trails")
    private List<Trail> trailList;
    private List<String> recentQueries;

    // Constructor
    public TrailList() {
        trailList = new ArrayList<>();
        recentQueries = new ArrayList<>();
    }

    // GETTERS
    public List<Trail> getTrailList() {
        return trailList;
    }
    public List<String> getRecentQueries() {
        return recentQueries;
    }
    public Trail getTrail(int index) {
        return trailList.get(index);
    }

    // ADDERS
    public void addTrail(Trail trail) {
        trailList.add(trail);
    }
    public void addQuery(String query) {
        recentQueries.add(query);
    }

    //Returns true if query was called recently
    public boolean findQuery(String query) {
        return recentQueries.contains(query);
    }

    // Remove a trail
    public void removeTrail(int index) { trailList.remove(index); }

    // Is the list null??
    public boolean isNullList() {
        return trailList == null;
    }

}
