package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

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
    private Boolean pop = false;
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
                //Intent intent = new Intent(this, HighestRated.class);
                //startActivity(intent);
                pop = true;
                new FetchPopularTask().execute();
                break;
            case R.id.action_mostpop:
                message = "Most Popular";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                pop = false;
                new FetchPopularTask().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchPopularTask extends AsyncTask<Void, Void, MovieInfo[]>{
        @Override
        protected MovieInfo[] doInBackground(Void... voids) {
            URL popularURL;
            if(pop == false){
                popularURL = NetworkUtils.buildUrl();

            }else{
                popularURL = NetworkUtils.buildRatedUrl();
            }
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

}

