package com.ahmednmahran.moviesapp.view;

import android.content.Context;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.listeners.InflateListener;
import com.ahmednmahran.moviesapp.model.Movie;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 */
public class MovieDetailsView extends MovieView {

    InflateListener inflateListener;
    public MovieDetailsView(Context context, InflateListener inflateListener) {
        super(context, inflateListener);
    }

    public MovieDetailsView inflateMovie(){

        super.inflateMovieView(R.layout.movie_detail);
        if(rootView ==  null ) {
            if(inflateListener != null)
                inflateListener.onInflateFailed(this);
            return this;
        }
        if(inflateListener != null)
            inflateListener.onInflated(this);
        return this;
    }

    @Override
    public void populateUiData(Movie movie) {
        super.populateUiData(movie);
        // TODO: 12/08/2016 add extra population
    }
}
