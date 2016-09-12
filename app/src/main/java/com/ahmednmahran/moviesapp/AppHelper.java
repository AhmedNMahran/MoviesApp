package com.ahmednmahran.moviesapp;

import android.content.Context;

/**
 * Created by Ahmed Nabil on 12/09/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class AppHelper {
    public static AppHelper instance;
    private Context mContext;

    private AppHelper(){}
    public static AppHelper getInstance(){
        if(instance == null){
            instance = new AppHelper();
        }
        return instance;
    }

    public void init(Context appContext){
        this.mContext = appContext;

    }

    public Context getApplicationContext(){
        return mContext;
    }
}
