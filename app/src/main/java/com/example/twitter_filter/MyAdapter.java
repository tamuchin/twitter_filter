package com.example.twitter_filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by basyou on 2017/11/30.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<TimeLine> TimeLineList;

    public MyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTimeLineList(ArrayList<TimeLine> timeLineList) {
        this.TimeLineList = timeLineList;
    }

    @Override
    public int getCount() {
        return TimeLineList.size();
    }

    @Override
    public Object getItem(int position) {
        return TimeLineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return TimeLineList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.adapter,parent,false);

        ((TextView)convertView.findViewById(R.id.tweet)).setText(TimeLineList.get(position).getTweet());
        //((TextView)convertView.findViewById(R.id.price)).setText(String.valueOf(TimeLineList.get(position).getPrice()));

        return convertView;
    }
}