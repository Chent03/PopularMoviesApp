package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        String title = intent.getStringExtra("EXTRA_TITLE");
        String plot = intent.getStringExtra("EXTRA_PLOT");
        String poster = intent.getStringExtra("EXTRA_POSTER");
        String rating = intent.getStringExtra("EXTRA_RATING");
        String date = intent.getStringExtra("EXTRA_DATE");

        TextView titleView = (TextView)findViewById(R.id.textTitle);
        titleView.setText(title);

        ImageView posterView = (ImageView)findViewById(R.id.textPoster);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w370_and_h556_bestv2"+poster).into(posterView);

        TextView plotView = (TextView)findViewById(R.id.textPlot);
        plotView.setText(plot);

        TextView rateView = (TextView)findViewById(R.id.textRate);
        rateView.setText("Rating: "+rating +"/10");

        TextView dateView = (TextView)findViewById(R.id.textDate);
        dateView.setText("Release Date: " + date);



    }
}
