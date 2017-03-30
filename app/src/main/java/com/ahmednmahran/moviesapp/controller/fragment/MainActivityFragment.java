package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.activity.BaseActivity;
import com.ahmednmahran.moviesapp.controller.adapter.MoviesRecyclerAdapter;
import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.retriever.MovieDataRetriever;
import com.ahmednmahran.moviesapp.model.AppSettings;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.model.Response;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ahmed Nabil on 07/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * A Fragment used to view the main list of movies
 *
 */
public class MainActivityFragment extends Fragment implements DataRetrieveListener, DatePickerDialog.OnDateSetListener{

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private ArrayList<String> dataList;
    private HashMap<String, String> parametersMap;
    private AppSettings appSettings;
    private MovieDataRetriever dataRetriever;
    private ProgressBar progressBar;
    private MenuItem lastCheckedItem;
    private boolean shownDefaultMovie;
    private boolean calledFromMenu;
    private View rootView;
    private String pickedDate;
    private FloatingActionButton fabFilter;

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

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        fabFilter = (FloatingActionButton) rootView.findViewById(R.id.fab_filter);
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a Grid layout manager
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        appSettings = AppSettings.getAppPreference(getContext().getApplicationContext());
        shownDefaultMovie = savedInstanceState!=null && savedInstanceState.getString("lastRequest").equals(appSettings.getRequestType()); // [Tablet UI] do distinguish between rotation that gets the last selected movie and first open or request that gets the first element to view in details
        dataRetriever = new MovieDataRetriever(this);
        calledFromMenu = false;
        getMoviesList(appSettings.getRequestType());
        return rootView;
    }


    private void showFilterDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                MainActivityFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
        if(appSettings.getRequestType().equals(getString(R.string.find_fav)))
            lastCheckedItem = menu.findItem(R.id.action_favorite).setChecked(true);
        else if(appSettings.getRequestType().equals(getString(R.string.popular)))
            lastCheckedItem = menu.findItem(R.id.action_popular).setChecked(true);
        else if(appSettings.getRequestType().equals(getString(R.string.top_rated)))
            lastCheckedItem = menu.findItem(R.id.action_top).setChecked(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        calledFromMenu = true;
        switch (item.getItemId()){
            case R.id.action_popular:
                getMoviesList(getString(R.string.popular));
                break;
            case R.id.action_top:
                getMoviesList(getString(R.string.top_rated));
                break;
            case R.id.action_favorite:
                getMoviesFavourites();
                break;
        }
        if(lastCheckedItem.getItemId() != item.getItemId())
            lastCheckedItem.setChecked(false);
        item.setChecked(true);
        lastCheckedItem = item;
        return true;
    }

    public void getMoviesFavourites() {
        appSettings.setRequestType(getString(R.string.find_fav));
        dataRetriever.setRetrieveListener(new DataRetrieveListener() {
            @Override
            public void onDataRetrieved(Object data) {
                progressBar.setVisibility(View.INVISIBLE);

                try{
                    final ArrayList<Movie> movies = (ArrayList<Movie>) data;
                    moviesRecyclerAdapter = new MoviesRecyclerAdapter(getContext(), movies);
                    mRecyclerView.setAdapter(moviesRecyclerAdapter);
                    if(getResources().getBoolean(R.bool.isTablet)){
                        if(!shownDefaultMovie || calledFromMenu){
                            appSettings.setDefaultMovieId(movies.get(0).getMovieId());
                            if(getActivity() != null){
                                DetailsFragment detailsFragment = (DetailsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
                                if(detailsFragment != null){
                                    detailsFragment.setMovie(movies.get(0));
                                    detailsFragment.retrieveMovie();
                                }else{
                                    rootView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            DetailsFragment detailsFragment = (DetailsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
                                            detailsFragment.setMovie(movies.get(0));
                                            detailsFragment.retrieveMovie();
                                        }
                                    },1000);
                                }
                            }
                        }
                    }
                }catch (ClassCastException e){
                    Toast.makeText(getContext(), getString(R.string.fetch_failed), Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(getContext(), getString(R.string.fetch_failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onRetrieveFailed() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        dataRetriever.retrieveFavourites();
    }

    private void getMoviesList(String requestType) {
        if(requestType .equals(getString(R.string.find_fav)))
            getMoviesFavourites(); // always get offline no connection needed.
        else{
            // check for connectivity first
            if(!((BaseActivity)getActivity()).isNetworkAvailable()){
                Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
            }
            // continue to get data, if device is offline, it will try to get data from database.
            appSettings.setRequestType(requestType);
            appSettings.setRequestDateFilter(pickedDate);
            if(dataRetriever != null) {
                dataRetriever.setRetrieveListener(this);
                dataRetriever.cancelRequestIfRunning();
                dataRetriever.retrieve(appSettings.getRequestUrl(),Response.class,true);
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDataRetrieved(Object data) {
        progressBar.setVisibility(View.INVISIBLE);

        try{
            Movie[] movies = ((Response)data).getResults();
            appSettings.saveMovies(movies);

            List<Movie> movieList = new ArrayList<>();

            //filter the retrieved data
            if(pickedDate != null && !pickedDate.isEmpty()) {
                for (Movie movie: movies){
                    if (movie.getReleaseDate().equalsIgnoreCase(pickedDate))
                        movieList.add(movie);
                }
                if(movieList.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.no_match, Toast.LENGTH_SHORT).show();
                }
            }else{
                movieList = Arrays.asList(movies);
            }
                moviesRecyclerAdapter = new MoviesRecyclerAdapter(getContext(), movieList);
            pickedDate = "";
            mRecyclerView.setAdapter(moviesRecyclerAdapter);
            if(getResources().getBoolean(R.bool.isTablet)){
                if(!shownDefaultMovie || calledFromMenu){
                    appSettings.setDefaultMovieId(movies[0].getMovieId());
                    DetailsFragment detailsFragment = (DetailsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
                    detailsFragment.setMovie(movies[0]);
                    detailsFragment.retrieveMovie();
                }
            }
        }catch (ClassCastException e){
            Toast.makeText(getContext(), R.string.fetch_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRetrieveFailed() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), R.string.fetch_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("lastRequest",appSettings.getRequestType());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!getResources().getBoolean(R.bool.isTablet))
            if(appSettings !=null) {
                String requestType = appSettings.getRequestType();
                if(requestType.equals(getString(R.string.find_fav))) // update favourites list
                    getMoviesList(requestType);
            }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String yearString = String.format("%04d",year);
        String monthString = String.format("%02d",(monthOfYear+1));
        String dayString = String.format("%02d",dayOfMonth);
        pickedDate = yearString + "-" + monthString + "-" + dayString;

        Toast.makeText(getActivity(), pickedDate, Toast.LENGTH_SHORT).show();
        getMoviesList(appSettings.getRequestType());
    }
}
