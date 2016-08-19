package com.ahmednmahran.moviesapp.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.model.Review;

import java.util.List;

/**
 * Created by Ahmed Nabil on 19/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ViewHolder>{
    private Context mContext;
    private List<Review> reviews;

    public ReviewsRecyclerAdapter(Context context, List<Review> reviews) {
        this.reviews = reviews;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder;
        View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view, parent, false);
        viewHolder = new ViewHolder(reviewView);
        reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(reviews.get(viewHolder.getAdapterPosition()).getUrl())));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.txtTitle.setText(review.getAuthor());
        holder.txtContent.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtContent;

        public ViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txtAuthor);
            this.txtContent = (TextView) view.findViewById(R.id.txtContent);

        }
    }
}
