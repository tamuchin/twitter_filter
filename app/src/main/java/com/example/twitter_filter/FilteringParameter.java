package com.example.twitter_filter;

import java.io.Serializable;

/**
 * Created by noy on 2017/11/16.
 */


public class FilteringParameter implements Serializable {
    private int minLength;                  //文字数条件
    private int minFav;                     //お気に入り数条件
    private int needImage;              //画層が含まれているかでフィルタリング
    public final int BOTH = 0;
    public final int NEED_IMAGE = 1;
    public final int NO_IMAGE = 2;

    FilteringParameter(){
        minLength = 0;
        minFav = 0;
        needImage = BOTH;
    }

    FilteringParameter(int length, int fav, int image){
        minLength = length;
        minFav = fav;
        needImage = image;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMinFav() {
        return minFav;
    }

    public void setMinFav(int minFav) {
        this.minFav = minFav;
    }

    public int getNeedImage() {
        return needImage;
    }

    public void setNeedImage(int needImage) {
        this.needImage = needImage;
    }
}
