package com.ahmednmahran.moviesapp.controller.retriever;


import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.model.Movie;

import java.util.List;

/**
 * Created by Ahmed Nabil on 27/07/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 */

public class MovieDataRetriever extends DataRetriever{


    public MovieDataRetriever(DataRetrieveListener dataRetrieveListener) {
        super(dataRetrieveListener);
    }

    /**
     *
     * @return {@link MovieDataRetriever}
     */
    public MovieDataRetriever retrieveWhere(String key, String value, boolean singleValue){
        Object model;
        if(singleValue)
        model = new Select()
                .from(Movie.class)
                .where(key + " = ?", value).executeSingle();
        else{
            model = new Select()
                    .from(Movie.class)
                    .where(key + " = ?", value).executeSingle();
        }
        if(dataRetrieveListener != null){
            if(model != null)
                dataRetrieveListener.onDataRetrieved(model);
            else{
                dataRetrieveListener.onRetrieveFailed();
            }
        }
        return this;
    }

    public MovieDataRetriever retrieveFavourites(){
        Object model;
            model = new Select()
                    .from(Movie.class)
                    .where( " favourite = ?", true).execute();
        if(dataRetrieveListener != null){
            if(model != null)
                dataRetrieveListener.onDataRetrieved(model);
            else{
                dataRetrieveListener.onRetrieveFailed();
            }
        }
        return this;
    }

    /**
     *
     * @param sortBy
     */
    public void retrieveBy(String sortBy){
        List<Model> data = new Select()
                .from(Movie.class)
                .orderBy(sortBy).execute();
        if(dataRetrieveListener != null){
            if(data != null)
                dataRetrieveListener.onDataRetrieved(data);
            else{
                dataRetrieveListener.onRetrieveFailed();
            }
        }
    }

}
