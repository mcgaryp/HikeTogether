package applegate;

import com.google.gson.annotations.SerializedName;

public class Trail {

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

    public void displayTrail() {
        System.out.println("Trail: " + name + " (" + id + ")");
        System.out.println("Summary:" + summary);
        System.out.println("Difficulty: " + difficulty);
        System.out.println(rating + " stars (" + ratingVotes + " votes)");
        System.out.println("Location: " + location);
        System.out.println("Length: " + length);
        System.out.println("Ascent: " + ascent);
        System.out.println("Descent: " + descent);
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Trail Condition: " + conditionStatus + " (" + conditionColor + ")");
        System.out.println("Details: " + conditionDetails);
        System.out.println("As of: " + conditionDate);
    }
}
