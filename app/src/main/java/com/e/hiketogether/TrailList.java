package applegate;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class TrailList {

    @SerializedName("trails")
    private List<Trail> trailList = new ArrayList<>();

    public List<Trail> getTrailList() {
        return trailList;
    }
}
