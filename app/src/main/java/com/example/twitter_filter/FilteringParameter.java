package com.example.twitter_filter;

/**
 * Created by noy on 2017/11/16.
 */


public class FilteringParameter {
    private final int filterNumber = 3;     //条件の数
    private int minLength;                  //文字数条件
    private int minFav;                     //お気に入り数条件
    private boolean[] isActive;             //フィルターが作用しているか

    FilteringParameter(){
        isActive = new boolean[filterNumber];

        minLength = 80; //仮決定した初期値
        minFav = 10;

        for(int i = 0; i < filterNumber; i++){
            isActive[i] = false;
        }
    }

    FilteringParameter(int length, int fav){
        isActive = new boolean[filterNumber];

        minLength = length;
        minFav = fav;

        for(int i = 0; i < filterNumber; i++){
            isActive[i] = false;
        }
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

    public boolean isActive(int i){
        return isActive[i];
    }
    public void activate(int i){
        isActive[i] = true;
    }

    public void inactivate(int i){
        isActive[i] = false;
    }

}
