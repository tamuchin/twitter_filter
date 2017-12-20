package com.example.twitter_filter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import static com.example.twitter_filter.R.id.listView;

public class MainActivity extends AppCompatActivity {

    Twitter mTwitter;

    //ArrayList<TimeLine> list;
    MyAdapter myAdapter;
    final int REQUEST_CODE = 1001; //オプションアクティビティからの戻り値を受け取る

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

    //アクションバーを表示するメソッド
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    //アクションバーのオプションをタップしたときに呼び出されるメソッド
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.option_menu:
                System.out.println("heko"); //test
                Intent intent = new Intent(getApplication(), OptionActivity.class);
                startActivityForResult( intent, REQUEST_CODE );

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult( int requestCode, int resultCode, Intent intent ) {
        // startActivityForResult()の際に指定した識別コードとの比較
        if( requestCode == 1001 ){
            // 返却結果ステータスとの比較
            if( resultCode == Activity.RESULT_OK ){
                // 返却されてきたintentから値を取り出す
                FilteringParameter filteringParameter = (FilteringParameter) intent.getExtras().get("key");

                System.out.println(filteringParameter.getMinFav()); //test
                System.out.println(filteringParameter.getMinLength()); //test
            }
        }
    }
}

