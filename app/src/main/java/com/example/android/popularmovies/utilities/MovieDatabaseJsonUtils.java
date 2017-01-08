package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.util.Log;

import com.example.android.popularmovies.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDatabaseJsonUtils {

    public static MovieInfo[] getPopularMovieStringsFromJson(Context context, String popularJsonStr) throws JSONException{

        final String MDB_LIST = "results";

        final String MDB_TITLE = "original_title";
        final String MDB_DATE = "release_date";
        final String MDB_PLOT = "overview";
        final String MDB_POSTER = "poster_path";
        final String MDB_RATING = "vote_average";

        final String MDB_PASS = "success";

        MovieInfo[] moviesInfo = null;

        //String[] posterData = null;

        JSONObject popularJSON = new JSONObject(popularJsonStr);

        if(popularJSON.has(MDB_PASS)){
            boolean successCode = popularJSON.getBoolean(MDB_PASS);

            if(successCode == false){
                return null;
            }
        }

        JSONArray popularArray = popularJSON.getJSONArray(MDB_LIST);

        moviesInfo = new MovieInfo[popularArray.length()];

        for(int i = 0; i < popularArray.length(); i++){
            String movieTitle;
            String movieRelease;
            String moviePlot;
            String moviePoster;
            int movieRate;


            JSONObject movieData = popularArray.getJSONObject(i);
            movieTitle = movieData.getString(MDB_TITLE);
            movieRelease = movieData.getString(MDB_DATE);
            moviePlot = movieData.getString(MDB_PLOT);
            moviePoster = movieData.getString(MDB_POSTER);
            movieRate = movieData.getInt(MDB_RATING);

            moviesInfo[i] = new MovieInfo(movieTitle, movieRelease, moviePlot, moviePoster, movieRate);
        }



        return moviesInfo;
    }
}



