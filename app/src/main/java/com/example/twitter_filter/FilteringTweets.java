package com.example.twitter_filter;

import java.util.Iterator;

import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * Created by noy on 2017/11/16.
 */

/*
    getFilterdTweet(ResponseList<Status>, FilteringPrameter) -> ResponseList<Status>
    ツイートのリストを受け取り、フィルタリング条件に一致した（表示できる）ツイートのみからなるリストを返す

    isPassing(Status, FilteringPrameter) -> boolean
    引数に与えたツイートがフィルタリング条件に一致するかを調べ、結果をbooleanで返す
 */


public class FilteringTweets {
    public ResponseList<Status> getFilteredTweet(ResponseList<Status> lists, FilteringPrameter parameter){
        Iterator<Status> it = lists.iterator();
        while(it.hasNext()){
            Status status = it.next();
            if(!isPassing(status, parameter)){
                it.remove();
            }
        }
        return lists;
    }

    public boolean isPassing(Status status, FilteringPrameter parameter){
        if(parameter.isActive(0) && status.getText().length() < parameter.getMinLength()) return false;
        if(parameter.isActive(1) && status.getFavoriteCount() < parameter.getMinFav()) return false;
        if(parameter.isActive(2) && status.getExtendedMediaEntities().length >= 1) return false;
        return true;
    }
}
