package com.stephen.newssearch;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyNewsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mArticles;

    public MyNewsArrayAdapter(Context mContext, int resource, String[] mArticles) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mArticles = mArticles;
    }

    @Override
    public Object getItem(int position) {
        String article = mArticles[position];
        return String.format("%s \nServes great: %s", article);
    }

    @Override
    public int getCount() {
        return mArticles.length;
    }
}
