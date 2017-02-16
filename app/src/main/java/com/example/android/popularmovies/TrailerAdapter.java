package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tony on 1/28/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    public MovieTrailers[] movieTrailers;

    public TrailerAdapter(Context context, MovieTrailers[] movieTrailers){
        this.context = context;
        this.movieTrailers = movieTrailers;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.trailer_links, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Item) holder).textView.setText(movieTrailers[position].getName());
        ((Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/watch?v=" + movieTrailers[position].getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieTrailers.length;
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView;

        public Item(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.trailerLinks);
        }
    }
}
