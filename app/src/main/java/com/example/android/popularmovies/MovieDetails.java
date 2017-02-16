package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavoriteColumns;
import com.example.android.popularmovies.data.FavoriteProvider;
import com.example.android.popularmovies.utilities.MovieDatabaseJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetails extends AppCompatActivity {

    private MovieTrailers movieTrailers[];
    private MovieReview movieReview[];
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private SQLiteDatabase mDb;
    ImageButton favorite;
    RecyclerView recyclerView;
    RecyclerView reviewRecycler;
    private boolean on = true;
    public String movieid;
    private Cursor mData;
    boolean isEmpty = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        final String title = intent.getStringExtra("EXTRA_TITLE");
        final String plot = intent.getStringExtra("EXTRA_PLOT");
        final String poster = intent.getStringExtra("EXTRA_POSTER");
        final String rating = intent.getStringExtra("EXTRA_RATING");
        final String date = intent.getStringExtra("EXTRA_DATE");
        movieid = intent.getStringExtra("EXTRA_MOVIEID");
        final int convertid = Integer.parseInt(movieid);

        TextView titleView = (TextView)findViewById(R.id.textTitle);
        titleView.setText(title);

        ImageView posterView = (ImageView)findViewById(R.id.textPoster);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w370_and_h556_bestv2"+poster).into(posterView);

        TextView plotView = (TextView)findViewById(R.id.textPlot);
        plotView.setText("Summay: \n" + plot);

        TextView rateView = (TextView)findViewById(R.id.textRate);
        rateView.setText("Rating: "+rating +"/10");

        TextView dateView = (TextView)findViewById(R.id.textDate);
        dateView.setText("Release Date: " + date);

        new FetchMovieTrailers().execute(movieid);
        new FetchMovieReviews().execute(movieid);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewRecycler = (RecyclerView)findViewById(R.id.reviewRecyclerView);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));

        final FavoriteItems[] favoriteArray = {
                new FavoriteItems(convertid, title, poster, plot, date, rating)
        };

        favorite = (ImageButton)findViewById(R.id.favoriteButton);
        new FavoriteCheckTask().execute(movieid);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                new FavoriteCheckTask().execute(movieid);
                switch (mData.getCount()){
                    case 0:
                        ContentValues cv = new ContentValues();
                        cv.put(FavoriteColumns._ID, convertid);
                        cv.put(FavoriteColumns.TITLE, title);
                        cv.put(FavoriteColumns.POSTER, poster);
                        cv.put(FavoriteColumns.PLOT,plot);
                        cv.put(FavoriteColumns.DATE, date);
                        cv.put(FavoriteColumns.RATING, rating);
                        try{
                            getContentResolver().insert(FavoriteProvider.Favorite.FAVORITE_URI, cv);
                        }catch (Exception e){
                            Log.e("ERROR", "Error applying insert", e);
                        }
                        favorite.setImageResource(android.R.drawable.btn_star_big_on);
                        message = "Favorited";
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        new FavoriteDeleteTask().execute(movieid);
                        message = "Unfavorite";
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }


    public class FetchMovieTrailers extends AsyncTask<String, Void, MovieTrailers[]>{

        @Override
        protected MovieTrailers[] doInBackground(String... id) {
            String number = id[0];
            int movieId = Integer.parseInt(number);


            URL trailerURL;
            trailerURL = NetworkUtils.buildVideoUrl(movieId);


            Log.d("TRAILER URL: ", trailerURL.toString());

            String responseTrailer = null;


            try{
                responseTrailer = NetworkUtils.getResponseFromHttpURL(trailerURL);

                movieTrailers = MovieDatabaseJsonUtils.getTrailersStringsFromJson(MovieDetails.this, responseTrailer);
            }catch (Exception e){
                e.printStackTrace();
            }
            return movieTrailers;
        }

        @Override
        protected void onPostExecute(MovieTrailers[] movieTrailerses) {

            if(movieTrailerses != null){
                Log.d("ARRAY", "CONTAINS SOMETHING");
                Context myActivity = getApplicationContext();
                Log.d("act", myActivity.toString());
                trailerAdapter = new TrailerAdapter(myActivity, movieTrailerses );
                recyclerView.setAdapter(trailerAdapter);
                //Log.d("ARRAY 1", s[0].getPoster());
            }else{
                Log.d("ERROR", "EMPTY ARRAY");
            }

        }
    }

    public class FetchMovieReviews extends AsyncTask<String, Void, MovieReview[]>{

        @Override
        protected MovieReview[] doInBackground(String... strings) {
            String id = strings[0];
            int reviewid = Integer.parseInt(id);

            URL reviewURL;
            reviewURL = NetworkUtils.buildReviewUrl(reviewid);

            String responseReview = null;

            try{
                responseReview = NetworkUtils.getResponseFromHttpURL(reviewURL);
                movieReview = MovieDatabaseJsonUtils.getReviewStringsFromJson(MovieDetails.this, responseReview);
            }catch (Exception e){
                e.printStackTrace();
            }

            return movieReview;
        }

        @Override
        protected void onPostExecute(MovieReview[] movieReviews) {
            if(movieReviews != null){
                Context myActivity = getApplicationContext();
                reviewAdapter = new ReviewAdapter(myActivity, movieReviews);
                reviewRecycler.setAdapter(reviewAdapter);
            }else{
                Log.d("ERROR", "EMPTY ARRAY");
            }
        }
    }

    public class FavoriteDeleteTask extends AsyncTask<String, Void, Integer>{
        @Override
        protected Integer doInBackground(String... id) {
            ContentResolver resolver = getContentResolver();

            String where = "_id=?";
            String movieNum = id[0];
            String[] args = new String[]{movieNum};
            int cursor = resolver.delete(FavoriteProvider.Favorite.FAVORITE_URI,where, args);

            return cursor;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 1){
                favorite.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }
    }

    public class FavoriteCheckTask extends AsyncTask<String, Void, Cursor>{
        @Override
        protected Cursor doInBackground(String... strings) {

            String[] project = {FavoriteColumns._ID};
            String where = "_id=?";
            String movieNum = strings[0];
            Log.d("MOVIE NUM", movieNum);
            String[] args = {movieNum};
            Log.d("ARGS LENGHT", args.length + "");

            ContentResolver resolver = getContentResolver();


            Cursor cursor = resolver.query(FavoriteProvider.Favorite.FAVORITE_URI,
                    project,
                    where,
                    args,
                    null);


            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            Log.d("Is CURSOR EMPTY", cursor.getCount() + "");
            mData = cursor;
            if(cursor.getCount() == 1){
                isEmpty = false;
                favorite.setImageResource(android.R.drawable.btn_star_big_on);
            }else{
                isEmpty = true;
                favorite.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }
    }
}
