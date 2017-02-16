package com.example.android.popularmovies;


public class MovieReview {
    String text;
    String author;

    public MovieReview(String author, String text){
        this.author = author;
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
