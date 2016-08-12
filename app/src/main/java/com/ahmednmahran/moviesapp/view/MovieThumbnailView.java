package com.ahmednmahran.moviesapp.view;

import android.content.Context;
import android.widget.ImageView;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.listeners.InflateListener;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * A view that wraps the thumbnail which be visible on the grid item
 */
public class MovieThumbnailView extends MovieView{

    public MovieThumbnailView(Context context, InflateListener mInflateListener) {
        super(context, mInflateListener);
    }
    public MovieThumbnailView(Context context){
        super(context);
    }

    /**
     * Prepare and inflate the view to be populated by item's data
     * @return {@link MovieThumbnailView}
     */
    public MovieView inflateMovieView() {
        super.inflateMovieView(R.layout.item_movie);
        if(rootView ==  null ) {
            if(mInflateListener != null)
                mInflateListener.onInflateFailed(this); // view inflated successfully
            return this;
        }
        imgThumbnail = (ImageView) rootView.findViewById(R.id.imgMovieThumbnail);
        if(mInflateListener != null)
            mInflateListener.onInflated(this); //failed to inflate this view
        return this;
    }
}
