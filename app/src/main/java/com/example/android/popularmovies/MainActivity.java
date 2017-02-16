package com.example.android.popularmovies;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavoriteColumns;
import com.example.android.popularmovies.data.FavoriteProvider;
import com.example.android.popularmovies.utilities.MovieDatabaseJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private MovieInfoAdapter movieInfoAdapter;
    private MovieInfo[] movieInfos;
    private GridView gridView;
    private Cursor mData;
    //public String[] posterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.activity_main);
        gridView.setAdapter(movieInfoAdapter);

        new FetchPopularTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        Context context = MainActivity.this;
        String message;
        switch (menuItemThatWasSelected){
            case R.id.action_highrated:
                message = "Highest Rated";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                new FetchRatedTask().execute();
                break;
            case R.id.action_mostpop:
                message = "Most Popular";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                new FetchPopularTask().execute();
                break;
            case R.id.action_favorite:
                message = "Favorites";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                new FavoriteFetchTask().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchPopularTask extends AsyncTask<Void, Void, MovieInfo[]>{
        @Override
        protected MovieInfo[] doInBackground(Void... voids) {
            URL popularURL;

            popularURL = NetworkUtils.buildUrl();

            String response = null;
            try{
                response = NetworkUtils.getResponseFromHttpURL(popularURL);

                movieInfos = MovieDatabaseJsonUtils.getPopularMovieStringsFromJson(MainActivity.this, response);

                //for(String link : posterData){
                //    System.out.println(link);
                //}
            }catch (Exception e){
                e.printStackTrace();
            }
            return movieInfos;

        }

        @Override
        protected void onPostExecute(MovieInfo[] s) {
            if(s != null){
                Log.d("ARRAY", "CONTAINS SOMETHING");
                Context myActivity = getApplicationContext();
                Log.d("act", myActivity.toString());
                movieInfoAdapter = new MovieInfoAdapter(myActivity, Arrays.asList(s));
                gridView.setAdapter(movieInfoAdapter);
                Log.d("ARRAY 1", s[0].getPoster());
            }else{
                Log.d("ERROR", "EMPTY ARRAY");
            }
        }
    }
    public class FetchRatedTask extends AsyncTask<Void, Void, MovieInfo[]>{
        @Override
        protected MovieInfo[] doInBackground(Void... voids) {
            URL popularURL;

            popularURL = NetworkUtils.buildRatedUrl();

            String response = null;
            try{
                response = NetworkUtils.getResponseFromHttpURL(popularURL);

                movieInfos = MovieDatabaseJsonUtils.getPopularMovieStringsFromJson(MainActivity.this, response);

                //for(String link : posterData){
                //    System.out.println(link);
                //}
            }catch (Exception e){
                e.printStackTrace();
            }
            return movieInfos;

        }

        @Override
        protected void onPostExecute(MovieInfo[] s) {
            if(s != null){
                Log.d("ARRAY", "CONTAINS SOMETHING");
                Context myActivity = getApplicationContext();
                Log.d("act", myActivity.toString());
                movieInfoAdapter = new MovieInfoAdapter(myActivity, Arrays.asList(s));
                gridView.setAdapter(movieInfoAdapter);
                Log.d("ARRAY 1", s[0].getPoster());
            }else{
                Log.d("ERROR", "EMPTY ARRAY");
            }
        }
    }
    public class FavoriteFetchTask extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected Cursor doInBackground(Void... voids) {
            ContentResolver resolver = getContentResolver();

            Cursor cursor = resolver.query(FavoriteProvider.Favorite.FAVORITE_URI,
                    null,
                    null,
                    null,
                    null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mData = cursor;

            MovieInfo[] movieInfos = new MovieInfo[mData.getCount()];

            mData.moveToFirst();
            while(!mData.isAfterLast()){
                String title = mData.getString(mData.getColumnIndex(FavoriteColumns.TITLE));
                String release = mData.getString(mData.getColumnIndex(FavoriteColumns.DATE));
                String plot = mData.getString(mData.getColumnIndex(FavoriteColumns.PLOT));
                String poster = mData.getString(mData.getColumnIndex(FavoriteColumns.POSTER));
                String rating = mData.getString(mData.getColumnIndex(FavoriteColumns.RATING));
                int convertRate = Integer.parseInt(rating);
                String movieid = mData.getString(mData.getColumnIndex(FavoriteColumns._ID));
                int id = Integer.parseInt(movieid);
                movieInfos[mData.getPosition()] = new MovieInfo(title, release, plot, poster, convertRate, id);
                mData.moveToNext();
            }
            if(movieInfos != null){
                Log.d("ARRAY", "CONTAINS SOMETHING");
                Context myActivity = getApplicationContext();
                Log.d("act", myActivity.toString());
                movieInfoAdapter = new MovieInfoAdapter(myActivity, Arrays.asList(movieInfos));
                gridView.setAdapter(movieInfoAdapter);
            }else{
                Log.d("ERROR", "EMPTY ARRAY");
            }




        }
    }

}

