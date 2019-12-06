package com.e.hiketogether.Models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TrailList {

    @SerializedName("trails")
    private List<Trail> trailList;
    private List<String> recentQueries;

    public TrailList() {
        trailList = new ArrayList<>();
        recentQueries = new ArrayList<>();
    }

    public List<Trail> getTrailList() {
        return trailList;
    }

    public List<String> getRecentQueries() {
        return recentQueries;
    }

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


    public Trail getTrail(int index) {
        return trailList.get(index);
    }

    public void removeTrail(int index) {
        trailList.remove(index);
    }
}
