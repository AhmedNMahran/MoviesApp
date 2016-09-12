package com.ahmednmahran.moviesapp.controller.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.fragment.DetailsFragment;
import com.ahmednmahran.moviesapp.controller.fragment.MainActivityFragment;
import com.ahmednmahran.moviesapp.model.AppSettings;

/**
 * Created by Ahmed Nabil on 07/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * An Activity used to view the main list of movies, using {@link com.ahmednmahran.moviesapp.controller.fragment.MainActivityFragment}
 *
 */
public class MainActivity extends AppCompatActivity implements DetailsFragment.OnFavoriteChangeListener{

    private DetailsFragment detailsFragment;
    private FloatingActionButton favoriteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getResources().getBoolean(R.bool.isTablet)){
            detailsFragment = ((DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment));
            favoriteFab = (FloatingActionButton) findViewById(R.id.fab);
            favoriteFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsFragment.toggleFavorite();

                }
            });
            detailsFragment.setOnFavoriteChangeListener(this);
        }

    }

    @Override
    public void onFavoriteChanged(boolean favorite, boolean shouldShowMessage) {
        // refresh and update the list on toggle favourite
        if(AppSettings.getAppPreference(getApplicationContext()).getRequestType().equals(getString(R.string.find_fav))) {
            ((MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment)).getMoviesFavourites();
        }
        if(favorite){
            favoriteFab.setImageResource(R.drawable.ic_favorite_white_24dp);
            if(shouldShowMessage)
                Snackbar.make(favoriteFab, "Movie added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }else{
            favoriteFab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            if(shouldShowMessage)
                Snackbar.make(favoriteFab, "Movie removed from favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
    }
}
