package com.stephen.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;

import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.NewsDetailActivity;
import com.stephen.newssearch.ui.NewsDetailFragment;
import com.stephen.newssearch.util.ItemTouchHelperAdapter;
import com.stephen.newssearch.util.OnStartDragListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;


public class FirebaseNewsListAdapter extends FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Article> mArticles = new ArrayList<>();
    private int mOrientation;


    public FirebaseNewsListAdapter(FirebaseRecyclerOptions<Article> options,
                                         Query ref,
                                         OnStartDragListener onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                mArticles.add(dataSnapshot.getValue(Article.class));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseNewsViewHolder, int position, @NonNull Article article) {
        firebaseNewsViewHolder.bindNews(article);
        
        mOrientation = firebaseNewsViewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
            createDetailFragment(0);
        }

        firebaseNewsViewHolder.mNewsImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseNewsViewHolder);
                }
                return false;
            }
        });


        firebaseNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = firebaseNewsViewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void createDetailFragment(int position){
        // Creates new NewsDetailFragment with the given position:
        NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(mArticles, position);
        // Gathers necessary components to replace the FrameLayout in the layout with the NewsDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the RestaurantDetailFragment:
        ft.replace(R.id.newsDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }

    @NonNull
    @Override
    public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_drag, parent, false);
        return new FirebaseNewsViewHolder(view);
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mArticles, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }


    @Override
    public void onItemDismiss(int position) {
        mArticles.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Article article : mArticles) {
            int index = mArticles.indexOf(article);
            DatabaseReference mReference = getRef(index);
            article.setIndex(Integer.toString(index));
            mReference.setValue(article);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }
}
