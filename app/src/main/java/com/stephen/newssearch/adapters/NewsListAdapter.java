package com.stephen.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;
import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.MainActivity;
import com.stephen.newssearch.ui.NewsDetailActivity;
import com.stephen.newssearch.ui.NewsDetailFragment;
import com.stephen.newssearch.util.OnNewsSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private ArrayList<Article> mArticles = new ArrayList<>();
    private Context mContext;

    public NewsListAdapter(ArrayList<Article> articles, Context context) {
        mContext = context;
        mArticles = articles;
    }


    @Override
    public NewsListAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view, mArticles);
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
        @BindView(R.id.title) TextView mTitle;
        @BindView(R.id.publishedAt) TextView mPublishedAt;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Article> mArticles = new ArrayList<>();
        private OnNewsSelectedListener mNewsSelectedListener;

        public NewsViewHolder(View itemView, ArrayList<Article> articles){
            super(itemView);
            ButterKnife.bind(this, itemView);
            
            mContext = itemView.getContext();
            // Determines the current orientation of the device:
            mOrientation = itemView.getResources().getConfiguration().orientation;

            mArticles = articles;

            // Checks if the recorded orientation matches Android's landscape configuration.
            // if so, we create a new DetailFragment to display in our special landscape layout:
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);
        }

        // Takes position of news in list as parameter:
        private void createDetailFragment(int position){
            // Creates new NewsDetailFragment with the given position:
            NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(mArticles, position);
            // Gathers necessary components to replace the FrameLayout in the layout with the NewsDetailFragment:
            FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
            //  Replaces the FrameLayout with the RestaurantDetailFragment:
            ft.replace(R.id.newsDetailContainer, detailFragment);
            // Commits these changes:
            ft.commit();
        }

        public void bindArticles(Article article){
            mNameTextView.setText(article.getName());
            Picasso.get().load(article.getUrlToImage()).into(mNewsImageView);
            mTitle.setText(article.getTitle());
            mPublishedAt.setText(article.getPublishedAt());
        }

        @Override
        public void onClick(View view) {
            // Determines the position of the news clicked:
            int itemPosition = getLayoutPosition();
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
                mContext.startActivity(intent);
            }
        }
    }

}
