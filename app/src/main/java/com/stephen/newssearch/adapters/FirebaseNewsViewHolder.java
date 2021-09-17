package com.stephen.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindNews(Article article) {
        ImageView newsImageView = (ImageView) mView.findViewById(R.id.newsImageView);
        TextView newsNameTextView = (TextView) mView.findViewById(R.id.newsNameTextView);
        TextView title = (TextView) mView.findViewById(R.id.title);
        TextView publishedAt= (TextView) mView.findViewById(R.id.publishedAt);


        Picasso.get().load(article.getUrlToImage()).into(newsImageView);

        newsNameTextView.setText(article.getName());
        title.setText(article.getTitle());
        publishedAt.setText(article.getPublishedAt());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Article> articles = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    articles.add(snapshot.getValue(Article.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("articles", Parcels.wrap(articles));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
