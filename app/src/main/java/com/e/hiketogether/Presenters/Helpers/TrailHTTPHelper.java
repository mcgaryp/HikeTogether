package com.e.hiketogether.Presenters.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

    /**
     * Helper class to send HTTP requests (the url) and receive responses.  Example use of the class:
     *
     * TrailHTTPHelper http = new TrailHTTPHelper();
     * String result = http.readHTTP("http://api.open-notify.org/iss-now.json");
     *
     * Note that the URL provided can also have parameters including keys.
     */
    public class TrailHTTPHelper extends AsyncTask<String, Void, String> {
        // VARIABLES
        private static String TAG = "TRAIL_HTTP_HELPER";

        /**
         * Will send the HTTP GET Request per the URL provided and listen for the the response.
         */
        @Override
        protected String doInBackground(String... strings) {
            try {
                String url = strings[0];
                // Convert the String url to a URL object which will allow a connection to be established
                URL urlObj = new URL(url);

                Log.d(TAG, "Initiating URL connection...");
                // Open a Connection to the remote site.  This will send the HTTP Get request
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

                // Open an input stream to read the response.  I prefer the BufferedReader since it provides
                // the readLine function.
                Log.d(TAG, "Reading Response...");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Log.d(TAG, "Got this far");
                // When concatenating strings read from the remote server, we get some performance improvement by
                // using the StringBuilder class.
                StringBuilder data = new StringBuilder();
                String line;

                // Keep reading from the remote server until we get a null which indicates the connection has
                // been closed.
                Log.d(TAG, "Converting response to string...");
                do {
                    line = reader.readLine();  // Read the next line
                    if (line != null) {
                        data.append(line); // Append will concatenate the new string just read into the StringBuilder
                    }
                }
                while (line != null);
                reader.close();

                Log.d(TAG, "Returning response.");
                // Convert the StringBuilder into a regular String for use by the calling function
                return data.toString();
            }
            catch (IOException ioe) {
                Log.d(TAG,"ERROR: " + ioe);
                return null;
            }
        }
    }
