package com.ahmednmahran.moviesapp.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ahmed Nabil on 14/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class TrailersRecyclerAdapter extends RecyclerView.Adapter<TrailersRecyclerAdapter.ViewHolder>{
    private Context mContext;
    private List<Trailer> trailers;

    public TrailersRecyclerAdapter(Context context, List<Trailer> trailers) {
        this.trailers = trailers;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder;
        View trailerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_view, parent, false);
        viewHolder = new ViewHolder(trailerView);
        trailerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailers.get(viewHolder.getAdapterPosition()).getKey())));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.txtTitle.setText(trailer.getName());
        holder.txtQuality.setText(trailer.getSize()+"");
        Picasso.with(mContext).load("http://img.youtube.com/vi/"+ trailer.getKey() +"/1.jpg").error(android.R.drawable.ic_media_play).fit().into(holder.imgThumbnail);

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtQuality;
        ImageView imgThumbnail;

        public ViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtQuality = (TextView) view.findViewById(R.id.txtQuality);
            this.imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);

        }
    }
}
