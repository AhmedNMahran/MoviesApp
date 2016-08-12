package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.listeners.DataRetriever;
import com.ahmednmahran.moviesapp.controller.listeners.InflateListener;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.view.MovieDetailsView;


public class DetailsFragment extends Fragment implements DataRetrieveListener {

    private MovieDetailsView movieView;

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
                new DataRetriever(DetailsFragment.this).retrieveById(getActivity().getIntent().getIntExtra(getString(R.string.extra_id),0));
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
            Movie movie = ((Movie)data);
            movieView.populateUiData(movie);

        }catch (ClassCastException e){
            Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRetrieveFailed() {
        Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
    }
}
