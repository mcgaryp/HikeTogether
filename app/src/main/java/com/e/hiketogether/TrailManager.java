package applegate;

import com.google.gson.Gson;

public class TrailManager {

    //Needed to store JSOn data
    private TrailList tl;

    //Fetches JSON data and returns it as a string
    private HTTPHelper helper = new HTTPHelper();

    //Needed to request JSON data from HikingProject API
    private String apiURL = "https://www.hikingproject.com/data/";
    private String lat = "lat=43.826069";
    private String lon = "lon=-111.789528";
    private String maxDistance = "maxDistance=30";
    private String maxResults;
    private String sort = "quality";
    private String minLength;
    private String minStars;
    private String key = "key=200628563-f10bd47da474cc11ccc357f4a540d8fe";


    public void setTL(TrailList tl) {
        this.tl = tl;
    }

    //Gets trails through the latitude and longitude
    public TrailList getTrails() {
        String url = apiURL + "get-trails?" + lat + "&" + lon + "&" + maxDistance + "&" + key;
        String tl = helper.readHTTP(url);

        Gson gson = new Gson();

        return gson.fromJson(tl, TrailList.class);
    }


    //Gets the trails by ID
    public TrailList getTrailsById() {
        String url = apiURL + "get-trails-by-id?ids=" + "&" + key;
        String tl = helper.readHTTP(url);

        Gson gson = new Gson();

        return gson.fromJson(tl, TrailList.class);
    }

    public void displayTrailList() {

        for (Trail t : tl.getTrailList()) {
            System.out.println("");
            t.displayTrail();
            System.out.println("");
            System.out.println("--------------------------------------");
        }
    }

    //Gets the conditions of the trails
    public TrailList getConditions() {
        String url = apiURL + "get-conditions?ids=" + "&" + key;
        String tl = helper.readHTTP(url);

        Gson gson = new Gson();

        return gson.fromJson(tl, TrailList.class);
    }
}
