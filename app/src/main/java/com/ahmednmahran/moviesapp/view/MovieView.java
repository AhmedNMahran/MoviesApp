package com.ahmednmahran.moviesapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.listener.InflateListener;
import com.ahmednmahran.moviesapp.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 */
public class MovieView extends FrameLayout{
    protected InflateListener mInflateListener;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    protected View rootView;
    protected ImageView imgThumbnail;
    protected TextView tvDate;
    private static final String TAG=MovieView.class.getSimpleName();

    public MovieView(Context context) {
        super(context);
        this.mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MovieView(Context context, InflateListener mInflateListener) {
        this(context);
        this.mInflateListener = mInflateListener;
    }


    /**
     * Inflates the view using the provided resource
     * @param resource
     * @return {@link MovieView}
     */
    protected MovieView inflateMovieView(int resource){

        rootView = mInflater.inflate(resource, this, true);
        return this;
    }

    public void populateUiData(Movie movie) {
        if(movie == null)
        {
            if(mInflateListener != null)
                mInflateListener.onInflateFailed(this);
        }
//        Picasso.Builder builder = new Picasso.Builder(mContext);
//        builder.listener(new Picasso.Listener()
//        {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
//            {
//                Log.e(TAG, "onImageLoadFailed: ", exception);
////                exception.printStackTrace();
//            }
//        });
//        builder.build().load(movie.getMovieThumbnail()).placeholder(R.mipmap.ic_launcher).error(android.R.drawable.stat_notify_error).fit().into(imgThumbnail);
        String posterPath = movie != null ? movie.getPosterPath() : "";
        if(posterPath == null)
            posterPath = "";
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185//"+ posterPath).placeholder(R.mipmap.ic_launcher).error(android.R.drawable.stat_notify_error).fit().into(imgThumbnail);
    }

    public void setInflateListener(InflateListener mInflateListener) {
        this.mInflateListener = mInflateListener;
    }
}
