package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.adapter.MoviesRecyclerAdapter;
import com.ahmednmahran.moviesapp.controller.listeners.DataRetriever;
import com.ahmednmahran.moviesapp.model.AppSettings;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Ahmed Nabil on 07/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * A Fragment used to view the main list of movies
 *
 */
public class MainActivityFragment extends Fragment implements DataRetrieveListener {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private ArrayList<String> dataList;
    private HashMap<String, String> parametersMap;
    private AppSettings appSettings;
    private DataRetriever dataRetriever;
    private ProgressBar progressBar;
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a Grid layout manager
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        appSettings = AppSettings.getAppPreference(getContext().getApplicationContext());
        dataRetriever = new DataRetriever(this);
        getMoviesList(getString(R.string.popular));
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_popular:
                getMoviesList(getString(R.string.popular));
            break;
            case R.id.action_top:
                getMoviesList(getString(R.string.top_rated));
            break;
        }
        return true;
    }

    private void getMoviesList(String requestType) {
        appSettings.setRequestType(requestType);
        if(dataRetriever != null) {
            dataRetriever.cancelRequestIfRunning();
            dataRetriever.retrieve(appSettings.getRequestUrl(),Response.class);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDataRetrieved(Object data) {
        progressBar.setVisibility(View.INVISIBLE);

        try{
            Movie[] movies = ((Response)data).getResults();
            ActiveAndroid.beginTransaction();
            try {
                for (int i = 0; i < movies.length; i++) {
                    Movie movie = movies[i];
                    movie.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
            moviesRecyclerAdapter = new MoviesRecyclerAdapter(getContext(), Arrays.asList(movies));
            mRecyclerView.setAdapter(moviesRecyclerAdapter);
        }catch (ClassCastException e){
            Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRetrieveFailed() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
