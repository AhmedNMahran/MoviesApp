package com.ahmednmahran.moviesapp.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.fragment.DetailsFragment;

public class DetailsActivity extends BaseActivity implements DetailsFragment.OnFavoriteChangeListener {

    private DetailsFragment detailsFragment;
    private FloatingActionButton favoriteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String stringExtra = getIntent().getStringExtra(getString(R.string.extra_title));
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null)
        {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            if(stringExtra != null )
                supportActionBar.setTitle(stringExtra);
        }
        detailsFragment = ((DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment));
        favoriteFab = (FloatingActionButton) findViewById(R.id.fab);
        favoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsFragment.toggleFavorite();

            }
        });

        detailsFragment.setOnFavoriteChangeListener(this);

    }

    @Override
    public void onFavoriteChanged(boolean favorite, boolean shouldShowMessage) {
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
