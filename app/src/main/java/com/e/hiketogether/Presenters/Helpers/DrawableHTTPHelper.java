package com.e.hiketogether.Presenters.Helpers;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//This class parses the URL for a trail's image and converts it to a drawable
public class DrawableHTTPHelper extends AsyncTask<String, Void, Drawable> {
    //VARIABLES
    public static final String TAG = "DRAWABLE_HTTP_HELPER";

    // Do this in the background!
    @Override
    protected Drawable doInBackground(String... strings) {
        String url = strings[0];

        try {
            InputStream iStream = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(iStream, "HikingProject");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
