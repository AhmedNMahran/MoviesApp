package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.activity.BaseActivity;
import com.ahmednmahran.moviesapp.controller.adapter.ReviewsRecyclerAdapter;
import com.ahmednmahran.moviesapp.controller.adapter.TrailersRecyclerAdapter;
import com.ahmednmahran.moviesapp.controller.listener.DataRetrieveListener;
import com.ahmednmahran.moviesapp.controller.listener.InflateListener;
import com.ahmednmahran.moviesapp.controller.retriever.MovieDataRetriever;
import com.ahmednmahran.moviesapp.model.AppSettings;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.model.Review;
import com.ahmednmahran.moviesapp.model.ReviewResponse;
import com.ahmednmahran.moviesapp.model.Trailer;
import com.ahmednmahran.moviesapp.model.TrailerResponse;
import com.ahmednmahran.moviesapp.view.MovieDetailsView;

import java.util.Arrays;


public class DetailsFragment extends Fragment implements DataRetrieveListener {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private MovieDetailsView movieView;
    private Movie movie;
    private OnFavoriteChangeListener onFavoriteChangeListener;
    private int movieId;
    private RecyclerView trailersRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AppSettings appSettings;
    private RecyclerView.Adapter mRecyclerAdapter;
    /**
     * use reference to the currently selected menu item to toggle menu checkboxes
     */
    private MenuItem lastCheckedItem;
    private MovieDataRetriever movieDataRetriever;
    private TextView txtListTitle;
    private ProgressBar progressBar;
    private View rootView;
    private FloatingActionButton fab;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * sets the movie to be used for related data retrieval
     * @param movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
        if(movie != null)
            this.movieId = movie.getMovieId();
        if(this.movieId == 0){
            this.movieId = getDefaultMovieId();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        if(getResources().getBoolean(R.bool.isTablet)){
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite();
                }
            });
        }
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        appSettings = AppSettings.getAppPreference(getContext().getApplicationContext());
        movieView = new MovieDetailsView(getContext(), new InflateListener() {
            @Override
            public void onInflated(View view) {
                if(savedInstanceState != null){
                    movieId = savedInstanceState.getInt("movie_id");
                    retrieveMovie();
                }else{
                    if(!getResources().getBoolean(R.bool.isTablet)) { // handheld device
                        movieId = getActivity().getIntent().getIntExtra(getString(R.string.extra_id), 0);
                        retrieveMovie();
                    }
                }
            }


            @Override
            public void onInflateFailed(View view) {
                Toast.makeText(getContext(), "Failed to fetch Data!", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout rootContainer = (LinearLayout) rootView.findViewById(R.id.rootContainer);
        rootContainer.addView(movieView);
        txtListTitle = (TextView) inflater.inflate(R.layout.text_list_title, container, false);
        rootContainer.addView(txtListTitle);
        movieView.inflateMovie();
        //inflate and prepare trailers
        trailersRecyclerView = (RecyclerView) inflater.inflate(R.layout.details_recycler,container,true);
        mLayoutManager = new LinearLayoutManager(getContext());
        trailersRecyclerView.setLayoutManager(mLayoutManager);
        rootContainer.addView(trailersRecyclerView);
        return rootView;
    }

    private int getDefaultMovieId() {
        return AppSettings.getAppPreference(getContext().getApplicationContext()).getDefaultMovieId();
    }

    /**
     * always gets the movie data from offline database using movieId
     */
    public void retrieveMovie() {
        progressBar.setVisibility(View.VISIBLE);
        new MovieDataRetriever(DetailsFragment.this)
                //get movie details
                .retrieveWhere(getString(R.string.movie_id_key), movieId +"",true);
        String detailRequestType = AppSettings.getAppPreference(getContext().getApplicationContext()).getDetailRequestType();
        if(detailRequestType.equalsIgnoreCase(getString(R.string.action_trailer)))
            getList(AppSettings.getAppPreference(getContext().getApplicationContext()).getMovieTrailersUrl(movieId), TrailerResponse.class);
        else
            getList(AppSettings.getAppPreference(getContext().getApplicationContext()).getMovieReviewsUrl(movieId), ReviewResponse.class);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!getResources().getBoolean(R.bool.isTablet))
        {
            inflater.inflate(R.menu.menu_details, menu);
            super.onCreateOptionsMenu(menu,inflater);
            if(appSettings.getDetailRequestType().equals(getString(R.string.action_review)))
                lastCheckedItem = menu.findItem(R.id.action_review).setChecked(true);
            else if(appSettings.getDetailRequestType().equals(getString(R.string.action_trailer)))
                lastCheckedItem = menu.findItem(R.id.action_trailer).setChecked(true);
            else
                lastCheckedItem = menu.findItem(R.id.action_trailer).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!getResources().getBoolean(R.bool.isTablet)){
            switch (item.getItemId()){
                case R.id.action_trailer:
                    AppSettings.getAppPreference(getContext().getApplicationContext()).saveDetailRequestType(getString(R.string.action_trailer));
                    getList(AppSettings.getAppPreference(getContext().getApplicationContext()).getMovieTrailersUrl(movieId), TrailerResponse.class);
                    break;
                case R.id.action_review:
                    AppSettings.getAppPreference(getContext().getApplicationContext()).saveDetailRequestType(getString(R.string.action_review));
                    getList(AppSettings.getAppPreference(getContext().getApplicationContext()).getMovieReviewsUrl(movieId), ReviewResponse.class);
                    break;
                case android.R.id.home:
                    getActivity().finish();
            }
            if(lastCheckedItem.getItemId() != item.getItemId())
                lastCheckedItem.setChecked(false);
            item.setChecked(true);
            lastCheckedItem = item;
        }
        return true;
    }

    private void getList(String url, Class<?> castClass) {
        if(!((BaseActivity)getActivity()).isNetworkAvailable()){
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
        if(txtListTitle != null)
            txtListTitle.setText(AppSettings.getAppPreference(getContext().getApplicationContext()).getDetailRequestType());
        if(movieDataRetriever != null)
            movieDataRetriever.cancelRequestIfRunning();
        movieDataRetriever = new MovieDataRetriever(new DataRetrieveListener() {
            @Override
            public void onDataRetrieved(Object data) {
                progressBar.setVisibility(View.INVISIBLE);
                if(data instanceof TrailerResponse)
                {
                    Trailer[] trailers = ((TrailerResponse) data).getResults();
                    appSettings.saveTrailers(trailers);
                    mRecyclerAdapter = new TrailersRecyclerAdapter(getContext(), Arrays.asList(trailers));
                    trailersRecyclerView.setAdapter(mRecyclerAdapter);
                }else if(data instanceof ReviewResponse){
                    Review[] reviews = ((ReviewResponse) data).getResults();
                    appSettings.saveReviews(reviews);
                    mRecyclerAdapter = new ReviewsRecyclerAdapter(getContext(), Arrays.asList(reviews));
                    trailersRecyclerView.setAdapter(mRecyclerAdapter);
                }
            }

            @Override
            public void onRetrieveFailed() {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Failed to fetch List!", Toast.LENGTH_SHORT).show();
            }
        });

        movieDataRetriever.retrieve(url,castClass,true);
//                        //get movie reviews
    }

    @Override
    public void onDataRetrieved(Object data) {
        try{
            movie = ((Movie)data);
            if(getResources().getBoolean(R.bool.isTablet))
            {
                fab.setVisibility(View.VISIBLE);
                fab.setImageResource(movie.isFavourite()?R.drawable.ic_favorite_white_24dp:R.drawable.ic_favorite_border_white_24dp);
            }
            movieView.populateUiData(movie);
            if(onFavoriteChangeListener != null){
                onFavoriteChangeListener.onFavoriteChanged(movie.isFavourite(), false);
            }

        }catch (ClassCastException e){
            Toast.makeText(getContext(), getString(R.string.fetch_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRetrieveFailed() {
        Toast.makeText(getContext(), getString(R.string.fetch_failed), Toast.LENGTH_SHORT).show();

    }

    /**
     * listen to favorite change
     * @param listener
     */
    public void setOnFavoriteChangeListener(OnFavoriteChangeListener listener){
        this.onFavoriteChangeListener = listener;
        if(!getResources().getBoolean(R.bool.isTablet)){
            if(movie != null)
                onFavoriteChangeListener.onFavoriteChanged(movie.isFavourite(),false);
        }
    }
    public void toggleFavorite() {
        if(movie != null){
            if(movie.isFavourite()) {
                movie.setFavourite(false);
                if(getResources().getBoolean(R.bool.isTablet)){
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Snackbar.make(fab, "Movie added to favorites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
            else{
                movie.setFavourite(true);
                if(getResources().getBoolean(R.bool.isTablet)){
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    Snackbar.make(fab, "Movie removed from favorites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
            movie.save(); //update the movie in database
            if(onFavoriteChangeListener != null){
                onFavoriteChangeListener.onFavoriteChanged(movie.isFavourite(), true);
            }
            if(getResources().getBoolean(R.bool.isTablet)){
                if(AppSettings.getAppPreference(getActivity().getApplicationContext()).getRequestType().equals(getString(R.string.find_fav)))
                {

                    MainActivityFragment mainActivityFragment = (MainActivityFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
                    mainActivityFragment.getMoviesFavourites();
                }
            }
        }
    }

    public interface OnFavoriteChangeListener{
        void onFavoriteChanged(boolean currentState, boolean shouldShowMessage);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("movie_id",movieId);
        super.onSaveInstanceState(outState);
    }
}
