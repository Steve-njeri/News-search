package com.stephen.newssearch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stephen.newssearch.Constants;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        mTitle.setText(mArticle.getTitle().toString());
        mPublishedAt.setText(mArticle.getPublishedAt());
        mDescription.setText(mArticle.getDescription());

        mNewsUrl.setOnClickListener(this);
        mSaveNewsButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mNewsUrl) {
            Intent webIntent = new Intent (Intent.ACTION_VIEW, Uri.parse(mArticle.getUrl()));
            startActivity(webIntent);
        }

        if (v == mSaveNewsButton) {
            DatabaseReference newsRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_NEWS);
            newsRef.push().setValue(mArticle);
            Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
        }

        if (v == mSaveNewsButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference newsRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_NEWS)
                    .child(uid);

            DatabaseReference pushRef = newsRef.push();
            String pushId = pushRef.getKey();
            mArticle.setPushId(pushId);
            pushRef.setValue(mArticle);

            Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}