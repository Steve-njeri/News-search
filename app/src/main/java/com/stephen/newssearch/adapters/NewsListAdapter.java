package com.stephen.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stephen.newssearch.R;
import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.NewsDetailActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private List<Article> mArticles;
    private Context mContext;

    public NewsListAdapter(List<Article> articles, Context context) {
        this.mArticles= articles;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.NewsViewHolder holder, int position) {
        holder.bindArticles(mArticles.get(position));

    }

    @Override
    public int getItemCount() {
        return  mArticles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.newsImageView) ImageView mNewsImageView;
        @BindView(R.id.newsNameTextView) TextView mNameTextView;
        @BindView(R.id.author)TextView mAuthor;
        @BindView(R.id.title) TextView mTitle;
        @BindView(R.id.publishedAt) TextView mPublishedAt;


        private Context mContext;

        public NewsViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindArticles(Article article){
            mNameTextView.setText(article.getName());
            Picasso.get().load(article.getUrlToImage()).into(mNewsImageView);
            mAuthor.setText(article.getAuthor());
            mTitle.setText(article.getTitle());
            mPublishedAt.setText(article.getPublishedAt());
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("articles", Parcels.wrap(mArticles));
            mContext.startActivity(intent);
        }
    }

}
