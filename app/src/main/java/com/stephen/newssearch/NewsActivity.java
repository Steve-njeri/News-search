package com.stephen.newssearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {
    private TextView mNewsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mNewsTextView = (TextView) findViewById(R.id.newsEditText);

        Intent intent = getIntent();
        String news = intent.getStringExtra("news");
        mNewsTextView.setText("Here are all the latest news: " + news);
    }
}