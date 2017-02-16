package com.example.android.popularmovies;

/**
 * Created by Tony on 2/13/17.
 */

public class FavoriteItems {
    public int movie_id;
    public String movie_name;
    public String movie_wallpaper;
    public String movie_plot;
    public String movie_date;
    public String movie_rating;

    public FavoriteItems(int movieid, String title, String wallpaper, String plot, String date, String rating){
        this.movie_id = movieid;
        this.movie_name = title;
        this.movie_wallpaper = wallpaper;
        this.movie_plot = plot;
        this.movie_date = date;
        this.movie_rating = rating;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public String getMovie_wallpaper() {
        return movie_wallpaper;
    }

    public String getMovie_plot() {
        return movie_plot;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public String getMovie_rating() {
        return movie_rating;
    }
}
