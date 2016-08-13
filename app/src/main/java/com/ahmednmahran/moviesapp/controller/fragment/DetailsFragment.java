package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.listener.InflateListener;
import com.ahmednmahran.moviesapp.controller.retriever.MovieDataRetriever;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.view.MovieDetailsView;


public class DetailsFragment extends Fragment implements DataRetrieveListener {

    private MovieDetailsView movieView;
    private Movie movie;
    private OnFavoriteChangeListener onFavoriteChangeListener;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        movieView = new MovieDetailsView(getContext(), new InflateListener() {
            @Override
            public void onInflated(View view) {
                new MovieDataRetriever(DetailsFragment.this).retrieveWhere(getString(R.string.movie_id_key),getActivity().getIntent().getIntExtra(getString(R.string.extra_id),0)+"",true);
            }

            @Override
            public void onInflateFailed(View view) {
                Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
            }
        });
        ((ViewGroup)rootView).addView(movieView);
        movieView.inflateMovie();
        return rootView;
    }


    @Override
    public void onDataRetrieved(Object data) {
        try{
            movie = ((Movie)data);
            movieView.populateUiData(movie);
            if(onFavoriteChangeListener != null){
                onFavoriteChangeListener.onFavoriteChanged(movie.isFavourite(), false);
            }

        }catch (ClassCastException e){
            Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRetrieveFailed() {
        Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
    }

    /**
     * listen to favorite change
     * @param listener
     */
    public void setOnFavoriteChangeListener(OnFavoriteChangeListener listener){
        this.onFavoriteChangeListener = listener;
        if(movie != null)
            onFavoriteChangeListener.onFavoriteChanged(movie.isFavourite(),false);
    }
    public void toggleFavorite() {
        if(movie != null){
            if(movie.isFavourite()) {
                movie.setFavourite(false);
            }
            else{
                movie.setFavourite(true);
            }
            movie.save();
            if(onFavoriteChangeListener != null){
                onFavoriteChangeListener.onFavoriteChanged(movie.isFavourite(), true);
            }
        }
    }

    public interface OnFavoriteChangeListener{
        void onFavoriteChanged(boolean currentState, boolean shouldShowMessage);
    }
}
