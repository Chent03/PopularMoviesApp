package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Tony on 1/30/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    public MovieReview[] movieReviews;

    public ReviewAdapter(Context context, MovieReview[] movieReviews) {
        this.context = context;
        this.movieReviews = movieReviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.movie_review, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Item) holder).textView.setText(movieReviews[position].getText());
    }

    @Override
    public int getItemCount() {
        return movieReviews.length;
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView;

        public Item(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.movieReview);
        }
    }
}
