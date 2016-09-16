package com.ahmednmahran.moviesapp.controller;


import com.ahmednmahran.moviesapp.AppHelper;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class MoviesAppApplication  extends com.activeandroid.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppHelper.getInstance().init(this);
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
}
