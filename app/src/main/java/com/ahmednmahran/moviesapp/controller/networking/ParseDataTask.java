package com.ahmednmahran.moviesapp.controller.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Modifier;


/**
 * Created by Ahmed Nabil on 27/07/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * A class extending {@link AsyncTask} used to parse json and map it to the provided Model in background
 */
public class ParseDataTask extends AsyncTask<String,Void,Object> {

    private static final String LOG_TAG = ParseDataTask.class.getName();

    private final DataRetrieveListener dataRetrieveListener;
    private final Class<?> mapObject;
    private Object data;

    /**
     * Constructor
     * @param listener to handle the parsed data
     * @param mapObject the type to which we will map the data
     */
    public  ParseDataTask(DataRetrieveListener listener, Class<?> mapObject) {
        this.dataRetrieveListener = listener;
        this.mapObject = mapObject;
    }

    @Override
    protected Object doInBackground(String... params) {
        try{

            Gson gson =// new Gson();
            new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .serializeNulls()
                    .create();
            String param = params[0];
            if(param == null || param.isEmpty())
                return null;
            data = gson.fromJson(param, mapObject);
        }catch (JsonSyntaxException e){
            Log.e(LOG_TAG, "onDataRetrieved: ", e); // there was an error mapping the json to the provided model type
            return null;
        }
        return data;
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