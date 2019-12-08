package com.e.hiketogether.Presenters.Helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper{
    // VARIABLES
    public static final String TAG = "FILE_HELPER";
    private Context context;

    // Constructor
    public FileHelper(Context context) {
        this.context = context;
    }

    //Return the file data in JSON format, as a string
    public String readFile(String filename) {
        try {
            BufferedReader reader;
            Log.d(TAG, "Attempting to read from file: " + filename);
            reader = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
            String data = reader.readLine();
            reader.close();
            Log.d(TAG, "Finished reading from file: " + filename);
            return data;
        } catch (IOException ioe) {
            Log.e(TAG, "Error reading from file (" + filename + "): " + ioe);
            return null;
        }
    }

    //Takes JSON data in a string and writes it to a file
    public boolean writeFile(String filename, String data) {
        try {
            Log.d(TAG, "Attempting to write to file: " + filename);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE)));
            writer.write(data);
            writer.close();
            Log.d(TAG, "Finished writing to file: " + filename);
            return true;
        }
        catch (IOException ioe) {
            Log.e(TAG, "Error writing to file (" + filename + "): " + ioe);
            return false;
        }
    }
}
