package com.example.android.popularmovies;


public class MovieTrailers {
    String name;
    String key;
    String type;

    public MovieTrailers(String name, String key, String type){
        this.name = name;
        this.key = key;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
