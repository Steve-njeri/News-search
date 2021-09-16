package com.stephen.newssearch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stephen.newssearch.R;
import com.stephen.newssearch.models.Article;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.newsImageView) ImageView mImageLabel;
    @BindView(R.id.newsNameTextView) TextView mNameLabel;
    @BindView(R.id.author)TextView mAuthor;
    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.publishedAt) TextView mPublishedAt;
    @BindView(R.id.description)TextView mDescription;
    @BindView(R.id.websiteTextView) TextView mNewsUrl;
    @BindView(R.id.saveNewsButton) TextView mSaveNewsButton;

    private Article mArticle;

    public NewsDetailFragment() {
        // Required empty public constructor
    }


    public static NewsDetailFragment newInstance(Article article) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("article", Parcels.wrap(article));
        newsDetailFragment.setArguments(args);
        return newsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mArticle = Parcels.unwrap(getArguments().getParcelable("article"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mArticle.getUrlToImage()).into(mImageLabel);

        mNameLabel.setText(mArticle.getName());
        mAuthor.setText(mArticle.getAuthor());
        mTitle.setText(mArticle.getTitle());
        mPublishedAt.setText(mArticle.getPublishedAt());
        mDescription.setText(mArticle.getDescription());

        mNewsUrl.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mNewsUrl) {
            Intent webIntent = new Intent (Intent.ACTION_VIEW, Uri.parse(mArticle.getUrl()));
            startActivity(webIntent);
        }

    }
}