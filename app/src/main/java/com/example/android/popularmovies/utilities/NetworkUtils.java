package com.example.android.popularmovies.utilities;


import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String API_KEY = "INSERT YOUR API KEY HERE";

    final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    final static String TMDB_RATED_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";
    final static String PARAM_API = "api_key";
    final static String TMDB_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String VIDEOS = "videos";
    final static String REVIEWS = "reviews";



    public static URL buildReviewUrl(int id){
        Uri builtURL = Uri.parse(TMDB_MOVIE_BASE_URL).buildUpon()
                .appendPath(Integer.toString(id))
                .appendPath(REVIEWS)
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(builtURL.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildVideoUrl(int id){
        Uri builtURL = Uri.parse(TMDB_MOVIE_BASE_URL).buildUpon()
                .appendPath(Integer.toString(id))
                .appendPath(VIDEOS)
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(builtURL.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.d("VIDEO URL" , url.toString());
        return url;
    }

    public static URL buildUrl(){
        Uri builtURL = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(builtURL.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static URL buildRatedUrl(){
        Uri builtRatedURL = Uri.parse(TMDB_RATED_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(builtRatedURL.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpURL(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
