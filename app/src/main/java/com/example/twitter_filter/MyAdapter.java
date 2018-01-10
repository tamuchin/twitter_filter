package com.example.twitter_filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import twitter4j.MediaEntity;
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

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(String.valueOf(item.getUser().getName()));
        TextView tweet = (TextView) convertView.findViewById(R.id.tweet);
        tweet.setText(item.getText());
        TextView fav = (TextView) convertView.findViewById(R.id.fav);
        fav.setText(String.valueOf(item.getFavoriteCount()));
        TextView retweet = (TextView) convertView.findViewById(R.id.retweet);
        retweet.setText(String.valueOf(item.getRetweetCount()));
        SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
        icon.setImageUrl(item.getUser().getProfileImageURL());

        MediaEntity[] mediaEntities = item.getExtendedMediaEntities();
        if (mediaEntities.length > 0) {
            SmartImageView pic1 = (SmartImageView) convertView.findViewById(R.id.pic1);
            pic1.setImageUrl(mediaEntities[0].getMediaURL());
        }


        return convertView;
    }
}