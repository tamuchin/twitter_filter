package com.example.twitter_filter;

import com.loopj.android.image.SmartImageView;

/**
 * Created by basyou on 2017/11/30.
 */

public class TimeLine {
    long id;
    private String tweet;
    private SmartImageView icon;
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

    public int getIcon(){
        //return icon;
        return 0;
    }

    public void setIcon(SmartImageView pic){
        this.icon=pic;
    }
}
