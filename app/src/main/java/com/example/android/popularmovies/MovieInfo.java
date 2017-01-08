package com.example.android.popularmovies;

public class MovieInfo {
    String title;
    String release;
    String poster;
    String plot;
    int vote;

    public MovieInfo(String title, String release, String plot, String poster, int vote){
        this.title = title;
        this.release = release;
        this.plot = plot;
        this.poster = poster;
        this.vote = vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
