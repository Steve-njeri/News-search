package com.stephen.newssearch;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyNewsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mNews;

    public MyNewsArrayAdapter(Context mContext, int resource, String[] mNews) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mNews = mNews;
    }

    @Override
    public Object getItem(int position) {
        String news = mNews[position];
        return String.format(news);
    }

    @Override
    public int getCount() {
        return mNews.length;
    }
}
