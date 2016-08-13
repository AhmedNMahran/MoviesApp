package com.ahmednmahran.moviesapp;

import android.util.Log;

import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.retriever.MovieDataRetriever;
import com.ahmednmahran.moviesapp.model.Movie;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    public static final String TAG = ExampleUnitTest.class.getSimpleName();
    private MovieDataRetriever movieDataRetriever;

    @Test
    public void movieRetrieveWhere() throws Exception {

        movieDataRetriever = new MovieDataRetriever(new DataRetrieveListener() {

            @Override
            public void onDataRetrieved(Object data) {
                Movie movie = (Movie) data;
                assertEquals(297761,movie.getMovieId());
            }

            @Override
            public void onRetrieveFailed() {
                Log.i(TAG, "onRetrieveFailed: ");
            }
        });
        movieDataRetriever.retrieveWhere("movie_id",297761+"",true);
    }
}