package com.example.twitter_filter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import static com.example.twitter_filter.R.id.listView;

public class MainActivity extends AppCompatActivity {

    private Twitter mTwitter;

    //ArrayList<TimeLine> list;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!TwitterUtils.hasAccessToken(this)){
            Intent intent = new Intent(getApplication(), TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            ListView TimeLine = (ListView) findViewById(listView);
            myAdapter = new MyAdapter(this);
            TimeLine.setAdapter(myAdapter);
            System.out.println("oawtta");

            mTwitter = TwitterUtils.getTwitterInstance(this);
            test();
        }
    }

    void test(){
        AsyncTask<Void, Void, List<Status>> task = new AsyncTask<Void, Void, List<Status>>() {
            @Override
            protected List<twitter4j.Status> doInBackground(Void... params) {
                try {
                    return mTwitter.getHomeTimeline();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(List<twitter4j.Status> lists) {
                if (lists != null) {
                    myAdapter.clear();
                    for(twitter4j.Status status : lists){
                        myAdapter.add(status);
                    }
                   // TimeLine.getListView().setSelection(0);
                }
            }
        };
        task.execute();
    }
}

