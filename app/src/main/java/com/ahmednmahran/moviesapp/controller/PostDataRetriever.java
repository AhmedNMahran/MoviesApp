package com.ahmednmahran.moviesapp.controller;


import com.ahmednmahran.moviesapp.controller.networking.FetchDataTask;
import com.ahmednmahran.moviesapp.controller.networking.ParseDataTask;

/**
 * Created by Ahmed Nabil on 27/07/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 */

public class PostDataRetriever implements DataRetrieveListener{
    private static final String LOG_TAG = PostDataRetriever.class.getSimpleName();
    private DataRetrieveListener dataRetrieveListener;
    private FetchDataTask fetchDataTask;
    public PostDataRetriever(DataRetrieveListener dataRetrieveListener) {
        this.dataRetrieveListener = dataRetrieveListener;
    }

    public PostDataRetriever retrieve(String url, final Class<?> classType){
        fetchDataTask = new FetchDataTask(new DataRetrieveListener() {
            @Override
            public void onDataRetrieved(Object data) {
                new ParseDataTask(PostDataRetriever.this,classType).execute((String)data);
            }

            @Override
            public void onRetrieveFailed() {

            }
        });
        fetchDataTask.execute(url);
        return this;
    }

    @Override
    public void onDataRetrieved(Object data) {
        dataRetrieveListener.onDataRetrieved(data);
    }

    @Override
    public void onRetrieveFailed() {
        dataRetrieveListener.onRetrieveFailed();
    }
}
