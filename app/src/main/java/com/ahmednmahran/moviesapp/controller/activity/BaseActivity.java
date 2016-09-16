package com.ahmednmahran.moviesapp.controller.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.cache.ConnectionBuddyCache;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

/**
 * Created by Ahmed Nabil on 16/09/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class BaseActivity extends AppCompatActivity implements ConnectivityChangeListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        if(savedInstanceState != null){
            ConnectionBuddyCache.clearLastNetworkState(this);
        }
    }


    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        this.connected = event.getState() == ConnectivityState.CONNECTED;
    }
    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }
    /**
     * determines whether device is connected to internet or not
     * @return
     */
    public boolean isConnected(){
        return connected;
    }

    @Override
    public void finish() {
        super.finish();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}