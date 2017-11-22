package com.example.twitter_filter;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterOAuthActivity extends AppCompatActivity {

    private String callBackURL;
    private Twitter twitter;
    private RequestToken requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_oauth);

        //CallBack用URLの設定
        callBackURL = getString(R.string.twitter_callback_url);
        //Twitterインスタンスの取得
        twitter = TwitterUtils.getTwitterInstance(this);

        //認証開始
        startAuthorize();
    }

    private void startAuthorize() {
        //AsyncTaskによる非同期処理
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    //リクエストトークンの取得
                    requestToken = twitter.getOAuthRequestToken(callBackURL);
                    return requestToken.getAuthorizationURL();
                }catch (TwitterException e){
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String url) {
                if (url != null) {
                    //渡されたurlへアクティビティを遷移する
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    // 失敗。。。
                }
            }
        };
        task.execute();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent == null || intent.getData() == null || !intent.getData().toString().startsWith(callBackURL)) {
            return;
        }
        //URLによって実行されたアプリから引数を取得する
        String verifier = intent.getData().getQueryParameter("oauth_verifier");

        AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
            @Override
            protected AccessToken doInBackground(String... params) {
                try {
                    //アクセストークンの取得
                    return twitter.getOAuthAccessToken(requestToken, params[0]);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(AccessToken accessToken) {
                //トークンの登録
                if (accessToken != null) {
                    // 認証成功！
                    showToast("認証成功！");
                    successOAuth(accessToken);
                } else {
                    // 認証失敗。。。
                    showToast("認証失敗。。。");
                }
            }
        };
        task.execute(verifier);
    }

    private void successOAuth(AccessToken accessToken) {

        //Utilクラスからトークン登録メソッドを呼び出し
        TwitterUtils.storeAccessToken(this, accessToken);

        //MainActivityへ遷移
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        //このアクティビティを狩猟する
        finish();
    }

    //トーストを表示するメソッド
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
