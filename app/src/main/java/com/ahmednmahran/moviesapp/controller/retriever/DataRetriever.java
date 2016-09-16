package com.ahmednmahran.moviesapp.controller.retriever;


import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.networking.FetchDataTask;
import com.ahmednmahran.moviesapp.controller.networking.ParseDataTask;

/**
 * Created by Ahmed Nabil on 27/07/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 */

public class DataRetriever implements DataRetrieveListener {
    private static final String LOG_TAG = DataRetriever.class.getSimpleName();
    protected DataRetrieveListener dataRetrieveListener;
    private FetchDataTask fetchDataTask;
    private ParseDataTask parseDataTask;

    public DataRetriever(DataRetrieveListener dataRetrieveListener) {
        this.dataRetrieveListener = dataRetrieveListener;
    }

    public DataRetriever retrieve(String url, final Class<?> classType, final boolean cancelRunningTask){
        fetchDataTask = new FetchDataTask(new DataRetrieveListener() {
            @Override
            public void onDataRetrieved(Object data) {
                if(cancelRunningTask) {
                    if(parseDataTask != null)
                        parseDataTask.cancel(true);
                }
                parseDataTask = new ParseDataTask(DataRetriever.this, classType);
                parseDataTask.execute((String)data);
            }

            @Override
            public void onRetrieveFailed() {
                DataRetriever.this.onRetrieveFailed();
            }
        });
        fetchDataTask.execute(url);
        return this;
    }

    public DataRetriever cancelRequestIfRunning(){
        if(fetchDataTask != null)
            fetchDataTask.cancel(true);
        return this;
    }
    @Override
    public void onDataRetrieved(Object data) {
        if(dataRetrieveListener != null)
            dataRetrieveListener.onDataRetrieved(data);
    }

    @Override
    public void onRetrieveFailed() {
        if(dataRetrieveListener != null)
            dataRetrieveListener.onRetrieveFailed();
    }

    public void setRetrieveListener(DataRetrieveListener retrieveListener) {
        this.dataRetrieveListener = retrieveListener;
    }
}
