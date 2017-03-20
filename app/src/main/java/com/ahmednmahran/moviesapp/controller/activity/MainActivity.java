package com.ahmednmahran.moviesapp.controller.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.fragment.DetailsFragment;
import com.ahmednmahran.moviesapp.controller.fragment.MainActivityFragment;
import com.ahmednmahran.moviesapp.model.AppSettings;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

/**
 * Created by Ahmed Nabil on 07/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * An Activity used to view the main list of movies, using {@link com.ahmednmahran.moviesapp.controller.fragment.MainActivityFragment}
 *
 */
public class MainActivity extends BaseActivity  {

    private DetailsFragment detailsFragment;
//    private FloatingActionButton favoriteFab;
    private AppSettings appPreference;
    private MainActivityFragment moviesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appPreference = AppSettings.getAppPreference(getApplicationContext());


        if(getResources().getBoolean(R.bool.isTablet)){
            detailsFragment = ((DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment));
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.radioReviews){
                        appPreference.saveDetailRequestType(getString(R.string.action_review));
                    }
                    else if(checkedId == R.id.radioTrailer){
                        AppSettings.getAppPreference(getApplicationContext()).saveDetailRequestType(getString(R.string.action_trailer));
                    }
                    detailsFragment.retrieveMovie();
                }
            });
        }

    }
}
