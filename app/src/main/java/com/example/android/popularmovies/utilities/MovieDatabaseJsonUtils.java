package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.util.Log;

import com.example.android.popularmovies.MovieInfo;
import com.example.android.popularmovies.MovieReview;
import com.example.android.popularmovies.MovieTrailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDatabaseJsonUtils {

    public static MovieReview[] getReviewStringsFromJson(Context context, String reviewJsonStr) throws JSONException{
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";
        final String REVIEW_LIST = "results";
        final String MDB_PASS = "success";


        MovieReview[] movieReviews = null;
        JSONObject reviewJSON = new JSONObject(reviewJsonStr);

        if(reviewJSON.has(MDB_PASS)){
            boolean successCode = reviewJSON.getBoolean(MDB_PASS);

            if(successCode == false){
                return null;
            }
        }

        JSONArray reviewArray = reviewJSON.getJSONArray(REVIEW_LIST);

        movieReviews = new MovieReview[reviewArray.length()];

        for(int i = 0; i < reviewArray.length(); i++){
            String author;
            String content;

            JSONObject reviewData = reviewArray.getJSONObject(i);
            author = reviewData.getString(REVIEW_AUTHOR);
            content = reviewData.getString(REVIEW_CONTENT);

            movieReviews[i] = new MovieReview(author, content);
        }
        return movieReviews;
    }

    public static MovieTrailers[] getTrailersStringsFromJson(Context context, String trailerJsonStr) throws JSONException{
        final String TRAILER_TYPE = "type";
        final String TRAILER_NAME = "name";
        final String TRAILER_KEY = "key";
        final String MDB_PASS = "success";
        final String TRAILER_LIST = "results";


        MovieTrailers[] movieTrails = null;

        JSONObject trailerJSON = new JSONObject(trailerJsonStr);

        if(trailerJSON.has(MDB_PASS)){
            boolean successCode = trailerJSON.getBoolean(MDB_PASS);

            if(successCode == false){
                return null;
            }
        }

        JSONArray trailerArray = trailerJSON.getJSONArray(TRAILER_LIST);

        movieTrails = new MovieTrailers[trailerArray.length()];

        for(int i = 0; i < trailerArray.length(); i++){
            String type;
            String name;
            String key;

            JSONObject trailerData = trailerArray.getJSONObject(i);
            type = trailerData.getString(TRAILER_TYPE);
            name = trailerData.getString(TRAILER_NAME);
            key = trailerData.getString(TRAILER_KEY);

            movieTrails[i] = new MovieTrailers(name, key, type);
        }

        return movieTrails;
    }


    public static MovieInfo[] getPopularMovieStringsFromJson(Context context, String popularJsonStr) throws JSONException{

        final String MDB_LIST = "results";

        final String MDB_TITLE = "original_title";
        final String MDB_DATE = "release_date";
        final String MDB_PLOT = "overview";
        final String MDB_POSTER = "poster_path";
        final String MDB_RATING = "vote_average";
        final String MDB_MOVIEID = "id";

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
            int movieid;


            JSONObject movieData = popularArray.getJSONObject(i);
            movieTitle = movieData.getString(MDB_TITLE);
            movieRelease = movieData.getString(MDB_DATE);
            moviePlot = movieData.getString(MDB_PLOT);
            moviePoster = movieData.getString(MDB_POSTER);
            movieRate = movieData.getInt(MDB_RATING);
            movieid = movieData.getInt(MDB_MOVIEID);

            moviesInfo[i] = new MovieInfo(movieTitle, movieRelease, moviePlot, moviePoster, movieRate, movieid);
        }
        return moviesInfo;
    }
}



