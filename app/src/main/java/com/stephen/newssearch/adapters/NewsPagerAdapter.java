package com.stephen.newssearch.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.NewsDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Article> mArticles;

    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Article> articles) {
        super(fm, behavior);
        mArticles = articles;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(mArticles, position);
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArticles.get(position).getTitle();
    }
}