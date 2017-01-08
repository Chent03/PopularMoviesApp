package com.example.android.popularmovies.utilities;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String API_KEY = "ENTER YOUR API_KEY HERE";

    final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    final static String TMDB_RATED_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";
    final static String PARAM_API = "api_key";

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
