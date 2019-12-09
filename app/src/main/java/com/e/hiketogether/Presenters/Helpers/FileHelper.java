package com.e.hiketogether.Presenters.Helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper{
    // VARIABLES
    public static final String TAG = "FILE_HELPER";

    private Context context;
    private String filename;

    public FileHelper(Context context) throws IOException {
        this.context = context;
        filename = context.getFilesDir() + "/trailCache.txt";

        //Make sure that the cache file exists before we try to read/write from it
        checkFileStatus();
    }

    //See if the file exists.  If it doesn't, create it.  If it does, do nothing.
    private void checkFileStatus() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            Log.d(TAG, "File (" + filename + ") was not found.  Attempting to create it...");
            new FileOutputStream(filename, true).close();
            //file.createNewFile();
            Log.d(TAG, "Finished creating file: " + filename);
        } else
            Log.d(TAG, "File (" + filename + ") already exists.");

    }

    //Return the file data in JSON format, as a string
    public String readFile() {
        //ATTEMPT ONE
//        try {
//            BufferedReader reader;
//            Log.d(TAG, "Attempting to read from file: " + filename);
//            reader = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
//            String data = reader.readLine();
//            reader.close();
//            Log.d(TAG, "Finished reading from file: " + filename);
//            return data;
//        } catch (IOException ioe) {
//            Log.e(TAG, "Error reading from file (" + filename + "): " + ioe);
//            return null;
//        }

        //ATTEMPT TWO
//        String result = "";
//
//        try {
//            InputStream iStream = context.openFileInput(filename);
//
//            if (iStream != null) {
//                InputStreamReader iStreamReader = new InputStreamReader(iStream);
//                BufferedReader bufferedReader = new BufferedReader(iStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ((receiveString = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(receiveString);
//                }
//
//                iStream.close();
//                result = stringBuilder.toString();
//            }
//        } catch (FileNotFoundException e)Ada {
//            Log.e(TAG, "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e(TAG, "Can't read file:" + e.toString());
//        }
//        return result;

        //ATTEMPT THREE
        try (FileInputStream reader = new FileInputStream(filename)){
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = reader.read()) != -1)
                sb.append((char)ch);

            Log.d(TAG, "File (" + filename + ") read successfully");
            Log.d(TAG, sb.toString());
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Can't read file:" + e.toString());
            return null;
        }
    }


    //Takes JSON data in a string and writes it to a file
    public boolean writeFile(String data) {
        //Attempt 1
//            try {
//                Log.d(TAG, "Attempting to write to file: " + filename);
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE)));
//                writer.write(data);
//                writer.close();
//                Log.d(TAG, "Finished writing to file: " + filename);
//                return true;
//            }
//            catch (IOException ioe) {
//                Log.e(TAG, "Error writing to file (" + filename + "): " + ioe);
//                return false;
//            }
//        }

        //Attempt 2
//        try {
//            OutputStreamWriter oStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
//            oStreamWriter.write(data);
//            oStreamWriter.close();
//        } catch (FileNotFoundException e) {
//            Log.e(TAG, "File not found: " + e.toString());
//            return false;
//        } catch (IOException e) {
//            Log.e(TAG, "Can't read file:" + e.toString());
//            return false;
//        }
//        return true;
//    }

        //Attempt 3
        try (FileOutputStream writer = new FileOutputStream(filename)) {
            byte[] bytes = data.getBytes();

            writer.write(bytes);
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "File not found: " + e.toString());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Can't read file:" + e.toString());
            return false;
        }
    }
}
