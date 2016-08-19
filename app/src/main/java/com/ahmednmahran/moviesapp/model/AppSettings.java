package com.ahmednmahran.moviesapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;
import com.ahmednmahran.moviesapp.BuildConfig;
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

        return mContext.getString(R.string.base_url)+getRequestType()+mContext.getString(R.string.api_key_param)+"="+BuildConfig.API_KEY;
    }

    public String getMovieTrailersUrl(int movieId){
        return mContext.getString(R.string.base_url)+"/"+movieId+mContext.getString(R.string.trailers_path)+mContext.getString(R.string.api_key_param)+"="+ BuildConfig.API_KEY;
    }

    public String getMovieReviewsUrl(int movieId){
        return mContext.getString(R.string.base_url)+"/"+movieId+mContext.getString(R.string.reviews_path)+mContext.getString(R.string.api_key_param)+"="+BuildConfig.API_KEY;
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

    public void saveTrailers(Trailer[] trailers) {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < trailers.length; i++) {
                Trailer trailer = trailers[i];
                trailer.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

    public void saveReviews(Review[] reviews) {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < reviews.length; i++) {
                Review review= reviews[i];
                review.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

    public void saveDetailRequestType(String requestType){
        editor.putString(mContext.getString(R.string.detail_request_type),requestType).commit();
    }

    public String getDetailRequestType() {
        return settings.getString(mContext.getString(R.string.detail_request_type) ,mContext.getString(R.string.action_trailer));
    }
}
