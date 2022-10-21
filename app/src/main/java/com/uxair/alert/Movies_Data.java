package com.uxair.alert;

import android.graphics.Bitmap;

public class Movies_Data {
    private String title;
    private String poster;
    private String year;
    private String type;
    private String desc;
    private String rating;
    private String backdrop_path;

    public Movies_Data(String title, String poster, String year, String type, String desc, String rating, String backdrop_path) {
        this.title = title;
        this.poster = poster;
        this.year = year;
        this.type = type;
        this.desc = desc;
        this.rating = rating;
        this.backdrop_path = backdrop_path;
    }

    public String getTitle(){
        return title;
    }
     public void setTitle(String title) {
        this.title = title;
     }

     public String getPoster(){
        return poster;
     }
     public void setPoster(String poster){
        this.poster = poster;
     }

    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year = year;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getRating(){
        return rating;
    }
    public void setRating(String rating){
        this.rating = rating;
    }

    public String getBackDrop(){
        return backdrop_path;
    }
    public void setBackDrop(String backdrop_path){
        this.backdrop_path = backdrop_path;
    }
}
