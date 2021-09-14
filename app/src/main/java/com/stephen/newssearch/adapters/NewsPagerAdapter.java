package com.stephen.newssearch.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.NewsDetailFragment;

import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private List<Article> mArticle;

    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Article> articles) {
        super(fm, behavior);
        mArticle = articles;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(mArticle.get(position));
    }

    @Override
    public int getCount() {
        return mArticle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArticle.get(position).getName();
    }
}