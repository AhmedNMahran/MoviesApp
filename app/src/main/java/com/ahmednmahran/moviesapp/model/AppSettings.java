package com.ahmednmahran.moviesapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;
import com.ahmednmahran.moviesapp.R;

/**
 * Created by Ahmed Nabil on 07/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * A Fragment used to view the main list of movies
 * A class used to save and retrieve data from shared preferences
 */
public class AppSettings {
    public static AppSettings appPreference;
    private Context mContext;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private AppSettings(Context context){
        mContext = context;
        settings = context.getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static AppSettings getAppPreference(Context context){
        if(appPreference==null){
            appPreference = new AppSettings(context);
        }
        return appPreference;
    }

    /**
     *
     * @param requestType whether it's popular, top rated,etc...
     */
    public void setRequestType(String requestType){
        editor.putString("requestType",requestType).commit();
    }

    /**
     *
     * @return the type of request to populate the grid
     */
    public String getRequestType(){
        return settings.getString("requestType",mContext.getString(R.string.popular));
    }

    /**
     *
     * @return
     */
    public String getRequestUrl() {

        return mContext.getString(R.string.base_url)+getRequestType()+mContext.getString(R.string.api_key_param)+"="+mContext.getString(R.string.api_key);
    }

    public String getMovieTrailersUrl(int movieId){
        return mContext.getString(R.string.base_url)+"/"+movieId+mContext.getString(R.string.trailers_path)+mContext.getString(R.string.api_key_param)+"="+mContext.getString(R.string.api_key);
    }

    public String getMovieReviewsUrl(int movieId){
        return mContext.getString(R.string.base_url)+"/"+movieId+mContext.getString(R.string.reviews_path)+mContext.getString(R.string.api_key_param)+"="+mContext.getString(R.string.api_key);
    }
    /**
     * save these movies to be accessible locally
     * @param movies
     */
    public void saveMovies(Movie[] movies) {
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
    }
}
