package com.example.twitter_filter;

import java.io.Serializable;

/**
 * Created by noy on 2017/11/16.
 */


public class FilteringParameter implements Serializable {
    private final int filterNumber = 3;     //条件の数
    private int minLength;                  //文字数条件
    private int minFav;                     //お気に入り数条件

    FilteringParameter(){
        minLength = 80; //仮決定した初期値
        minFav = 10;

    }

    FilteringParameter(int length, int fav){
        minLength = length;
        minFav = fav;
    }

    public int getFilterNumber() {
        return filterNumber;
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

}
