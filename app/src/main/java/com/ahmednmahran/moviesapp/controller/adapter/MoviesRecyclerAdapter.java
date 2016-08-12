package com.ahmednmahran.moviesapp.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.listeners.InflateListener;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.view.MovieThumbnailView;
import com.ahmednmahran.moviesapp.view.MovieView;

import java.util.ArrayList;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> mThumbnailsUrls;

    public MoviesRecyclerAdapter(Context context, ArrayList<String> mThumbnailsUrls) {
        this.mThumbnailsUrls = mThumbnailsUrls;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder movieViewHolder;
        MovieThumbnailView movieThumbnailView = new MovieThumbnailView(mContext);
        movieViewHolder = new ViewHolder(movieThumbnailView);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = new Movie(); // TODO: 12/08/2016 make real data
        holder.movieView.setInflateListener( new InflateListener() {
            @Override
            public void onInflated(View view) {
                holder.movieView.populateUiData(movie);
            }

            @Override
            public void onInflateFailed(View view) {
                Toast.makeText(mContext, R.string.thumb_inflate_failed, Toast.LENGTH_SHORT).show();
            }
        });
        ((MovieThumbnailView)holder.movieView).inflateMovieView();
    }

    @Override
    public int getItemCount() {
        return mThumbnailsUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MovieView movieView;

        public ViewHolder(MovieView movieView) {
            super(movieView);
            this.movieView = movieView;

        }


        /**
         * adds a new url to the dataset
         * @param position
         * @param url
         */
        public void add(int position, String url) {
            if(mThumbnailsUrls != null)
                mThumbnailsUrls.add(position, url);
            notifyItemInserted(position);
        }

        /**
         * removes item whose url is this
         * @param url
         */
        public void remove(String url) {
            int position = mThumbnailsUrls.indexOf(url);
            if(mThumbnailsUrls != null)
                mThumbnailsUrls.remove(position);
            notifyItemRemoved(position);
        }
    }
}
