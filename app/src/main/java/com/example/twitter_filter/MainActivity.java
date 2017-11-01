package com.example.twitter_filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!TwitterUtils.hasAccessToken(this)){
            Intent intent = new Intent(getApplication(), TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

