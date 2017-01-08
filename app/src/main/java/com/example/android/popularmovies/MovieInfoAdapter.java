package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieInfoAdapter extends ArrayAdapter<MovieInfo>{
    private static final String LOG_TAG = MovieInfoAdapter.class.getSimpleName();

    public MovieInfo[] movieInfos;



    public  interface MovieInfoAdapterOnClickHandler{
        void onClick(String movieInfo);
    }



    public MovieInfoAdapter(Context context, List<MovieInfo> movieInfos){
        super(context, 0, movieInfos);
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        final MovieInfo movieInfo = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_panels, parent, false);
        }
        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w370_and_h556_bestv2"+movieInfo.getPoster()).into(iconView);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MovieDetails.class);
                String title = movieInfo.getTitle();
                String plot = movieInfo.getPlot();
                String poster = movieInfo.getPoster();
                int rating = movieInfo.getVote();
                String rate = Integer.toString(rating);
                String release = movieInfo.getRelease();
                Log.d("title", title);
                intent.putExtra("EXTRA_TITLE", title);
                intent.putExtra("EXTRA_PLOT", plot);
                intent.putExtra("EXTRA_POSTER", poster);
                intent.putExtra("EXTRA_RATING", rate);
                intent.putExtra("EXTRA_DATE", release);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public void setMovieInfos(MovieInfo[] moviedata){
        movieInfos = moviedata;
        notifyDataSetChanged();
    }
}