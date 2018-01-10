package com.example.twitter_filter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Toast;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import static com.example.twitter_filter.R.id.listView;

public class MainActivity extends AppCompatActivity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GestureDetector mGestureDetector;

    Twitter mTwitter;
    FilteringParameter filteringParameter;
    FilteringTweets filteringTweets;

    int mPage;  //ツイートを読み込む場所を表す

    //ArrayList<TimeLine> list;
    MyAdapter myAdapter;
    final int REQUEST_CODE = 1001; //オプションアクティビティからの戻り値を受け取る

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filteringParameter = new FilteringParameter();
        filteringTweets = new FilteringTweets();
        mGestureDetector = new GestureDetector(this, mOnGestureListener);
        mPage = 1;

        if(!TwitterUtils.hasAccessToken(this)){
            Intent intent = new Intent(getApplication(), TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            ListView TimeLine = (ListView) findViewById(listView);
            myAdapter = new MyAdapter(this);
            TimeLine.setAdapter(myAdapter);

            mTwitter = TwitterUtils.getTwitterInstance(this);
            test();
        }




        test();
    }

    void test(){
        AsyncTask<Void, Void, ResponseList<Status>> task = new AsyncTask<Void, Void, ResponseList<Status>>() {
            @Override
            protected ResponseList<twitter4j.Status> doInBackground(Void... params) {
                Paging paging = new Paging();
                paging.setPage(mPage);
                paging.setCount(40);
                try {
                    return mTwitter.getHomeTimeline(paging);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(ResponseList<twitter4j.Status> lists) {
                if (lists != null) {
                    ResponseList<twitter4j.Status> filteredLists = filteringTweets.getFilteredTweet(lists, filteringParameter);
                    myAdapter.clear();
                    for(twitter4j.Status status : filteredLists){
                        myAdapter.add(status);
                    }
                    // TimeLine.getListView().setSelection(0);
                }else{
                    Toast.makeText(MainActivity.this, "ツイートが読み込めません nullが返ってきた", Toast.LENGTH_SHORT).show();
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
                // 返却されてきたintentから値を取り出し、パラメータを設定する
                filteringParameter = (FilteringParameter) intent.getExtras().get("key");
                test(); //update listview
            }
        }
    }


    // これがないとGestureDetectorが動かない
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            try {

                if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH) {
                    // 縦の移動距離が大きすぎる場合は無視
                    return false;
                }

                if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    // 開始位置から終了位置の移動距離が指定値より大きい
                    // X軸の移動速度が指定値より大きい
                    Toast.makeText(MainActivity.this, "右から左", Toast.LENGTH_SHORT).show();
                    mPage++;
                    test();

                } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    // 終了位置から開始位置の移動距離が指定値より大きい
                    // X軸の移動速度が指定値より大きい
                    Toast.makeText(MainActivity.this, "左から右", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    };
}

