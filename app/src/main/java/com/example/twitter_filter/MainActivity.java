package com.example.twitter_filter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import static com.example.twitter_filter.R.id.listView;

public class MainActivity extends AppCompatActivity {

    Twitter mTwitter;

    ArrayList<TimeLine> list;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView TimeLine = (ListView) findViewById(listView);

        list = new ArrayList<>();
        myAdapter = new MyAdapter(MainActivity.this);
        myAdapter.setTimeLineList(list);
        TimeLine.setAdapter(myAdapter);

        if(!TwitterUtils.hasAccessToken(this)){
            Intent intent = new Intent(getApplication(), TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }
        System.out.println("oawtta");

        mTwitter = TwitterUtils.getTwitterInstance(this);




        test();
    }

    void test(){
        AsyncTask<Void, Void, ResponseList<Status>> task = new AsyncTask<Void, Void, ResponseList<Status>>() {
            @Override
            protected ResponseList<twitter4j.Status> doInBackground(Void... params) {
                try {
                    return mTwitter.getHomeTimeline();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(ResponseList<twitter4j.Status> lists) {
                for(twitter4j.Status status : lists){
                    System.out.println(status.getText());
                    String tweet_str= status.getText();

                    TimeLine timeline = new TimeLine();
                    timeline.setTweet(tweet_str);
                    list.add(timeline);
                    myAdapter.notifyDataSetChanged();
                }
            }
        };
        task.execute();
    }
}

