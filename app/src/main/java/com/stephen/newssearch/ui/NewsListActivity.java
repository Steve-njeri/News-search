package com.stephen.newssearch.ui;

import static com.stephen.newssearch.Constants.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.ProgressBar;

import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;
import com.stephen.newssearch.adapters.NewsListAdapter;
import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.models.NewsSearchResponse;
import com.stephen.newssearch.network.NewsApi;
import com.stephen.newssearch.network.NewsClient;

import java.util.List;


public class NewsListActivity extends AppCompatActivity {
//    private SharedPreferences mSharedPreferences;
//    private String mRecentNews;

    private static final String TAG = NewsListActivity.class.getSimpleName();
    private NewsListAdapter mAdapter;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    public List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        NewsApi client = NewsClient.getClient();

        Call<NewsSearchResponse> call = client.getTopHeadlines(source, API_KEY);

        call.enqueue(new Callback<NewsSearchResponse>() {

            @Override
            public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    articles = response.body().getArticles();
                    mAdapter = new NewsListAdapter(articles, NewsListActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(NewsListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showNews();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<NewsSearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
                hideProgressBar();
                showFailureMessage();
            }

        });

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mRecentNews  = mSharedPreferences.getString(Constants.PREFERENCES_SOURCE_KEY, null);

        String q = intent.getStringExtra("source");

    }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showNews() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
