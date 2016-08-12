package com.ahmednmahran.moviesapp.controller.listeners;


import com.activeandroid.query.Select;
import com.ahmednmahran.moviesapp.controller.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.networking.FetchDataTask;
import com.ahmednmahran.moviesapp.controller.networking.ParseDataTask;
import com.ahmednmahran.moviesapp.model.Movie;

/**
 * Created by Ahmed Nabil on 27/07/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 */

public class DataRetriever implements DataRetrieveListener {
    private static final String LOG_TAG = DataRetriever.class.getSimpleName();
    private DataRetrieveListener dataRetrieveListener;
    private FetchDataTask fetchDataTask;
    private ParseDataTask parseDataTask;

    public DataRetriever(DataRetrieveListener dataRetrieveListener) {
        this.dataRetrieveListener = dataRetrieveListener;
    }

    /**
     *
     * @param id finds in DB by this id and notifies retrieve listener when finished
     * @return {@link DataRetriever}
     */
    public DataRetriever retrieveById(int id){
        Movie movie = new Select()
                .from(Movie.class)
                .where("movie_id = ?", id)
                .executeSingle();
        if(dataRetrieveListener != null){
            if(movie != null)
                dataRetrieveListener.onDataRetrieved(movie);
            else{
                dataRetrieveListener.onRetrieveFailed();
            }
        }
        return this;
    }
    public DataRetriever retrieve(String url, final Class<?> classType){
        fetchDataTask = new FetchDataTask(new DataRetrieveListener() {
            @Override
            public void onDataRetrieved(Object data) {
                if(parseDataTask != null)
                    parseDataTask.cancel(true);
                parseDataTask = new ParseDataTask(DataRetriever.this, classType);
                parseDataTask.execute((String)data);
            }

            @Override
            public void onRetrieveFailed() {

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
}
