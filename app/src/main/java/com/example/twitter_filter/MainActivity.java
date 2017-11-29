package com.example.twitter_filter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MainActivity extends AppCompatActivity {

    Twitter mTwitter;
    ArrayAdapter<String> adapter;
    ArrayList<String> hoge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!TwitterUtils.hasAccessToken(this)){
            Intent intent = new Intent(this, TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }

        ListView list = (ListView) findViewById(R.id.listView);
        hoge = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.adapter, hoge);

        list.setAdapter(adapter);


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
                    adapter.add(tweet_str);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        task.execute();
    }
}

