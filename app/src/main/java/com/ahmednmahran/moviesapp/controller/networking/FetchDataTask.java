package com.ahmednmahran.moviesapp.controller.networking;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ahmed Nabil on 27/07/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 * A class extending {@link AsyncTask} used to fetch required data
 */
public class FetchDataTask extends AsyncTask<String,Void,Object> {

    private static final String LOG_TAG = FetchDataTask.class.getName();

    private final DataRetrieveListener dataRetrieveListener;

    public FetchDataTask(DataRetrieveListener listener) {
        this.dataRetrieveListener = listener;
    }

    @Override
    protected Object doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonStr = null;

        Object preparedData = null; // the object that will be finally returned to the onPostExecute method.
        try {

            URL url = new URL(Uri.parse(params[0]).toString());

            // Create the request , and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.
                return null;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return jsonStr;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if(result != null)
            dataRetrieveListener.onDataRetrieved(result); // notify the listener that the data is retrieved successfully.
        else
            dataRetrieveListener.onRetrieveFailed(); // notify the listener that there was an error.
    }
}