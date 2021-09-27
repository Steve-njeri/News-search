package com.stephen.newssearch.util;

import com.stephen.newssearch.models.Article;

import java.util.ArrayList;

public interface OnNewsSelectedListener {
    public void onNewsSelected(Integer position, ArrayList<Article> restaurants);
}