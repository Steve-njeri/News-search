package com.stephen.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;
import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.NewsDetailActivity;
import com.stephen.newssearch.util.ItemTouchHelperViewHolder;

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    public ImageView mNewsImageView;

    public FirebaseNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindNews(Article article) {
        mNewsImageView = mView.findViewById(R.id.newsImageView);
        TextView newsNameTextView = mView.findViewById(R.id.newsNameTextView);
        TextView title = mView.findViewById(R.id.title);
        TextView publishedAt= mView.findViewById(R.id.publishedAt);

        Picasso.get().load(article.getUrlToImage()).into(mNewsImageView);
        newsNameTextView.setText(article.getName());
        title.setText(article.getTitle());
        publishedAt.setText(article.getPublishedAt());
    }

    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
