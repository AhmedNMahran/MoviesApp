package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.listener.InflateListener;
import com.ahmednmahran.moviesapp.controller.retriever.MovieDataRetriever;
import com.ahmednmahran.moviesapp.model.AppSettings;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.model.Trailer;
import com.ahmednmahran.moviesapp.model.TrailerResponse;
import com.ahmednmahran.moviesapp.view.MovieDetailsView;


public class DetailsFragment extends Fragment implements DataRetrieveListener {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private MovieDetailsView movieView;
    private Movie movie;
    private OnFavoriteChangeListener onFavoriteChangeListener;
    private int movieId;

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
                movieId = getActivity().getIntent().getIntExtra(getString(R.string.extra_id), 0);
                new MovieDataRetriever(DetailsFragment.this)
                        //get movie details
                        .retrieveWhere(getString(R.string.movie_id_key), movieId +"",true);
                        //get movie trailers
                        new MovieDataRetriever(new DataRetrieveListener() {
                            @Override
                            public void onDataRetrieved(Object data) {
                                Trailer[] trailers = ((TrailerResponse)data).getResults();
                                for (Trailer trailer :
                                        trailers) {
                                    Log.i(TAG, "onDataRetrieved:  "+ trailer);
                                }
                            }

                            @Override
                            public void onRetrieveFailed() {

                            }
                        }).retrieve(AppSettings.getAppPreference(getContext().getApplicationContext()).getMovieTrailersUrl(movieId),TrailerResponse.class,false);
//                        //get movie reviews
//                        .retrieve(AppSettings.getAppPreference(getContext().getApplicationContext()).getRequestUrl(AppSettings.REQUEST_REVIEWS),ReviewResponse.class,false);

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
