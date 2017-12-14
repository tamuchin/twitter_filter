package com.example.twitter_filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import twitter4j.Status;

/**
 * Created by basyou on 2017/11/30.
 */

public class MyAdapter extends ArrayAdapter<Status> {
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter,null);
        }
        Status item = getItem(position);

        TextView tweet = (TextView) convertView.findViewById(R.id.tweet);
        tweet.setText(item.getText());
        SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
        icon.setImageUrl(item.getUser().getProfileImageURL());


        return convertView;
    }
}