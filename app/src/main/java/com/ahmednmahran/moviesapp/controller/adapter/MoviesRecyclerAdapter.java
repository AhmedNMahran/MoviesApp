package com.ahmednmahran.moviesapp.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.activity.DetailsActivity;
import com.ahmednmahran.moviesapp.controller.listener.InflateListener;
import com.ahmednmahran.moviesapp.model.Movie;
import com.ahmednmahran.moviesapp.view.MovieThumbnailView;
import com.ahmednmahran.moviesapp.view.MovieView;

import java.util.List;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>{
    private Context mContext;
    private List<Movie> movies;

    public MoviesRecyclerAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
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
        holder.movieView.setInflateListener( new InflateListener() {
            @Override
            public void onInflated(View view) {
                if(movies != null)
                {
                    final Movie movie = movies.get(holder.getAdapterPosition());
                    holder.movieView.populateUiData(movie);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mContext.startActivity(new Intent(mContext, DetailsActivity.class).
                                    putExtra(mContext.getString(R.string.extra_id)
                                            , movie.getMovieId())
                                    .putExtra(mContext.getString(R.string.extra_title)
                                            , movie.getOriginalTitle()));
                        }
                    });
                }
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
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MovieView movieView;

        public ViewHolder(MovieView movieView) {
            super(movieView);
            this.movieView = movieView;

        }


        /**
         * adds a new movie to movie list
         * @param position
         * @param movie
         */
        public void add(int position, Movie movie) {
            if(movies != null)
                movies.add(position, movie);
            notifyItemInserted(position);
        }

        /**
         * removes this item from list
         * @param moviePosition
         * retutn true if removed. false otherwise
         */
        public boolean remove(int moviePosition) {
            if(movies == null)
                return false;
            try{
                movies.remove(moviePosition);
                notifyItemRemoved(moviePosition);
                return true;
            }catch (UnsupportedOperationException e){
                return false;
            }catch (IndexOutOfBoundsException e){
                return false;
            }
        }
    }
}
