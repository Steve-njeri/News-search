package com.stephen.newssearch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;
import com.stephen.newssearch.adapters.FirebaseNewsViewHolder;
import com.stephen.newssearch.models.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedNewsListActivity extends AppCompatActivity {
    private DatabaseReference mNewsReference;
    private FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        mNewsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS);
        setUpFirebaseAdapter();
        hideProgressBar();
        showNews();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(mNewsReference, Article.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder>(options) {

            @NonNull
            @Override
            public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
                return new FirebaseNewsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseNewsViewHolder, int position, @NonNull Article article) {
                firebaseNewsViewHolder.bindNews(article);

            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    private void showNews() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}