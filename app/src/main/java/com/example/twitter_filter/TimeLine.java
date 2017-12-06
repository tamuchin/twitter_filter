package com.example.twitter_filter;

/**
 * Created by basyou on 2017/11/30.
 */

public class TimeLine {
    long id;
    private String tweet;
    //private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTweet(){
        return tweet;
    }

    public void setTweet(String str){
        this.tweet=str;
    }

    /*public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price=price;
    }*/
}
